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
import com.centaline.trans.cases.vo.ServiceRestartVo;
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
import com.centaline.trans.eval.service.EvalServiceStopService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ActRuTaskService;
import com.centaline.trans.task.service.ToApproveRecordService;

/**
 * @Description:TODO
 * @author：jinwl6
 * @date:2017年10月16日
 * @version:
 */
@Service
public class EvalServiceStopServiceImpl implements EvalServiceStopService {
	@Autowired
	ToEvalReportProcessService toEvalReportProcessService;
	@Autowired
	TaskService taskService;
	@Autowired
	ToApproveRecordService toApproveRecordService;
	@Autowired
	UamSessionService uamSessionService;
	@Autowired
	ToWorkFlowService toWorkFlowService;
	@Autowired
	ToCaseInfoService toCaseInfoService;
	@Autowired
	ProcessInstanceService processInstanceService;
	@Autowired
	PropertyUtilsService propertyUtilsService;
	@Autowired
	ActRuTaskService actRuTaskService;
	@Autowired
	WorkFlowManager workFlowManager;
	@Autowired
	UamUserOrgService uamUserOrgService;
	
	@SuppressWarnings("unchecked")
	@Override
	public AjaxResponse<StartProcessInstanceVo> checkIsCanStop(ServiceRestartVo vo) {
		AjaxResponse<StartProcessInstanceVo> resp = new AjaxResponse<StartProcessInstanceVo>();
		SessionUser u = uamSessionService.getSessionUser();
		String userId = u.getId();
		vo.setUserId(userId);
		vo.setUserName(u.getUsername());
		vo.setOrgId(u.getServiceDepId());
        //查询评估信息
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByEvalCode(vo.getEvaCode());
		if(toEvalReportProcess==null){
			 resp.setSuccess(false);
			 resp.setMessage("此评估不存在！");
			 return resp;
		}
		/*if(EvalStatusEnum.YSYBG.getCode().equals(toEvalReportProcess.getStatus())){
			   resp.setSuccess(false);
			   resp.setMessage("此评估报告已使用，不能重启流程！");
			   return resp;
		}*/
		
		if(EvalStatusEnum.YCQ.getCode().equals(toEvalReportProcess.getStatus())){
			 resp.setSuccess(false);
			 resp.setMessage("此评估已爆单！");
			 return resp;
		}
		
		//如果流程存在爆单，说明爆单未结束
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.EVAL_SERVICE_STOP_PROCESS.getCode());
		wf.setBizCode(vo.getEvaCode());
		ToWorkFlow activeWf = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(wf);
		if (activeWf != null) {
			 resp.setSuccess(false);
			 resp.setMessage("当前爆单流程尚未结束！");
			 return resp;
		}
		
		//相关流程挂起
		//activateOrSuspendProcessInstance(vo.getEvaCode(),false);
		
		/** 启动 评估爆单流程 */
		Map<String, Object> vars = new HashMap<>();
		
		//String manager = toCaseInfoService.getCaseManager(vo.getCaseCode());
		vars.put("consultant", vo.getUserName());
		vars.put("manager", getLeaderUser(vo.getUserName()));
		StartProcessInstanceVo spv = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_SERVICE_STOP_PROCESS.getCode()), vo.getEvaCode(), vars);
		List<TaskVo> tasks = taskService.listTasks(spv.getId(), false, vo.getUserName()).getData();
		if (tasks != null && !tasks.isEmpty()) {
			spv.setActiveTaskId(tasks.get(0).getId() + "");
		}
		wf.setCaseCode(vo.getCaseCode());
		wf.setProcessOwner(vo.getUserId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_SERVICE_STOP_PROCESS.getCode()));
		wf.setInstCode(spv.getId());
		wf.setBizCode(vo.getEvaCode());
		wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(wf);
		resp.setSuccess(true);
		resp.setContent(spv);
		return resp;
	}
	
	@Override
	public AjaxResponse<StartProcessInstanceVo> applySubmit(ServiceRestartVo vo) {
		SessionUser u = uamSessionService.getSessionUser();
		vo.setUserId(u.getId());
		vo.setUserName(u.getUsername());
		AjaxResponse<StartProcessInstanceVo> resp = new AjaxResponse<StartProcessInstanceVo>();
		taskService.submitTask(vo.getTaskId());
		ToApproveRecord record = new ToApproveRecord();
		record.setApproveType("16");
		record.setCaseCode(vo.getCaseCode());
		record.setContent("爆单原因:"+vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(vo.getPartCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveRecordService.insertToApproveRecord(record);
		resp.setSuccess(true);
		return resp;
	}
	
	@Override
	public AjaxResponse<StartProcessInstanceVo> approveSubmit(ServiceRestartVo vo) {
		AjaxResponse<StartProcessInstanceVo> resp = new AjaxResponse<StartProcessInstanceVo>();
		insertIntoApproveRecord(vo);//入审批记录表
		submitEvelStopTask(vo);//爆单审批提交任务
		if(vo.getIsApproved()){
			//activateOrSuspendProcessInstance(vo.getEvaCode(),true);//打开挂起流程
			handerProcessAfterServiceStop(vo);//流程爆单后续业务处理
		}
		resp.setSuccess(true);
		return resp;
	}
	
	/**
	 * @param vo
	 */
	private void handerProcessAfterServiceStop(ServiceRestartVo vo) {
		
		//删除评估爆单 //TODO 这里待定
		List<TaskVo> taskVos = actRuTaskService.getRuTaskByBizCode(vo.getEvaCode());
		for (TaskVo task : taskVos) {
			if (WorkFlowEnum.EVAL_PROCESS.getCode().equals(task.getBusiness_key())) {
				try{
					workFlowManager.deleteProcess(task.getInstCode());
				}catch(WorkFlowException e){
                    throw e;					
				}
			}
		}
		
		ToWorkFlow t = new ToWorkFlow();
		t.setBusinessKey(WorkFlowEnum.EVAL_PROCESS.getCode());
		t.setBizCode(vo.getEvaCode());
		//workflow流程表更改为爆单
		ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(t);
		if (record != null) {
			record.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(record);
		}
		
		//更改评估单状态为爆单
		toEvalReportProcessService.updateStatusByEvalCode(EvalStatusEnum.YBD.getCode(),vo.getEvaCode());
	}

	/**
	 * 评估相关流程挂起或恢复
	 * @param instCode
	 * @param b
	 */
	private void activateOrSuspendProcessInstance(String evalCode, boolean b) {
		List<TaskVo> taskVos = actRuTaskService.getRuTaskByBizCode(evalCode);
		for (TaskVo task : taskVos) {
			if (!WorkFlowEnum.EVAL_SERVICE_STOP_PROCESS.getCode().equals(task.getBusiness_key())) {
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
		record.setApproveType("16");
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
	private void submitEvelStopTask(ServiceRestartVo vo) {

		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.EVAL_SERVICE_STOP_PROCESS.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setInstCode(vo.getInstCode());
		wf.setStatus(WorkFlowStatus.COMPLETE.getCode());
		toWorkFlowService.updateWorkFlowByInstCode(wf);
		Map<String, Object> defValsMap = new HashMap<String,Object>();
    	defValsMap.put("is_approved", vo.getIsApproved());
		taskService.submitTask(vo.getTaskId(),defValsMap);
	}
	
	/**
	 * 根据申请人找对应的主管 若申请人是权证那么找权证经理  若是内勤找内勤经理
	 * @param username
	 * @return
	 */
	public String  getLeaderUser(String username){
		Job job = null;
		User user = null;
		SessionUser sessionUser = uamSessionService.getSessionUser();
		//若是内勤找内勤经理
		if(TransJobs.TNQZL.getCode().equals(sessionUser.getServiceJobCode())){
			job = uamUserOrgService.getJobByCode(TransJobs.TNQJL.getCode(), sessionUser.getCityCode());
			user = uamUserOrgService.getUserByJobId(job.getId()).get(0);
		}else{
			// 若是权证找权证经理
			user = uamUserOrgService.getUserByUsername(username);
			user = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getOrgId(),TransJobs.TNQZL.getCode());
		}
		return user.getUsername();
	}

}
