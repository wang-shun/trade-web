package com.centaline.trans.signroom.service;

import java.util.List;

import com.centaline.trans.signroom.entity.TradeCenter;

public interface TradeCenterService {

	/**
	 * 获取交易中心信息列表集合
	 * 
	 * @return 交易中心信息列表
	 */
	public List<TradeCenter> getTradeCenterList();
}
