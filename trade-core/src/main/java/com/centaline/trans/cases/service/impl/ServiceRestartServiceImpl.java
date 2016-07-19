package com.centaline.trans.cases.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ServiceRestartService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskOperate;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.ToTransPlanService;
import com.centaline.trans.task.service.UnlocatedTaskService;
@Service
@Transactional(readOnly=true)
public class ServiceRestartServiceImpl implements ServiceRestartService {
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private ToApproveRecordService toApproveService;
	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private ToTransPlanService toTransPlanService;
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	@Override
	@Transactional(readOnly=false)
	public StartProcessInstanceVo restart(ServiceRestartVo vo) {
		
		ToWorkFlow wf=new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		ToWorkFlow sameOne= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if(sameOne!=null){
			throw new BusinessException("当前重启流程尚未结束！");
		}
		
		ProcessInstance pi=new ProcessInstance(propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()), vo.getCaseCode());
		StartProcessInstanceVo spv=workFlowManager.startCaseWorkFlow(pi, vo.getUserName(),vo.getCaseCode());
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setProcessOwner(vo.getUserId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()));
		wf.setInstCode(spv.getId());
		toWorkFlowService.insertSelective(wf);
		return spv;
	}
	@Transactional(readOnly=false)
	@Override
	public boolean apply(ServiceRestartVo vo) {
		workFlowManager.submitTask(null, vo.getTaskId(),vo.getInstCode(),null, vo.getCaseCode());
		ToApproveRecord record=new ToApproveRecord();
		record.setApproveType("7");
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(vo.getPartCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveService.insertToApproveRecord(record);
		return true;
	}
	@Transactional(readOnly=false)
	@Override
	public boolean approve(ServiceRestartVo vo) {
		List<RestVariable> vs=new ArrayList<>();
		RestVariable v=new RestVariable("is_approved",vo.getIsApproved());
		vs.add(v);
		workFlowManager.submitTask(vs, vo.getTaskId(),vo.getInstCode(),null, vo.getCaseCode());
		ToApproveRecord record=new ToApproveRecord();
		record.setApproveType("7");
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(vo.getPartCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveService.insertToApproveRecord(record);
		if(vo.getIsApproved()){
			doApproved(vo);
		}
		return true;
	}
	/**
	 * 审批同意操作
	 * @param vo
	 */
	private void doApproved(ServiceRestartVo vo){
		toTransPlanService.deleteTransPlansByCaseCode(vo.getCaseCode());
		//删除原主流程 更新原主流程记录
		ToWorkFlow t=new ToWorkFlow();
		t.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		t.setCaseCode(vo.getCaseCode());
		ToWorkFlow mainflow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(t);
		if(mainflow!=null){
			try{
				unlocatedTaskService.deleteByInstCode(mainflow.getInstCode());
				workFlowManager.deleteProcess(mainflow.getInstCode());
			} catch (WorkFlowException e) {
				if(!e.getMessage().contains("statusCode[404]"))throw e;
			}
			mainflow.setStatus(WorkFlowStatus.TERMINATE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(mainflow);
		}
		//启动新的主流程并记录流程表
		ToCase cas=toCaseService.findToCaseByCaseCode(vo.getCaseCode());
		cas.setCaseProperty(CasePropertyEnum.TPZT.getCode());
		cas.setStatus(CaseStatusEnum.YFD.getCode());
		//更新Case表
		toCaseService.updateByCaseCodeSelective(cas);
		User u=uamUserOrgService.getUserById(cas.getLeadingProcessId());
		//无效业务表单
		toWorkFlowService.inActiveForm(vo.getCaseCode());
    	//更新当前流程为结束
		ToWorkFlow tf= toWorkFlowService.queryWorkFlowByInstCode(vo.getInstCode());
		tf.setStatus(WorkFlowStatus.COMPLETE.getCode());
		toWorkFlowService.updateByPrimaryKeySelective(tf);
		
		ProcessInstance process = new ProcessInstance();
    	process.setBusinessKey(vo.getCaseCode());
    	process.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
    	/*流程引擎相关*/
    	Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());
		List<RestVariable> variables = new ArrayList<RestVariable>();
	    Iterator it = defValsMap.keySet().iterator();  
	    while (it.hasNext()) {  
            String key = it.next().toString();  
    		RestVariable restVariable = new RestVariable();
    		restVariable.setName(key); 
    		restVariable.setValue(defValsMap.get(key));
    		variables.add(restVariable);
	    }
    	process.setVariables(variables);
    	StartProcessInstanceVo spi=workFlowManager.startCaseWorkFlow(process, u.getUsername(),vo.getCaseCode());
    	ToWorkFlow wf=new ToWorkFlow();
    	wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setProcessOwner(cas.getLeadingProcessId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
		wf.setInstCode(spi.getId());
		toWorkFlowService.insertSelective(wf);
	}

}
