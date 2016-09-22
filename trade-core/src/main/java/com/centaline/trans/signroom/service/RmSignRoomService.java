package com.centaline.trans.signroom.service;

import java.util.List;
import java.util.Map;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.signroom.entity.RmSignRoom;
import com.centaline.trans.signroom.entity.TradeCenter;

public interface RmSignRoomService {
	/**
     * 获取某天某个贵宾服务部下的房间信息
     * @param map
     * @return
     */
	AjaxResponse<Map> generatePageDate(Map map);
	
	/**
	 * 获取交易中心信息
	 * @return
	 */
	List<TradeCenter> getTradeCenters();

	/**
	 * 获取签约室列表及策略值信息
	 * @param map
	 * @return
	 */
	AjaxResponse<List<RmSignRoom>> signRoomShedualList(Map map);
	
	/**
	 * 保存和更新签约室及策略值信息
	 * @param rmSignRoom
	 */
	void saveOrUpdateSignRoomSchedual(RmSignRoom rmSignRoom);

}
