package com.centaline.trans.signroom.service;

import java.util.List;
import java.util.Map;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.signroom.entity.RmSignRoom;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.vo.ReservationInfoVo;

public interface RmSignRoomService {
	/**
     * 获取某天某个贵宾服务部下的房间信息
     * @param gp
     * @return
     */
	AjaxResponse<Map> generatePageDate(JQGridParam gp);
	
	/**
	 * 获取交易中心信息
	 * @return
	 */
	List<TradeCenter> getTradeCenters();

	/**
	 * 获取签约室列表及策略值信息
	 * @param gp
	 * @return
	 */
	AjaxResponse<List<RmSignRoom>> signRoomShedualList(JQGridParam gp);
	
	/**
	 * 保存和更新签约室及策略值信息
	 * @param rmSignRoom
	 */
	void saveOrUpdateSignRoomSchedual(RmSignRoom rmSignRoom);

	/**
	 * 删除签约室信息
	 * @param rmSignRoom
	 */
	void deleteSignRoom(RmSignRoom rmSignRoom);

	/**
	 * 临时分配签约室
	 * @param reservationInfoVo
	 */
	void addReservation(ReservationInfoVo reservationInfoVo);

}
