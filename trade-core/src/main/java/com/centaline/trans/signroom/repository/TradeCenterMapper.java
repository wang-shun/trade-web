package com.centaline.trans.signroom.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.vo.Consultant;

@MyBatisRepository
public interface TradeCenterMapper {

	/**
	 * 获取交易中心信息列表集合
	 * 
	 * @return 交易中心信息列表
	 */
	public List<TradeCenter> getTradeCenterList();

	/**
	 * 取得签约中心信息
	 * 
	 * @param map
	 * @return
	 */
	TradeCenter getTradeCenter(Map map);

	/**
	 * 获取交易中心信息列表集合
	 * 
	 * @return 交易中心信息列表
	 */
	public List<TradeCenter> getTradeCenterListByDistrictId(Map map);

	/**
	 * 根据交易中心id获取处于该交易中心下的所有前台交易顾问
	 * 
	 * @param tradeCenterId
	 *            交易中心id
	 * @return 前台交易顾问列表
	 */
	public List<Consultant> getConsultantListByTradecentId(Long tradeCenterId);
}