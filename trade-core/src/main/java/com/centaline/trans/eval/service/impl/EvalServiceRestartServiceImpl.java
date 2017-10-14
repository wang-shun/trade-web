package com.centaline.trans.eval.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.common.enums.EvalStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.service.EvalServiceRestartService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ActRuTaskService;
import com.centaline.trans.task.service.ToApproveRecordService;

/**
 * @Description:评估重启service
 * @author：jinwl6
 * @date:2017年10月12日
 * @version:
 */
@Service
public class EvalServiceRestartServiceImpl implements EvalServiceRestartService {
	
	@Autowired
	ToEvalReportProcessService toEvalReportProcessService;
	@Autowired
	ToWorkFlowService toWorkFlowService;
	@Autowired
	ActRuTaskService actRuTaskService;
	@Autowired
	WorkFlowManager workFlowManager;
	@Autowired
	ToCaseService toCaseService;
	@Autowired
	PropertyUtilsService propertyUtilsService;
	@Autowired
	ProcessInstanceService processInstanceService;
	@Autowired
	TaskService taskService;
	@Autowired
	ToApproveRecordService toApproveRecordService;
	@Autowired
	UamSessionService uamSessionService;
	@Autowired
	ToCaseInfoService toCaseInfoService;
	
	public StartProcessInstanceVo restart(ServiceRestartVo vo) {
		
		return null;
	}

	@Override
	public boolean checkIsCanRestart(ServiceRestartVo vo, String userJob) {
		//查询评估信息
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByEvalCode(vo.getEvaCode());
		if(EvalStatusEnum.YSYBG.getCode().equals(toEvalReportProcess.getStatus())){
			  return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StartProcessInstanceVo SuspendEvalSubProcess(ServiceRestartVo vo) {
		
		// 1.查询流程
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.EVAL_SERVICE_RESTART_PROCESS.getCode());
		wf.setBizCode(vo.getEvaCode());
		ToWorkFlow activeWf = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(wf);
		if (activeWf != null) {
			throw new BusinessException("当前重启流程尚未结束！");
		}
		
	    /** 评估相关流程挂起  */
		activateOrSuspendProcessInstance(vo.getEvaCode(),false);
		
		//评估单更新成挂起评估
		//toEvalReportProcessService.updateEvalPropertyByEvalCode(vo.getEvaCode(),EvalPropertyEnum.PGGQ.getCode());
		
		/** 启动 评估重启流程 */
		Map<String, Object> vars = new HashMap<>();
		String manager = toCaseInfoService.getCaseManager(vo.getCaseCode());// 根据案件所在组找主管
		vars.put("consultant", vo.getUserName());//评估流程重启发起人
		vars.put("manager", manager);
		StartProcessInstanceVo spv = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_SERVICE_RESTART_PROCESS.getCode()), vo.getEvaCode(), vars);
		List<TaskVo> tasks = taskService.listTasks(spv.getId(), false, vo.getUserName()).getData();
		if (tasks != null && !tasks.isEmpty()) {
			spv.setActiveTaskId(tasks.get(0).getId() + "");
		}
		//wf.setBusinessKey(WorkFlowEnum.EVAL_SERVICE_RESTART_PROCESS.getCode());
		wf.setCaseCode(vo.getCaseCode());
		//wf.setBizCode(vo.getEvaCode());
		wf.setProcessOwner(vo.getUserId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_SERVICE_RESTART_PROCESS.getCode()));
		wf.setInstCode(spv.getId());
		wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(wf);
		return spv;
	}
	

	@Override
	public boolean apply(ServiceRestartVo vo) {
		workFlowManager.submitTask(null, vo.getTaskId(), vo.getInstCode(), null, vo.getCaseCode());
		ToApproveRecord record = new ToApproveRecord();
		record.setApproveType("10");
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(vo.getPartCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveRecordService.insertToApproveRecord(record);
		return true;
	}

	@Override
	public boolean approve(ServiceRestartVo vo) {
		SessionUser u = uamSessionService.getSessionUser();
		insertIntoApproveRecord(vo);//入审批记录表
		handerProcessAfterServiceRestart(vo,u);//流程重启后续业务处理
		activateOrSuspendProcessInstance(vo.getEvaCode(),true);//打开挂起流程
		submitEvelRestartTask(vo);//审批提交重启任务
		return true;
	}
    
	/**
	 * 流程重启后->删除旧评估流程->更改评估状态->启动新评估流程
	 */
	private void handerProcessAfterServiceRestart(ServiceRestartVo vo,SessionUser user) {
		
		//删除原评估主流程
		ToWorkFlow t = new ToWorkFlow();
		t.setBusinessKey(WorkFlowEnum.EVAL_PROCESS.getCode());
		t.setBizCode(vo.getEvaCode());
		ToWorkFlow preFlow = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(t);
		if (preFlow != null) {
			try {
				workFlowManager.deleteProcess(preFlow.getInstCode());
			} catch (WorkFlowException e) {
				if (!e.getMessage().contains("statusCode[404]"))
					throw e;
			}
			preFlow.setStatus(WorkFlowStatus.TERMINATE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(preFlow);
		}
		
		//workflow流程表更改为已结束
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setBizCode(vo.getEvaCode());
		ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(workFlow);
		if (record != null) {
			record.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(record);
		}
		vo.setUserId(user.getId());
		vo.setUserName(user.getUsername());
		
		//更改评估单状态为重启
		toEvalReportProcessService.updateStatusByEvalCode(EvalStatusEnum.YCQ.getCode(),vo.getEvaCode());
		
		//启动新的评估流程，入workflow表
		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_PROCESS.getCode()), 
    			vo.getEvaCode());

		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.EVAL_PROCESS.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setBizCode(vo.getCaseCode());
		wf.setProcessOwner(user.getId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_PROCESS.getCode()));
		wf.setInstCode(processInstance.getId());
		toWorkFlowService.insertSelective(wf);
	}

	/**
	 * 评估相关流程挂起或恢复
	 * @param instCode
	 * @param b
	 */
	private void activateOrSuspendProcessInstance(String evalCode, boolean b) {
		List<TaskVo> taskVos = actRuTaskService.getRuTaskByBizCode(evalCode);
		for (TaskVo task : taskVos) {
			if (!WorkFlowEnum.EVAL_SERVICE_RESTART_PROCESS.getCode().equals(task.getBusiness_key())) {
				if(b){
					 if(!task.getSuspended()) continue;
					 workFlowManager.activateOrSuspendProcessInstance(task.getInstCode(), b);
				}else{
					//挂起  未挂起的评估流程
					if(task.getSuspended()) continue;
					workFlowManager.activateOrSuspendProcessInstance(task.getInstCode(), b);
				}
				
				
			}
		}
	}
	
	/**
	 * @param vo
	 */
	private void insertIntoApproveRecord(ServiceRestartVo vo) {
		ToApproveRecord record = new ToApproveRecord();
		record.setApproveType("10");
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(vo.getPartCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveRecordService.insertToApproveRecord(record);		
	}

	/**
	 * 提交任务
	 * @param vo
	 */
	private void submitEvelRestartTask(ServiceRestartVo vo) {
		/*if (vo.getIsApproved()) {
			ToEvalReportProcess toEvalReportProcess = new ToEvalReportProcess();
			toEvalReportProcessService.updateEvalPropertyByEvalCode(vo.getEvaCode(),EvalPropertyEnum.PGYX.getCode());
		} else {

			ToWorkFlow wf = new ToWorkFlow();
			wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
			wf.setCaseCode(vo.getCaseCode());
			wf.setInstCode(vo.getInstCode());
			wf.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateWorkFlowByInstCode(wf);
		}*/
		List<RestVariable> vs = new ArrayList<>();
		RestVariable v = new RestVariable("is_approved", vo.getIsApproved());
		vs.add(v);
		workFlowManager.submitTask(vs, vo.getTaskId(), vo.getInstCode(), null, vo.getCaseCode());
	}
	
	
    
	
}
