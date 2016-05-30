package com.centaline.trans.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.TsMsgSendHistory;
import com.centaline.trans.task.repository.TsMsgSendHistoryMapper;
import com.centaline.trans.task.service.TsMsgSendHistoryService;


@Service
public class TsMsgSendHistoryServiceImpl implements TsMsgSendHistoryService{

	@Autowired
	private TsMsgSendHistoryMapper tsmsgSendHistoryMapper;
	
	
	@Override
	public int insert(TsMsgSendHistory record) {
		int msghistory=tsmsgSendHistoryMapper.insert(record);
		return msghistory; 
	}

	
	/**
	 * 功能：根据caseCode 去查询，是否给客户已经发送过短信
	 * 作者：zhangxb16
	 */
	@Override
	public int countMsghistory(TsMsgSendHistory msghist) {
		int countMsg=tsmsgSendHistoryMapper.countMsghistory(msghist);
		return countMsg;
	}
	
}
