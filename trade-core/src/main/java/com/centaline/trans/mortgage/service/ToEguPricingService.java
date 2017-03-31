package com.centaline.trans.mortgage.service;

import java.util.List;

import com.centaline.trans.mortgage.entity.ToEguPricing;

public interface ToEguPricingService {

	/**
	 * 保存egu询价结果信息
	 * @param toEguPricing
	 */
	void saveToEguPricing(ToEguPricing toEguPricing);
	
	/**
	 * 根据询价编号查询询价记录
	 * @param evaCode
	 * @return
	 */
	List<ToEguPricing> findToEguPricingByEvaCode(String evaCode);
	
	/**
	 * 修改询价结果信息
	 * @param toEguPricing
	 */
	void updateToEguPricing(ToEguPricing toEguPricing);
	
	/**
	 * 根据主键查询询价结果信息
	 * @param pkid
	 * @return
	 */
	ToEguPricing findById(Long pkid);
	
	/**
	 * 根据案件编号和是否最终接受的询价结果查询询价结果信息
	 * @param caseCode
	 * @return
	 */
	ToEguPricing findIsFinalEguPricing(String caseCode);
	
	/**
	 * 绑定案件编号到询价结果
	 * @param toEguPricing
	 */
	void bindEvaCode(ToEguPricing toEguPricing);

}
