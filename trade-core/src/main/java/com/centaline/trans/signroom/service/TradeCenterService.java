package com.centaline.trans.signroom.service;

import java.util.List;
import java.util.Map;

import com.centaline.trans.signroom.entity.TradeCenter;

public interface TradeCenterService {

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
