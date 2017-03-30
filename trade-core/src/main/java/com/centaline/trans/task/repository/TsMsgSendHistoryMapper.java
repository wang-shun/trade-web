package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.TsMsgSendHistory;


@MyBatisRepository
public interface TsMsgSendHistoryMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsMsgSendHistory record);

    int insertSelective(TsMsgSendHistory record);

    TsMsgSendHistory selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsMsgSendHistory record);

    int updateByPrimaryKey(TsMsgSendHistory record);
    
    /**
	 * 功能：根据caseCode 去查询，是否给客户已经发送过短信
	 * 作者：zhangxb16
	 */
    public int countMsghistory(TsMsgSendHistory msghist);
}