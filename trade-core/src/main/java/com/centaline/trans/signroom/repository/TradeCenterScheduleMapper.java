package com.centaline.trans.signroom.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.entity.TradeCenterSchedule;

@MyBatisRepository
public interface TradeCenterScheduleMapper {

	/**
	 * 新增一条值班数据
	 * @param tradeCenterSchedule
	 * @return
	 */
	int addTradeCenterSchedule(TradeCenterSchedule tradeCenterSchedule);
	/**
	 * 删除一条值班数据
	 * @param tradeCenterSchedule
	 * @return
	 */
	int deleteTradeCenterSchedule(TradeCenterSchedule tradeCenterSchedule);
	
	/**
	 * 按条件查询值班数据
	 * @param map
	 * @return
	 */
	List<TradeCenterSchedule> queryTradeCenterSchedules(Map map);
}