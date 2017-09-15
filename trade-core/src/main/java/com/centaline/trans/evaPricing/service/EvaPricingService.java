package com.centaline.trans.evaPricing.service;

import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;

/**
 * 询价service
 * @author wbcaiyx
 *
 */
public interface EvaPricingService {
	
	/**
	 * 查询询价明细	
	 * @return
	 */
	ToEvaPricingVo findEvaPricingDetailByPKID(Long PKID);
	
	/**
	 * 新增询价数据
	 * @param vo
	 */
	void insertEvaPricing(ToEvaPricingVo vo);
	
	/**
	 * 记录询价数据
	 */
	void updateEvaPricing(ToEvaPricingVo vo);
	
	/**
	 * 取消询价
	 * @param PKID
	 */
	int cancelByPKID(Long PKID); 

}
