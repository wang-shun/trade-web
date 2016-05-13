package com.centaline.trans.common.service;

import java.util.List;

import com.centaline.trans.common.entity.TgGuestInfo;

public interface TgGuestInfoService {
	List<TgGuestInfo> findTgGuestInfoByCaseCode(String caseCode);
	
	List<TgGuestInfo> findTgGuestInfosByCaseCode(TgGuestInfo tgGuestInfo);
	
	TgGuestInfo findTgGuestInfoByGuestCode(String guestCode);
	
	TgGuestInfo findTgGuestInfoById(Long pkid);
	
	int removeGuestInfo(Long pkid);
	
	/**
	 * 功能: 给客户发送短信
	 * 作者：zhangxb16
	 */
	public int sendAssignMsg(String username, String phone, String propertyAddress, String partCode);
	
	
	public int sendMsgHistory(String caseCode, String partCode);
	
	
	TgGuestInfo selectByPrimaryKey(Long pkid);
	int updateByPrimaryKeySelective(TgGuestInfo tgGuestInfo);
	
}
