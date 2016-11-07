package com.centaline.trans.signroom.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.signroom.entity.RmSignRoom;
import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.entity.TradeCenterSchedule;
import com.centaline.trans.signroom.vo.DateWeekVo;
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
	 * 获取交易中心信息
	 * @return
	 */
	List<TradeCenter> getTradeCenters(Map map);

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
	
	/**
	 * 判断是否存在该签约室 
	 * @param rmSignRoom
	 * @return
	 */
	boolean isExist(RmSignRoom rmSignRoom);

	/**
	 * 判断某个时间段排期是否已预约
	 * @param reservationInfoVo
	 * @return
	 */
	boolean isUsedByRmRoomSchedule(ReservationInfoVo reservationInfoVo);
	
	/**
	 * 判断是否能够删除签约室
	 * @param rmSignRoom
	 * @return
	 */
	boolean isCanDelSignRoom(RmSignRoom rmSignRoom);

	/**
	 * 显示排班数据
	 * @param map
	 * @return
	 * @throws ParseException 
	 */
	List<List<DateWeekVo>> showSchedulingData(Map map) throws ParseException;
	/**
	 * 新增或更新一条值班数据
	 * @param tradeCenterSchedule
	 * @return
	 */
	int addOrUpdateTradeCenterSchedule(TradeCenterSchedule tradeCenterSchedule);
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
	
	/**
	 * 判断是否当日值班
	 * @return
	 */
	boolean isCurrenDayDuty();

}
