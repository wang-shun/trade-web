package com.centaline.trans.common.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.enums.EventTypeEnum;
import com.centaline.trans.common.enums.MessageEnum;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ActRuEventSubScr;
import com.centaline.trans.task.repository.ActRuEventSubScrMapper;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private ActRuEventSubScrMapper actRuEventSubScrMapper;

	@Override
	public void sendMortgageFinishMsgByIntermi(String instanceId) {
		// 发送消息
		ActRuEventSubScr event = new ActRuEventSubScr();
		event.setEventType(MessageEnum.MORTGAGE_FINISH_MSG.getEventType());
		event.setEventName(MessageEnum.MORTGAGE_FINISH_MSG.getName());
		event.setProcInstId(instanceId);
		event.setActivityId(EventTypeEnum.INTERMEDIATECATCHEVENT.getName());
		
		ExecuteAction action = new ExecuteAction();
		action.setAction(EventTypeEnum.INTERMEDIATECATCHEVENT.getEventType());
		action.setMessageName(MessageEnum.MORTGAGE_FINISH_MSG.getName());

		sendMessage(event,action);
	}
	
	@Override
	public void sendSpvFinishMsgByIntermi(String instanceId) {
		// 发送消息
		ActRuEventSubScr event = new ActRuEventSubScr();
		event.setEventType(MessageEnum.SPV_FINISH_MSG.getEventType());
		event.setEventName(MessageEnum.SPV_FINISH_MSG.getName());
		event.setProcInstId(instanceId);
		event.setActivityId(EventTypeEnum.SPVFINISHEVENTCATCH.getName());
		
		ExecuteAction action = new ExecuteAction();
		action.setAction(EventTypeEnum.SPVFINISHEVENTCATCH.getEventType());
		action.setMessageName(MessageEnum.SPV_FINISH_MSG.getName());

		sendMessage(event,action);
	}
	
	@Override
	public void sendSatisFinishMsgByIntermi(String instanceId) {
		// 发送消息
		ActRuEventSubScr event = new ActRuEventSubScr();
		event.setEventType(MessageEnum.SATIS_FINISH_MSG.getEventType());
		event.setEventName(MessageEnum.SATIS_FINISH_MSG.getName());
		event.setProcInstId(instanceId);
		event.setActivityId(EventTypeEnum.SATISFINISHEVENTCATCH.getName());
		
		ExecuteAction action = new ExecuteAction();
		action.setAction(EventTypeEnum.SATISFINISHEVENTCATCH.getEventType());
		action.setMessageName(MessageEnum.SATIS_FINISH_MSG.getName());

		sendMessage(event,action);
	}

	@Override
	public void sendMortgageSelectMsgByBoudary(String instanceId) {
		ActRuEventSubScr event = new ActRuEventSubScr();
		event.setEventType(MessageEnum.START_MORTGAGE_SELECT_MSG.getEventType());
		event.setEventName(MessageEnum.START_MORTGAGE_SELECT_MSG.getName());
		event.setProcInstId(instanceId);
		event.setActivityId(EventTypeEnum.TRADEBOUNDARYMSG.getName());
		
		ExecuteAction action = new ExecuteAction();
		action.setAction(EventTypeEnum.TRADEBOUNDARYMSG.getEventType());
		action.setMessageName(MessageEnum.START_MORTGAGE_SELECT_MSG.getName());
		
		sendMessage(event,action);
	}

	@Override
	public void sendMessage(ActRuEventSubScr event, ExecuteAction action) {
		List<ActRuEventSubScr> subScrs= actRuEventSubScrMapper.listBySelective(event);
		if(CollectionUtils.isNotEmpty(subScrs)) {
			for(ActRuEventSubScr subScr : subScrs) {
				action.setExecutionId(subScr.getExecutionId());
				workFlowManager.executeAction(action);
			}
		}
	}

	@Override
	public void sendMortgageSelectMsgByBoudary(String instanceId, List<RestVariable> variables) {
		ActRuEventSubScr event = new ActRuEventSubScr();
		event.setEventType(MessageEnum.START_MORTGAGE_SELECT_MSG.getEventType());
		event.setEventName(MessageEnum.START_MORTGAGE_SELECT_MSG.getName());
		event.setProcInstId(instanceId);
		event.setActivityId(EventTypeEnum.TRADEBOUNDARYMSG.getName());
		
		ExecuteAction action = new ExecuteAction();
		action.setAction(EventTypeEnum.TRADEBOUNDARYMSG.getEventType());
		action.setMessageName(MessageEnum.START_MORTGAGE_SELECT_MSG.getName());
		action.setVariables(variables);
		
		sendMessage(event,action);
	}
	
	/*
	 * @author:zhuody
	 * @date: 2017-03-27
	 * @des: 分级银行审批通过发送信息
	 * 
	 */
	@Override
	public void sendBankLevelApproveMsg(String instanceId,boolean approveFlag) {
		// 发送消息
		ActRuEventSubScr event = new ActRuEventSubScr();
		event.setEventType(MessageEnum.BANK_LEVEL_APPROVE_MSG.getEventType());
		event.setEventName(MessageEnum.BANK_LEVEL_APPROVE_MSG.getName());
		event.setProcInstId(instanceId);
		event.setActivityId(EventTypeEnum.BANKLEVELAPPROVEEVENTCATCH.getName());
				
		ExecuteAction action = new ExecuteAction();
		action.setAction(EventTypeEnum.BANKLEVELAPPROVEEVENTCATCH.getEventType());
		action.setMessageName(MessageEnum.BANK_LEVEL_APPROVE_MSG.getName());
		
		//添加消息标示
/*		List<RestVariable> variables = new ArrayList<RestVariable>();
		variables.add(new RestVariable("approveFlag",approveFlag));
		action.setVariables(variables);*/

		sendMessage(event,action);
		
	}
}
