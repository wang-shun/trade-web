package com.centaline.api.ccai.listener;

import com.aist.common.exception.BusinessException;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.api.ccai.vo.MQCaseMessage;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.common.entity.MqOpertationLog;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.*;
import com.centaline.trans.common.repository.MqOpertationLogMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.utils.WorkFlowUtils;
import com.centaline.trans.engine.vo.ExecutionVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.task.entity.ActRuEventSubScr;
import com.centaline.trans.task.repository.ActRuEventSubScrMapper;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.*;

/**
 * 导入成功的成交报告信息消费者
 * 用于监听消息队列 启动交易主流程
 *
 * @author yinchao
 * @date 2017/9/19
 */
@Component
public class FlowWorkListener {
	//案件消息队列名称
	private static final String caseQueueName = "casequeue";
	Logger logger = LoggerFactory.getLogger(FlowWorkListener.class);
	@Autowired
	ToCaseParticipantMapper participantMapper;
	@Autowired(required = true)
	private ToWorkFlowService toWorkFlowService;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private MqOpertationLogMapper logMapper;
	@Autowired
	private ActRuEventSubScrMapper actRuEventSubScrMapper;
	@Autowired
	private ToCaseMapper caseMapper;
	@Autowired
	private ToPropertyInfoMapper casePropertyInfoMapper;
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	UamMessageService uamMessageService;

	@Autowired
	private WorkFlowEngine engine;//该处使用engine 否则无法进行访问流程引擎平台
	@Value("${trade.casemq.enable:true}")
	private boolean enable;//是否自动启动流程

	@JmsListener(destination = caseQueueName)
	public void mqCaseOpertation(Message msg) {
		if (msg == null) return;
		try {
			if (msg instanceof ObjectMessage) {
				ObjectMessage objectMessage = (ObjectMessage) msg;
				//具体操作
				caseOpertation((MQCaseMessage) objectMessage.getObject());
			}
		} catch (JMSException e) {
			logger.error("change Message to Object error.", e);
		}
	}

	public static String getCaseQueueName() {
		return caseQueueName;
	}

	/**
	 * 具体的操作
	 *
	 * @param message
	 */
	private void caseOpertation(MQCaseMessage message) {
		if (!enable) {
			logger.info("get a opertation:" + message + " but service is not enable.ignore");
			return;
		}
		MqOpertationLog mqlog = new MqOpertationLog();
		mqlog.setQueueName(caseQueueName);
		mqlog.setMessage(JSONObject.toJSONString(message));
		mqlog.setOpertation(message.getType());
		mqlog.setCreateBy("SYSTEM");
		mqlog.setCreateTime(new Date());
		mqlog.setOpertationTime(new Date());
		try {
			if (MQCaseMessage.STARTFLOW_TYPE.equals(message.getType())) {
				startProcess(message.getCaseCode());
				mqlog.setStatus("0");
			} else if (MQCaseMessage.UPDATEFLOW_TYPE.equals(message.getType())) {
				updateProcess(message.getCaseCode());
				mqlog.setStatus("0");
			} else if (MQCaseMessage.REPEAL_TYPE.equals(message.getType())) {
				repealProcess(message.getCaseCode());
				mqlog.setStatus("0");
			} else {
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
	 * 开启自办贷款/评估审批流程
	 * @param caseCode
	 */
	private void startProcessLoanAndAsse(String caseCode){
		List<ToCaseParticipant> participants = participantMapper.selectByCaseCode(caseCode);
		//流程引擎相关
		Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());
		ToCaseParticipant owner = null;
		for(ToCaseParticipant pa : participants){
			//将所有的参与人 以参与人类型放入到流程引擎参数中
			defValsMap.put(pa.getPosition(), pa.getUserName());
			//有贷款权证 则贷款权证是案件拥有者 否则为过户权证
			if(owner == null && CaseParticipantEnum.WARRANT.getCode().equals(pa.getPosition())){
				owner = pa;
			}else if(CaseParticipantEnum.LOAN.getCode().equals(pa.getPosition())){
				owner = pa;
			}
		}
		if(owner!=null){
			//设置流程引擎平台登录用户 否则无法创建httpclient
			engine.setAuthUserName(owner.getUserName());
			//接单跟进人员
			defValsMap.put("receiver",owner.getUserName());
			//权证经理环节
			defValsMap.put("manager",owner.getGrpMgrUsername());
			//TODO 兼容原有流程 否则会无法启动 后面正式流程删除
			defValsMap.put("caseOwner",owner.getGrpMgrUsername());
		}else{
			throw new BusinessException("交易案件 编号["+caseCode+"] 未获取到案件贷款或过户权证信息 启动流程失败.");
		}

		ToWorkFlow toWorkFlow = new ToWorkFlow();
		//启动流程引擎
		StartProcessInstanceVo pIVo = startWorkFlowBase(propertyUtilsService.getProcessDfId(WorkFlowEnum.LOANANDASSE_PROCESS.getCode()), caseCode, defValsMap);
		toWorkFlow.setInstCode(pIVo.getId());
		toWorkFlow.setBusinessKey(WorkFlowEnum.LOANANDASSE_PROCESS.getCode());
		toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
		User user = uamUserOrgService.getUserByUsername(owner.getUserName());
		toWorkFlow.setProcessOwner(user.getId());
		toWorkFlow.setCaseCode(caseCode);
		toWorkFlow.setBizCode(caseCode);
		toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());

		toWorkFlowService.insertSelective(toWorkFlow);

		logger.info("交易案件 编号["+caseCode+"] 自办贷款/评估审批流程启动成功");
	}
	/**
	 * 开启案件交易流程
	 *
	 * @param caseCode
	 * @throws BusinessException
	 */
	private void startProcess(String caseCode) throws BusinessException {
		List<ToCaseParticipant> participants = participantMapper.selectByCaseCode(caseCode);
		//流程引擎相关
		Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());
		ToCaseParticipant owner = null;
		for (ToCaseParticipant pa : participants) {
			//将所有的参与人 以参与人类型放入到流程引擎参数中
			defValsMap.put(pa.getPosition(), pa.getUserName());
			//有贷款权证 则贷款权证是案件拥有者 否则为过户权证
			if (owner == null && CaseParticipantEnum.WARRANT.getCode().equals(pa.getPosition())) {
				owner = pa;
			} else if (CaseParticipantEnum.LOAN.getCode().equals(pa.getPosition())) {
				owner = pa;
			}
		}
		if (owner != null) {
			//设置流程引擎平台登录用户 否则无法创建httpclient
			engine.setAuthUserName(owner.getUserName());
			//接单跟进人员
			defValsMap.put("receiver", owner.getUserName());
			//权证经理环节
			defValsMap.put("manager", owner.getGrpMgrUsername());
		} else {
			throw new BusinessException("交易案件 编号[" + caseCode + "] 未获取到案件贷款或过户权证信息 启动流程失败.");
		}

		ToWorkFlow toWorkFlow = new ToWorkFlow();
		//启动流程引擎
		StartProcessInstanceVo pIVo = startWorkFlowBase(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()), caseCode, defValsMap);
		toWorkFlow.setInstCode(pIVo.getId());
		toWorkFlow.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
		User user = uamUserOrgService.getUserByUsername(owner.getUserName());
		toWorkFlow.setProcessOwner(user.getId());
		toWorkFlow.setCaseCode(caseCode);
		toWorkFlow.setBizCode(caseCode);
		toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());

		toWorkFlowService.insertSelective(toWorkFlow);

		logger.info("交易案件 编号[" + caseCode + "] 主流程启动成功");
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
	 * 向流程发送CCAI修改完成消息
	 * 并更改案件状态
	 *
	 * @param caseCode
	 */
	private void updateProcess(String caseCode) {
		//获取流程信息
		ToWorkFlow wf = new ToWorkFlow();
		wf.setCaseCode(caseCode);
		wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
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

				//更新案件参与人信息 及 案件所有人信息
				action.setVariables(updateFlowParticipants(caseCode, wordkFlowDB.getPkid()));

				//调用REST接口发送消息
				action.setExecutionId(subScrs.get(0).getExecutionId());
				engine.RESTfulWorkFlow(WorkFlowConstant.PUT_EXECUTE_KEY, ExecutionVo.class, action);
			}
			//修改案件状态为未接单
			ToCase acase = caseMapper.findToCaseByCaseCode(caseCode);
			acase.setStatus(CaseStatusEnum.WJD.getCode());//将案件重置为未接单
			caseMapper.updateByCaseCodeSelective(acase);
		}
	}

	/**
	 * 更新案件流程相关的任务环节人员信息
	 *
	 * @param caseCode
	 * @return 返回最新的人员参数设置
	 */
	private List<RestVariable> updateFlowParticipants(String caseCode, Long toWorkFlowId) {
		List<ToCaseParticipant> participants = participantMapper.selectByCaseCode(caseCode);
		List<RestVariable> variables = new ArrayList<>();
		ToCaseParticipant owner = null;
		//更新流程设置的案件人员信息
		for (ToCaseParticipant pa : participants) {
			variables.add(new RestVariable(pa.getPosition(), pa.getUserName()));
			//有贷款权证 则贷款权证是案件拥有者 否则为过户权证
			if (owner == null && CaseParticipantEnum.WARRANT.getCode().equals(pa.getPosition())) {
				owner = pa;
			} else if (CaseParticipantEnum.LOAN.getCode().equals(pa.getPosition())) {
				owner = pa;
			}
		}
		variables.add(new RestVariable("receiver", owner.getUserName()));
		variables.add(new RestVariable("manager", owner.getGrpMgrUsername()));
		ToWorkFlow toWorkFlow = new ToWorkFlow();
		toWorkFlow.setPkid(toWorkFlowId);
		//更新案件拥有人信息
		User user = uamUserOrgService.getUserByUsername(owner.getUserName());
		toWorkFlow.setProcessOwner(user.getId());
		toWorkFlowService.updateByPrimaryKeySelective(toWorkFlow);
		return variables;
	}

	/**
	 * 成交报告撤单处理
	 * @param caseCode 案件编号
	 */
	private void repealProcess(String caseCode) {
		ToPropertyInfo info = casePropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
		List<ToCaseParticipant> participants = participantMapper.selectByCaseCode(caseCode);
		Set<String> users = new HashSet<>();
		//给贷款和过户权证 以及 部门主管发送信息
		for(ToCaseParticipant pa : participants){
			if(CaseParticipantEnum.LOAN.getCode().equals(pa.getPosition())
					|| CaseParticipantEnum.WARRANT.getCode().equals(pa.getPosition())){
				User user = uamUserOrgService.getUserByUsername(pa.getUserName());
				if(user!=null){
					users.add(user.getId());
				}
			}
		}
		//TODO 后面使用模板等 将更多的信息展示给用户
		com.aist.message.core.remote.vo.Message message= new com.aist.message.core.remote.vo.Message();
		message.setTitle("成交报告撤单");//消息标题
		message.setType(MessageType.SITE);//消息类型
		message.setMsgCatagory(MsgCatagoryEnum.LOSS.getCode());
		message.setContent("案件["+caseCode+"]，物业地址："+info.getPropertyAddr()+"。\r\n在CCAI中进行了撤单!");
		message.setSenderId(uamUserOrgService.getUserByUsername("admin").getId());
		uamMessageService.sendMessageByDist(message,new ArrayList<>(users));
	}

}
