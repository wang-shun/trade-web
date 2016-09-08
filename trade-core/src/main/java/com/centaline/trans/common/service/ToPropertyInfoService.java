package com.centaline.trans.common.service;

import java.util.List;

import com.centaline.trans.cases.vo.ViHouseDelBaseVo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.task.entity.ToPropertyResearchVo;

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
    
    ViHouseDelBaseVo getHouseBaseByHoudelCode(String delCode);
    
    ToPropertyResearchVo getPropertyDepInfoByuserDepId(String depId);
}
