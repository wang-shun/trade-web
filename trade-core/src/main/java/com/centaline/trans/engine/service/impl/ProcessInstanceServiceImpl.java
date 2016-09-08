package com.centaline.trans.engine.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.utils.WorkFlowUtils;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.vo.ProcessInstanceVO;

/**
 * 
 * @author jimmy 流程相关操作
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {
	@Autowired
	private WorkFlowEngine engine;

	@Override
	public StartProcessInstanceVo startWorkFlowByDfId(String processDefinitionId, String businessKey) {
		return startWorkFlowBase(processDefinitionId, null, null, businessKey, null);
	}

	@Override
	public StartProcessInstanceVo startWorkFlowByDfKey(String processDefinitionKey, String businessKey) {
		return startWorkFlowBase(null, processDefinitionKey, null, businessKey, null);
	}

	@Override
	public StartProcessInstanceVo startWorkFlowByMessage(String message, String businessKey) {
		return startWorkFlowBase(null, null, message, businessKey, null);
	}

	@Override
	public StartProcessInstanceVo startWorkFlowByDfId(String processDefinitionId, String businessKey,
			Map<String, Object> vars) {
		return startWorkFlowBase(processDefinitionId, null, null, businessKey, vars);
	}

	@Override
	public StartProcessInstanceVo startWorkFlowByDfKey(String processDefinitionKey, String businessKey,
			Map<String, Object> vars) {
		return startWorkFlowBase(null, processDefinitionKey, null, businessKey, vars);
	}

	@Override
	public StartProcessInstanceVo startWorkFlowByMessage(String message, String businessKey, Map<String, Object> vars) {
		return startWorkFlowBase(null, null, message, businessKey, vars);
	}

	/**
	 * 流程启动
	 * 
	 * @param processDefinitionId
	 * @param processDefinitionKey
	 * @param message
	 * @param businessKey
	 * @param vars
	 * @return
	 */
	private StartProcessInstanceVo startWorkFlowBase(String processDefinitionId, String processDefinitionKey,
			String message, String businessKey, Map<String, Object> vars) {
		ProcessInstance process = new ProcessInstance();
		process.setProcessDefinitionId(processDefinitionId);
		process.setMessage(message);
		process.setProcessDefinitionKey(processDefinitionKey);
		process.setBusinessKey(businessKey);

		process.setVariables(WorkFlowUtils.mapToVarList(vars));

		StartProcessInstanceVo obj = (StartProcessInstanceVo) engine
				.RESTfulWorkFlow(WorkFlowConstant.START_PROCESS_INSTANCE_KEY, StartProcessInstanceVo.class, process);
		return obj;
	}

	@Override
	public void activateOrSuspendProcessInstance(String processInstanceId, boolean activate) {
		Map<String, String> vars = new HashMap<>();
		vars.put("processInstanceId", processInstanceId);
		vars.put("action", activate ? "activate" : "suspend");
		engine.RESTfulWorkFlow(WorkFlowConstant.ACTIVATE_OR_SUSPEND_PROCESS_KEY, String.class, vars, null);
	}

	@Override
	public void deleteProcess(String processInstanceId) {
		Map<String, String> vars = new HashMap<>();
		vars.put("processInstanceId", processInstanceId);
		try {
			engine.RESTfulWorkFlow(WorkFlowConstant.DELETE_PROCESS_INSTANCE_KEY, Map.class, vars, null);
		} catch (WorkFlowException e) {
			if (!e.getMessage().contains("statusCode[404]"))
				throw e;
			e.printStackTrace();
		}
	}

	@Override
	public StartProcessInstanceVo getHistoryInstances(String processInstanceId) {
		Map<String, String> vars = new HashMap<>();
		vars.put("processInstanceId", processInstanceId);
		return (StartProcessInstanceVo) engine.RESTfulWorkFlow(WorkFlowConstant.GET_HIS_INSTANCES_KEY,
				StartProcessInstanceVo.class, vars, null);
	}
	@Override
	@SuppressWarnings("rawtypes")
	public PageableVo getByBusinessKey(String businessKey){
		Map<String, String> vars = new HashMap<>();
		vars.put("businessKey", businessKey);
		PageableVo vo = (PageableVo) engine.RESTfulWorkFlow(WorkFlowConstant.GET_PROCESS_INSTANCES_KEY, PageableVo.class, vars);
		WorkFlowUtils.convertPageableData(vo, StartProcessInstanceVo.class);
		return vo;
	}
	@Override
	public void deleteByBusinessKey(String businessKey){
		PageableVo pageable= getByBusinessKey(businessKey);
		if(pageable.getSize()>0){
			for (Object obj : pageable.getData()) {
				StartProcessInstanceVo vo= (StartProcessInstanceVo)obj;
				deleteProcess(vo.getId());
			}
		}
	}
	
}
