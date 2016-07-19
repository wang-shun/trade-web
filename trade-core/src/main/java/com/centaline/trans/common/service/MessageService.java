package com.centaline.trans.common.service;

import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.task.entity.ActRuEventSubScr;

public interface MessageService {
	
	/***
	 *  由中间捕获事件发送无贷款消息
	 */
	void sendMortgageFinishMsgByIntermi(String instanceId);
	
	
	/**
	 *  由过户边界事件发送消息
	 */
	void sendMortgageSelectMsgByBoudary(String instanceId);
	
	/***
	 *  基础消息发送事件
	 * 
	 * @param event
	 * @param action
	 */
	void sendMessage(ActRuEventSubScr event,ExecuteAction action);

}
