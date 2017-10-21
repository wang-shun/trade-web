package com.centaline.api.ccai.listener;

import com.aist.common.exception.BusinessException;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.service.ToCaseParticipantService;
import com.centaline.trans.common.entity.MqOpertationLog;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.*;
import com.centaline.trans.common.repository.MqOpertationLogMapper;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;
import com.centaline.trans.eloan.service.ToSelfAppInfoService;
import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.utils.WorkFlowUtils;
import com.centaline.trans.engine.vo.ExecutionVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.message.activemq.vo.MQCaseMessage;
import com.centaline.trans.message.activemq.vo.MQEvalMessage;
import com.centaline.trans.task.entity.ActRuEventSubScr;
import com.centaline.trans.task.repository.ActRuEventSubScrMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 导入成功的评估信息消费者
 * 用于监听消息队列 启动评估相关流程
 * @author yinchao
 * @date 2017-10-18
 */
@Component
public class EvalFlowWorkListener {
	/**
	 * 需要同步修改队列名称的地方
	 * @see com.centaline.trans.eval.service.impl.ToEvalRebateServiceImpl#startRebateFlow(String, String)
	 */
	//案件消息队列名称
	private static final String evalQueueName = "evalqueue";
	Logger logger = LoggerFactory.getLogger(EvalFlowWorkListener.class);

	@Autowired
	private ToPropertyInfoService toPropertyInfoService;

	@Autowired
	private ToCaseParticipantService toCaseParticipantService;

	@Autowired
	private ToEvalRebateService toEvalRebateService;

	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;

	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Autowired(required = true)
	private ToWorkFlowService toWorkFlowService;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private MqOpertationLogMapper logMapper;
	@Autowired
	private ActRuEventSubScrMapper actRuEventSubScrMapper;
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	UamMessageService uamMessageService;
	@Value("${trade.casemq.enable:true}")
	private boolean enable;//是否自动启动流程

	@Autowired
	private WorkFlowEngine engine;//该处使用engine 否则无法进行访问流程引擎平台

	@Autowired
	private ToEvalReportProcessService toEvalReportProcessService;
	
	
	@Autowired
	private ToSelfAppInfoService toSelfAppInfoService;
	
	@JmsListener(destination = evalQueueName)
	public void mqEvalOpertation(Message msg) {
		if (msg == null) return;
		try {
			if (msg instanceof ObjectMessage) {
				ObjectMessage objectMessage = (ObjectMessage) msg;
				//具体操作
				evalOpertation((MQEvalMessage) objectMessage.getObject());
			}
		} catch (JMSException e) {
			logger.error("change Message to Object error.", e);
		}
	}

	public static String getEvalQueueName() {
		return evalQueueName;
	}

	/**
	 * 具体的操作
	 *
	 * @param message
	 */
	private void evalOpertation(MQEvalMessage message) {
		if (!enable) {
			logger.info("get a opertation:" + message + " but service is not enable.ignore");
			return;
		}
		MqOpertationLog mqlog = new MqOpertationLog();
		mqlog.setQueueName(evalQueueName);
		mqlog.setMessage(JSONObject.toJSONString(message));
		mqlog.setOpertation(message.getType());
		mqlog.setCreateBy("SYSTEM");
		mqlog.setCreateTime(new Date());
		mqlog.setOpertationTime(new Date());
		try {
			//评估返利相关处理
			if (WorkFlowEnum.EVAL_REBATE_PROCESS.getCode().equals(message.getFlowType())) {
				if(MQEvalMessage.STARTFLOW_TYPE.equals(message.getType())){
					startRebateProcess(message.getEvalCode());//启动流程
				}else if(MQEvalMessage.UPDATEFLOW_TYPE.equals(message.getType())){
					updateRebateProcess(message.getEvalCode());//驳回修改完成
				}else if(MQEvalMessage.SENDMESSAGE_TYPE.equals(message.getType())){
					rebateSendMessage(message.getEvalCode());
				}else{
					throw new BusinessException("评估返利消息:未识别的操作.");
				}
				mqlog.setStatus("0");
			}else if(WorkFlowEnum.ASSE_PROCESS.getCode().equals(message.getFlowType())){
				//自办评估
				if(MQEvalMessage.STARTFLOW_TYPE.equals(message.getType())){
					startProcessAss(message.getEvalCode());
				}else if(MQEvalMessage.UPDATEFLOW_TYPE.equals(message.getType())){
					updateProcessAss(message.getEvalCode());
				}else{
					throw new BusinessException("自办评估消息:未识别的操作.");
				}
				mqlog.setStatus("0");
			}else if(WorkFlowEnum.LOANANDASSE_PROCESS.equals(message.getFlowType())){
				//自办贷款
				if(MQEvalMessage.STARTFLOW_TYPE.equals(message.getType())){
					startProcessLoan(message.getEvalCode());
				}else if(MQEvalMessage.STARTFLOW_TYPE.equals(message.getType())){
					updateProcessLoan(message.getEvalCode());
				}else{
					throw new BusinessException("自办贷款消息:未识别的操作.");
				}
				mqlog.setStatus("0");
			}else if(MQCaseMessage.EVAREFUND_TYPE.equals(message.getType())){
				startProcessEvaRefund(message.getEvalCode());
				mqlog.setStatus("0");
			}else {
				mqlog.setOpertation(message.getType());
				mqlog.setStatus("-1");
				mqlog.setErrmsg("未识别的操作.");
			}
		} catch (Exception e) {
			mqlog.setStatus("-1");
			mqlog.setErrmsg(e.getMessage());
		}
		logMapper.insertSelective(mqlog);
	}
	
	/**
	 * 修改自办贷款审批流程
	 * @param evalCode
	 */
	private void updateProcessLoan(String caseCode) {
		//获取流程信息
		ToWorkFlow wf = new ToWorkFlow();
		wf.setCaseCode(caseCode);
		wf.setBusinessKey(WorkFlowEnum.LOANANDASSE_PROCESS.getCode());
		ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if (wordkFlowDB != null) {
			// 发送消息
			ActRuEventSubScr event = new ActRuEventSubScr();
			event.setEventType(MessageEnum.CCAI_MODIFY_MSG.getEventType());
			event.setEventName(MessageEnum.CCAI_MODIFY_MSG.getName());
			event.setProcInstId(wordkFlowDB.getInstCode());
			event.setActivityId(EventTypeEnum.SELF_LOAN_MSG_EVENT_CATCH.getName());
			ExecuteAction action = new ExecuteAction();
			action.setAction(EventTypeEnum.SELF_LOAN_MSG_EVENT_CATCH.getEventType());
			action.setMessageName(MessageEnum.CCAI_MODIFY_MSG.getName());
			List<ActRuEventSubScr> subScrs = actRuEventSubScrMapper.listBySelective(event);
			if (CollectionUtils.isNotEmpty(subScrs)) {
				//设置流程引擎登录用户 否则无法访问REST接口
				User user = uamUserOrgService.getUserById(wordkFlowDB.getProcessOwner());
				engine.setAuthUserName(user.getUsername());
				//调用REST接口发送消息
				action.setExecutionId(subScrs.get(0).getExecutionId());
				engine.RESTfulWorkFlow(WorkFlowConstant.PUT_EXECUTE_KEY, ExecutionVo.class, action);
			}
			String type = "自办贷款";
			ToSelfAppInfo  toSelfAppInfo = toSelfAppInfoService.getAppInfoByCaseCode(caseCode, type);
			if(toSelfAppInfo != null){
				toSelfAppInfo.setStatus(SelfDoStatusEnum.DOING.getCode());
				toSelfAppInfoService.updateByPrimaryKeySelective(toSelfAppInfo);
			}else{
				throw new BusinessException("获取自办审批失败["+caseCode+"]!");
			}
		}
		
	}

	/**
	 * 修改自办评估审批流程
	 * @param evalCode
	 */
	private void updateProcessAss(String caseCode) {
		ToWorkFlow wf = new ToWorkFlow();
		wf.setCaseCode(caseCode);
		wf.setBusinessKey(WorkFlowEnum.ASSE_PROCESS.getCode());
		ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if (wordkFlowDB != null) {
			// 发送消息
			ActRuEventSubScr event = new ActRuEventSubScr();
			event.setEventType(MessageEnum.CCAI_MODIFY_ASS_MSG.getEventType());
			event.setEventName(MessageEnum.CCAI_MODIFY_ASS_MSG.getName());
			event.setProcInstId(wordkFlowDB.getInstCode());
			event.setActivityId(EventTypeEnum.SELF_ASSE_MSG_EVENT_CATCH.getName());
			ExecuteAction action = new ExecuteAction();
			action.setAction(EventTypeEnum.SELF_ASSE_MSG_EVENT_CATCH.getEventType());
			action.setMessageName(MessageEnum.CCAI_MODIFY_ASS_MSG.getName());
			List<ActRuEventSubScr> subScrs = actRuEventSubScrMapper.listBySelective(event);
			if (CollectionUtils.isNotEmpty(subScrs)) {
				//设置流程引擎登录用户 否则无法访问REST接口
				User user = uamUserOrgService.getUserById(wordkFlowDB.getProcessOwner());
				engine.setAuthUserName(user.getUsername());
				//调用REST接口发送消息
				action.setExecutionId(subScrs.get(0).getExecutionId());
				engine.RESTfulWorkFlow(WorkFlowConstant.PUT_EXECUTE_KEY, ExecutionVo.class, action);
			}
			String type = "自办评估";
			ToSelfAppInfo  toSelfAppInfo = toSelfAppInfoService.getAppInfoByCaseCode(caseCode, type);
			if(toSelfAppInfo != null){
				toSelfAppInfo.setStatus(SelfDoStatusEnum.DOING.getCode());
				toSelfAppInfoService.updateByPrimaryKeySelective(toSelfAppInfo);
			}else{
				throw new BusinessException("获取自办审批失败["+caseCode+"]!");
			}
		}
	}

	/**
	 * 开启评估返利流程
	 *
	 * @param evalCode 目前为案件编号 一个案件只有一个评估返利
	 * @throws BusinessException
	 */
	private void startRebateProcess(String evalCode){
		ToEvalRebate rebate = toEvalRebateService.findToEvalRebateByCaseCode(evalCode);
		if(StringUtils.isBlank(rebate.getEvaCode())){
			throw new BusinessException("评估返利需要先关联评估，才能发起流程!");
		}
		//获取评估返利对应案件所属组别内勤信息
		User user = toCaseParticipantService.findCaseAssistant(evalCode);
		if(user!=null){
			//设置流程引擎平台登录用户 否则无法创建httpclient
			engine.setAuthUserName(user.getUsername());
			//流程引擎相关
			Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.EVAL_REBATE_PROCESS.getCode());
			defValsMap.put("assign",user.getUsername());
			ToWorkFlow toWorkFlow = new ToWorkFlow();
			//启动流程引擎
			StartProcessInstanceVo pIVo = startWorkFlowBase(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_REBATE_PROCESS.getCode(),user.getOrgId()), rebate.getCaseCode(), defValsMap);
			toWorkFlow.setInstCode(pIVo.getId());
			toWorkFlow.setBusinessKey(WorkFlowEnum.EVAL_REBATE_PROCESS.getCode());
			toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
			toWorkFlow.setProcessOwner(user.getId());
			toWorkFlow.setCaseCode(rebate.getCaseCode());
			toWorkFlow.setBizCode(rebate.getEvaCode());
			toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
			toWorkFlowService.insertSelective(toWorkFlow);

			logger.info("评估返利 编号[" + evalCode + "] 流程启动成功");
		}else{
			throw new BusinessException("["+evalCode+"]案件未获取到内勤助理");
		}
	}

	/**
	 * 向流程发送评估返利修改完成
	 * 并更改案件状态
	 *
	 * @param evalCode 目前为案件编号 一个案件只有一个评估返利
	 */
	private void updateRebateProcess(String evalCode) {
		//获取流程信息
		ToWorkFlow wf = new ToWorkFlow();
		wf.setCaseCode(evalCode);
		wf.setBusinessKey(WorkFlowEnum.EVAL_REBATE_PROCESS.getCode());
		ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if (wordkFlowDB != null) {
			// 发送消息
			ActRuEventSubScr event = new ActRuEventSubScr();
			event.setEventType(MessageEnum.CCAI_UPDATED_MSG.getEventType());
			event.setEventName(MessageEnum.CCAI_UPDATED_MSG.getName());
			event.setProcInstId(wordkFlowDB.getInstCode());
			event.setActivityId(EventTypeEnum.CCAI_UPDATED_MSG_EVENT_CATCH.getName());
			ExecuteAction action = new ExecuteAction();
			action.setAction(EventTypeEnum.CCAI_UPDATED_MSG_EVENT_CATCH.getEventType());
			action.setMessageName(MessageEnum.CCAI_UPDATED_MSG.getName());
			List<ActRuEventSubScr> subScrs = actRuEventSubScrMapper.listBySelective(event);
			if (CollectionUtils.isNotEmpty(subScrs)) {
				//设置流程引擎登录用户 否则无法访问REST接口
				User user = uamUserOrgService.getUserById(wordkFlowDB.getProcessOwner());
				engine.setAuthUserName(user.getUsername());
				//调用REST接口发送消息
				action.setExecutionId(subScrs.get(0).getExecutionId());
				engine.RESTfulWorkFlow(WorkFlowConstant.PUT_EXECUTE_KEY, ExecutionVo.class, action);
			}
			//修改评估返利单状态为审批中
			ToEvalRebate rebate = toEvalRebateService.findToEvalRebateByCaseCode(evalCode);
			if(rebate!=null){
				rebate.setStatus(EvalRebateStatusEnum.DOING.getCode());
				toEvalRebateService.updateByPrimaryKeySelective(rebate);
			}else{
				throw new BusinessException("获取评估返利单失败["+evalCode+"]!");
			}
		}
	}

	/**
	 * 给权证内勤 和 贷款 发送消息
	 * 有新的评估返利申请，需要先录入评估信息
	 * @param evalCode 目前为案件编号 一个案件只有一个评估返利
	 */
	private void rebateSendMessage(String evalCode){
		//获取评估返利对应案件所属组别内勤信息
		User assistant = toCaseParticipantService.findCaseAssistant(evalCode);
		User loan = toCaseParticipantService.findCaseLoan(evalCode);
		if(assistant!=null&&loan!=null){
			//案件物业信息
			ToPropertyInfo info = toPropertyInfoService.findToPropertyInfoByCaseCode(evalCode);
			List<String> users = new ArrayList<>();
			users.add(assistant.getId());
			users.add(loan.getId());
			//TODO 后面使用模板等 将更多的信息展示给用户
			com.aist.message.core.remote.vo.Message message= new com.aist.message.core.remote.vo.Message();
			message.setTitle("评估返利申请");//消息标题
			message.setType(MessageType.SITE);//消息类型
			message.setMsgCatagory(MsgCatagoryEnum.LOSS.getCode());
			message.setContent("案件["+evalCode+"]，物业地址："+info.getPropertyAddr()+"。\r\n在CCAI中进行了评估返利申请!但系统中还未发起评估申请，请先发起评估申请，才能看到返利申请信息!");
			message.setSenderId(uamUserOrgService.getUserByUsername("admin").getId());
			uamMessageService.sendMessageByDist(message,new ArrayList<>(users));
		}else{
			throw new BusinessException("评估返利 发送信息["+evalCode+"]案件下未获取到内勤助理或贷款权证信息");
		}
	}


	/**
	 * 流程启动
	 * 主要用于在启动流程前设置用户 否则httpclien会报控制空指针 复制原方法见see
	 *
	 * @param processDefinitionId
	 * @param businessKey
	 * @param vars
	 * @return
	 * @see com.centaline.trans.engine.service.impl.ProcessInstanceServiceImpl#startWorkFlowByDfId(String, String, Map)
	 */
	private StartProcessInstanceVo startWorkFlowBase(String processDefinitionId, String businessKey, Map<String, Object> vars) {
		ProcessInstance process = new ProcessInstance();
		process.setProcessDefinitionId(processDefinitionId);
		process.setBusinessKey(businessKey);
		process.setVariables(WorkFlowUtils.mapToVarList(vars));
		StartProcessInstanceVo obj = (StartProcessInstanceVo) engine
				.RESTfulWorkFlow(WorkFlowConstant.START_PROCESS_INSTANCE_KEY, StartProcessInstanceVo.class, process);
		return obj;
	}
	
	/**
	 * 开启自办贷款/评估审批流程
	 * @param caseCode
	 */
	private void startProcessAss(String caseCode){
		ToCaseInfo info = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		//获取评估返利对应案件所属组别内勤信息
		Org org = uamUserOrgService.getOrgByCode(info.getArCode());
		List<User> users = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),TransJobs.QZJL.getCode());
		if(users!=null&&users.size()>0){
			//防止报错 取第一个内勤作为办理人
			User assign = users.get(0);
			//设置流程引擎平台登录用户 否则无法创建httpclient
			engine.setAuthUserName(assign.getUsername());
			//流程引擎相关
			Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.ASSE_PROCESS.getCode());
			User user = users.get(0);
			defValsMap.put("manager",user.getUsername());
			defValsMap.put("chief",user.getUsername());
			ToWorkFlow toWorkFlow = new ToWorkFlow();
			//启动流程引擎
			StartProcessInstanceVo pIVo = startWorkFlowBase(propertyUtilsService.getProcessDfId(WorkFlowEnum.ASSE_PROCESS.getCode(),org.getId()), caseCode, defValsMap);
			toWorkFlow.setInstCode(pIVo.getId());
			toWorkFlow.setBusinessKey(WorkFlowEnum.ASSE_PROCESS.getCode());
			toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
			toWorkFlow.setProcessOwner(assign.getId());
			toWorkFlow.setCaseCode(caseCode);
			toWorkFlow.setBizCode(caseCode);
			toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
			toWorkFlowService.insertSelective(toWorkFlow);
		
			logger.info("自办评估审批编号[" + caseCode + "] 流程启动成功");
		}else{
			throw new BusinessException(org.getOrgName()+"["+info.getArCode()+"]部门下未获取到权证总监");
		}
	}

	
	/**
	 * 开启评估退费流程
	 * @param caseCode
	 */
	private void startProcessEvaRefund(String caseCode) {
		ToCaseInfo info = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
		if(toEvalReportProcess != null && StringUtils.isNotBlank(toEvalReportProcess.getEvaCode())){
			//获取评估返利对应案件所属组别内勤信息
			Org org = uamUserOrgService.getOrgByCode(info.getArCode());
			List<User> users = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),TransJobs.TNQZL.getCode());
			if(users!=null&&users.size()>0){
				//防止报错 取第一个内勤作为办理人
				User assign = users.get(0);
				//设置流程引擎平台登录用户 否则无法创建httpclient
				engine.setAuthUserName(assign.getUsername());
				//流程引擎相关
				Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.EVALREFUND_PROCESS.getCode());
				defValsMap.put("assistant", users.get(0).getUsername());
				defValsMap.put("manager",users.get(0).getUsername());
				defValsMap.put("chief",users.get(0).getUsername());
				ToWorkFlow toWorkFlow = new ToWorkFlow();
				//启动流程引擎
				StartProcessInstanceVo pIVo = startWorkFlowBase(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVALREFUND_PROCESS.getCode(),org.getId()), caseCode, defValsMap);
				toWorkFlow.setInstCode(pIVo.getId());
				toWorkFlow.setBusinessKey(WorkFlowEnum.EVALREFUND_PROCESS.getCode());
				toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
				toWorkFlow.setProcessOwner(assign.getId());
				toWorkFlow.setCaseCode(caseCode);
				toWorkFlow.setBizCode(caseCode);
				toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
				toWorkFlowService.insertSelective(toWorkFlow);
				logger.info("评估退费编号[" + caseCode + "] 流程启动成功");
			}else{
				throw new BusinessException(org.getOrgName()+"["+info.getArCode()+"]部门下未获取到权证总监");
			}
		}else{
			throw new BusinessException("报告类评估信息["+caseCode+"]不存在！");
		}
	}
	
	/**
	 * 开启自办评估审批流程
	 * @param caseCode
	 */
	private void startProcessLoan(String caseCode) {
		ToCaseInfo info = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		//获取评估返利对应案件所属组别内勤信息
		Org org = uamUserOrgService.getOrgByCode(info.getArCode());
		List<User> users = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),TransJobs.QZJL.getCode());
		if(users!=null&&users.size()>0){
			//防止报错 取第一个内勤作为办理人
			User assign = users.get(0);
			//设置流程引擎平台登录用户 否则无法创建httpclient
			engine.setAuthUserName(assign.getUsername());
			//流程引擎相关
			Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.LOANANDASSE_PROCESS.getCode());
			defValsMap.put("manager",users.get(0).getUsername());
			defValsMap.put("chief",users.get(0).getUsername());
			ToWorkFlow toWorkFlow = new ToWorkFlow();
			//启动流程引擎
			StartProcessInstanceVo pIVo = startWorkFlowBase(propertyUtilsService.getProcessDfId(WorkFlowEnum.LOANANDASSE_PROCESS.getCode(),org.getId()), caseCode, defValsMap);
			toWorkFlow.setInstCode(pIVo.getId());
			toWorkFlow.setBusinessKey(WorkFlowEnum.LOANANDASSE_PROCESS.getCode());
			toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
			toWorkFlow.setProcessOwner(assign.getId());
			toWorkFlow.setCaseCode(caseCode);
			toWorkFlow.setBizCode(caseCode);
			toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
			toWorkFlowService.insertSelective(toWorkFlow);
		
			logger.info("自办贷款审批编号[" + caseCode + "] 流程启动成功");
		}else{
			throw new BusinessException(org.getOrgName()+"["+info.getArCode()+"]部门下未获取到权证总监");
		}
		
	}

}
