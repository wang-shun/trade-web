package com.centaline.trans.task.service;

import com.centaline.trans.task.entity.TsMsgSendHistory;

public interface TsMsgSendHistoryService {
	
	public int insert(TsMsgSendHistory record);

	/**
	 * 功能：根据caseCode 去查询，是否给客户已经发送过短信
	 * 作者：zhangxb16
	 */
	public int countMsghistory(TsMsgSendHistory msghist);
	
}
