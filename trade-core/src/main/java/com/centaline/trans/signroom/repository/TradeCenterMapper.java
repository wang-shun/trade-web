package com.centaline.trans.signroom.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.TradeCenter;

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
}