package com.centaline.trans.eval.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Job;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.common.enums.CaseParticipantEnum;
import com.centaline.trans.common.enums.EvalStatusEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
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
	@Autowired
	UamUserOrgService uamUserOrgService;
	
	public StartProcessInstanceVo restart(ServiceRestartVo vo) {
		
		return null;
	}

	@Override
	public boolean checkIsCanRestart(ServiceRestartVo vo, String userJob) {
		//查询评估信息
		/*ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByEvalCode(vo.getEvaCode());
		if(EvalStatusEnum.YSYBG.getCode().equals(toEvalReportProcess.getStatus())){
			  return false;
		}*/
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StartProcessInstanceVo SuspendEvalSubProcess(ServiceRestartVo vo,AjaxResponse<StartProcessInstanceVo> resp) {
		//查询评估单状态
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByEvalCode(vo.getEvaCode());
		if(toEvalReportProcess!=null && EvalStatusEnum.YCQ.getCode().equals(toEvalReportProcess.getStatus())){
			 resp.setSuccess(false);
			 resp.setMessage("当前重启流程刚完成，无需再重启！");
			 return null;
		}
		
		// 1.查询流程
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.EVAL_SERVICE_RESTART_PROCESS.getCode());
		wf.setBizCode(vo.getEvaCode());
		ToWorkFlow activeWf = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(wf);
		if (activeWf != null) {
			 resp.setSuccess(false);
			 resp.setMessage("当前重启流程尚未结束！");
			 return null;
		}
		
		
	    /** 评估相关流程挂起  */
		//activateOrSuspendProcessInstance(vo.getEvaCode(),false);
		
		//评估单更新成挂起评估
		//toEvalReportProcessService.updateEvalPropertyByEvalCode(vo.getEvaCode(),EvalPropertyEnum.PGGQ.getCode());
		
		/** 启动 评估重启流程 */
		SessionUser sessionUser = uamSessionService.getSessionUser();
		
		//根据岗位找内勤经理
		Job job = uamUserOrgService.getJobByCode(TransJobs.TNQJL.getCode(), sessionUser.getCityCode());
		List<User> userList = uamUserOrgService.getUserByJobId(job.getId());
		//String manager = toCaseInfoService.getCaseManager(vo.getCaseCode());
		if(userList==null || userList.size()==0 ){
			 resp.setSuccess(false);
			 resp.setMessage("当前用户归属主管不存在！");
			 return null;
		}
		
		Map<String, Object> vars = new HashMap<>();
		vars.put("consultant", vo.getUserName());//评估流程重启发起人
		vars.put("manager", userList.get(0).getUsername());
		StartProcessInstanceVo spv = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_SERVICE_RESTART_PROCESS.getCode()), vo.getEvaCode(), vars);
		List<TaskVo> tasks = taskService.listTasks(spv.getId(), false, vo.getUserName()).getData();
		if (tasks != null && !tasks.isEmpty()) {
			spv.setActiveTaskId(tasks.get(0).getId() + "");
		}
		wf.setCaseCode(vo.getCaseCode());
		wf.setProcessOwner(vo.getUserId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_SERVICE_RESTART_PROCESS.getCode()));
		wf.setInstCode(spv.getId());
		wf.setBizCode(vo.getEvaCode());
		wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(wf);
		return spv;
	}
	

	@Override
	public boolean apply(ServiceRestartVo vo) {
		taskService.submitTask(vo.getTaskId());
		ToApproveRecord record = new ToApproveRecord();
		record.setApproveType("10");
		record.setCaseCode(vo.getCaseCode());
		record.setContent("重启原因:"+vo.getContent());
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
		if(vo.getIsApproved()){
			handerProcessAfterServiceRestart(vo,u);//流程重启后续业务处理
			//activateOrSuspendProcessInstance(vo.getEvaCode(),true);//打开挂起流程
		}
		insertIntoApproveRecord(vo);//入审批记录表
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
		ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(t);
		if (record != null) {
			record.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(record);
		}
		vo.setUserId(user.getId());
		vo.setUserName(user.getUsername());
		
		//更改评估单状态为重启
		toEvalReportProcessService.updateStatusByEvalCode(EvalStatusEnum.YCQ.getCode(),vo.getEvaCode());
		//评估信息
		ToEvalReportProcess toEvalReportProcess =toEvalReportProcessService.findToEvalReportProcessByEvalCode(vo.getEvaCode());
		Map<String, Object> defValsMap = new HashMap<String,Object>();
    	defValsMap.put(CaseParticipantEnum.LOAN.getCode(), toEvalReportProcess.getProposeer());
    	defValsMap.put(CaseParticipantEnum.ASSISTANT.getCode(), toEvalReportProcess.getTransactor());
		
		//启动新的评估流程，入workflow表
		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_PROCESS.getCode()), 
    			vo.getEvaCode(),defValsMap);
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.EVAL_PROCESS.getCode());
		wf.setCaseCode(toEvalReportProcess.getCaseCode());
		wf.setBizCode(vo.getEvaCode());
		wf.setProcessOwner(user.getId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_PROCESS.getCode()));
		wf.setInstCode(processInstance.getId());
		wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
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
		String prefix=null;
		if(vo.getIsApproved()){
			prefix="通过:";
		}else{
			prefix="驳回原因:";
		}
		ToApproveRecord record = new ToApproveRecord();
		record.setApproveType("10");
		record.setCaseCode(vo.getCaseCode());
		record.setContent(prefix+vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(vo.getPartCode());
		record.setProcessInstance(vo.getInstCode());
		record.setNotApprove(prefix+vo.getContent());
		toApproveRecordService.insertToApproveRecord(record);		
	}

	/**
	 * 提交任务
	 * @param vo
	 */
	private void submitEvelRestartTask(ServiceRestartVo vo) {

		Map<String, Object> defValsMap = new HashMap<String,Object>();
	    defValsMap.put("is_approved", vo.getIsApproved());
		taskService.submitTask(vo.getTaskId(),defValsMap);
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.EVAL_SERVICE_RESTART_PROCESS.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setInstCode(vo.getInstCode());
		wf.setBizCode(vo.getEvaCode());
		wf.setStatus(WorkFlowStatus.COMPLETE.getCode());
		toWorkFlowService.updateWorkFlowByInstCode(wf);
	}
	
	
    
	
}
