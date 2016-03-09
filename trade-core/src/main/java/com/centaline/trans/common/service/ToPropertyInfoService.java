package com.centaline.trans.common.service;

import java.util.List;

import com.centaline.trans.common.entity.ToPropertyInfo;

public interface ToPropertyInfoService {
	ToPropertyInfo findToPropertyInfoByCaseCode(String caseCode);

	/**
	 * 根据userid获取到物业信息
	 * 
	 * @param userId
	 * @return
	 */
	List<ToPropertyInfo> getPropertyInfoByUserId(String userId);

	int insertSelective(ToPropertyInfo record);

    ToPropertyInfo findToPropertyInfoByCaseCodeAndAddr(ToPropertyInfo record);
}
