package com.centaline.trans.cases.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.service.CaseStopService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.enums.CaseStopPartCodeEnnum;
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
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ActRuTaskService;
import com.centaline.trans.task.service.ToApproveRecordService;

/**
 * 案件爆单service impl
 * @author wbcaiyx
 *
 */
@Service
public class CaseStopServiceImpl implements CaseStopService {

	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private ActRuTaskService actRuTaskService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private ToCaseInfoService toCaseInfoService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private ToApproveRecordService toApproveRecordService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ToCaseMapper toCaseMapper;
	@Autowired
	private UamTemplateService uamTemplateService;
	@Autowired
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;
	
	@Override
	public AjaxResponse<StartProcessInstanceVo> checkIsCanStop(String caseCode) {
		AjaxResponse<StartProcessInstanceVo> result = new AjaxResponse<StartProcessInstanceVo>();
		//check是否是结案
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		if(toCase == null){
			result.setSuccess(false);
			result.setMessage("查无此案件!");
			return result;
		}
		if(CasePropertyEnum.TPJA.getCode().equals(toCase.getCaseProperty()) 
				||CasePropertyEnum.TPBD.getCode().equals(toCase.getCaseProperty())){
			result.setSuccess(false);
			result.setMessage("案件已结案或已爆单!");
			return result;
		}
		if(CaseStatusEnum.YGH.getCode().equals(toCase.getStatus())
				||CaseStatusEnum.YLTZ.getCode().equals(toCase.getStatus())
				||CaseStatusEnum.YJY.getCode().equals(toCase.getStatus())){
			result.setSuccess(false);
			result.setMessage("已过户的案件不可爆单");
			return result;
		}
		//检查是否正在爆单流程中
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.CASE_STOP_PROCESS.getCode());
		wf.setCaseCode(toCase.getCaseCode());
		ToWorkFlow awf = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if (awf != null) {
			 result.setSuccess(false);
			 result.setMessage("当前已存在未结束的爆单流程");
			 return result;
		}
		//案件相关主流程全部挂起
		activateOrSuspendProcess(caseCode, false);
		
		//案件爆单流程启动
		SessionUser user = uamSessionService.getSessionUser();
		Map<String, Object> vars = new HashMap<>();
		//权证主管
		String manager = toCaseInfoService.getCaseManager(caseCode);
		vars.put("consultant", user.getUsername());
		vars.put("manager", manager);
		StartProcessInstanceVo spv = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getProcessDfId(WorkFlowEnum.CASE_STOP_PROCESS.getCode()), caseCode, vars);
		List<TaskVo> tasks = taskService.listTasks(spv.getId(), false, user.getUsername()).getData();
		if (tasks != null && !tasks.isEmpty()) {
			spv.setActiveTaskId(tasks.get(0).getId() + "");
		}
		wf.setBizCode(toCase.getCaseCode());
		wf.setProcessOwner(user.getId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.CASE_STOP_PROCESS.getCode()));
		wf.setInstCode(spv.getId());
		wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(wf);
		result.setSuccess(true);
		result.setContent(spv);
		
		return result;
	}
	
	@Override
	public AjaxResponse<String> applySubmit(ServiceRestartVo vo) {
		AjaxResponse<String> result = new AjaxResponse<String>();
		taskService.submitTask(vo.getTaskId());
		
		SessionUser user = uamSessionService.getSessionUser();
		ToApproveRecord record = new ToApproveRecord();
		record.setApproveType("12");//案件爆单审批类型
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(user.getId());
		record.setOperatorTime(new Date());
		record.setPartCode(CaseStopPartCodeEnnum.CASE_STOP_APPLY.getCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveRecordService.insertToApproveRecord(record);
		
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public AjaxResponse<String> approveSubmit(ServiceRestartVo vo) {
		AjaxResponse<String> result = new AjaxResponse<String>();
		//入审批记录表
		insertIntoApproveRecord(vo);
		//爆单审批提交任务
		submitCaseStopTask(vo);
		
		activateOrSuspendProcess(vo.getCaseCode(),true);//恢复挂起流程
		if(vo.getIsApproved()){
			handleOthers(vo);//流程爆单后续业务处理
			//站内信发送
			sendMsg(vo, true);	   
		}else {
			sendMsg(vo, false);	    
		}
		result.setSuccess(true);
		return result;
	}


	/**
	 * 案件相关流程挂起或恢复
	 */
	private void activateOrSuspendProcess(String caseCode, boolean active) {
		//只操作主流程相关
		List<TaskVo> taskVos = actRuTaskService.getRuTaskByBizCode(caseCode);
		for (TaskVo task : taskVos) {
			if (!WorkFlowEnum.CASE_STOP_PROCESS.getCode().equals(task.getBusiness_key())) {
				if(active){
					 if(!task.getSuspended()) {
						 continue;
					 }
					 workFlowManager.activateOrSuspendProcessInstance(task.getInstCode(), active);
				}else{
					if(task.getSuspended()) {
						continue;
					}
					workFlowManager.activateOrSuspendProcessInstance(task.getInstCode(), active);
				}	
			}
		}
	}
	
	/**
	 * 案件爆单后处理
	 * @param vo
	 */
	private void handleOthers(ServiceRestartVo vo) {
		
		//TODO 删除主流程相关,按规范,BizCode主流程相关都为案件编号,通过此筛选删除
		List<TaskVo> taskVos = actRuTaskService.getRuTaskByBizCode(vo.getCaseCode());
		for (TaskVo task : taskVos) {
			if (!WorkFlowEnum.CASE_STOP_PROCESS.getCode().equals(task.getBusiness_key())) {
				try{
					workFlowManager.deleteProcess(task.getInstCode());
				}catch(WorkFlowException e){
                    throw e;					
				}
			}
		}
		//更改案件状态为爆单
		ToCase tocase = new ToCase();
		tocase.setCaseCode(vo.getCaseCode());
		tocase.setCaseProperty(CasePropertyEnum.TPBD.getCode());
		toCaseMapper.updateByCaseCodeSelective(tocase);
		
		//workflow流程表更改为爆单
		ToWorkFlow t = new ToWorkFlow();
		t.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		t.setCaseCode(vo.getCaseCode());
		ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(t);
		if (record != null) {
			record.setStatus(WorkFlowStatus.BAODAN.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(record);
		}
	}
	
	/**
	 * 案件爆单审批记录
	 * @param vo
	 */
	private void insertIntoApproveRecord(ServiceRestartVo vo) {
		SessionUser user = uamSessionService.getSessionUser();
		
		ToApproveRecord record = new ToApproveRecord();
		record.setApproveType("13");//案件爆单审批类型
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(user.getId());
		record.setOperatorTime(new Date());
		record.setPartCode(CaseStopPartCodeEnnum.CASE_STOP_APPROVE.getCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveRecordService.insertToApproveRecord(record);		
	}
	
	/**
	 * 案件爆单审批提交
	 * @param vo
	 */
	private void submitCaseStopTask(ServiceRestartVo vo) {

		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.CASE_STOP_PROCESS.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setInstCode(vo.getInstCode());
		wf.setStatus(WorkFlowStatus.COMPLETE.getCode());
		toWorkFlowService.updateWorkFlowByInstCode(wf);
		
		taskService.submitTask(vo.getTaskId());
	}

	private void sendMsg(ServiceRestartVo vo,boolean isApprove){
		//站内信通知
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("case_code", vo.getCaseCode());
		if(isApprove){
			params.put("approve_result", "通过");
		}else{
			params.put("approve_result", "未通过");
		}
		
		String content = uamTemplateService.mergeTemplate(MsgLampEnum.CASE_STOP_REMINDER.getCode(), params);
	    Message message = new Message();
	    // 消息标题
	    message.setTitle(MsgLampEnum.CASE_STOP_REMINDER.getName());
	    // 消息类型
	    message.setType(MessageType.SITE);
	    //设置提醒列别 
	    message.setMsgCatagory(MsgCatagoryEnum.NEWS.getCode());
	    //内容 
	    message.setContent(content);
	    // 发送人 
	    String senderId = uamSessionService.getSessionUser().getId();
	    //设置发送人 
	    message.setSenderId(senderId);
	    ToApproveRecord  toApproveRecord = new ToApproveRecord();
	    toApproveRecord.setCaseCode(vo.getCaseCode());
	    toApproveRecord.setProcessInstance(vo.getInstCode());
	    toApproveRecord.setApproveType("12");
	    ToApproveRecord record = toApproveRecordService.queryToApproveRecord(toApproveRecord);

	    if(record != null){
	    	uamMessageService.sendMessageByUser(message, record.getOperator());
	    }
	}

}
