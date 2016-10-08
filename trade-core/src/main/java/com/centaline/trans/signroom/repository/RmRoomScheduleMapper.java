package com.centaline.trans.signroom.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.RmRoomSchedule;
import com.centaline.trans.signroom.vo.FreeRoomVo;

@MyBatisRepository
public interface RmRoomScheduleMapper {

	/**
	 * 获取排期信息
	 * 
	 * @param map
	 * @return
	 */
	List<RmRoomSchedule> getRmRoomSchedules(Map map);

	/**
	 * 更新闲置房间的状态
	 * 
	 * @param freeRoomVo
	 *            条件
	 * @return 如果返回1,更新成功。如果返回0,更新失败。
	 */
	public int updateFreeRoomStatus(FreeRoomVo freeRoomVo);

	/**
	 * 更新预约中的房间状态至闲置状态
	 * 
	 * @param scheduleId
	 *            签约室安排标识
	 * @return 如果返回1,更新成功。如果返回0,更新失败。
	 */
	public int updateToFreeStatus(String scheduleId);
	
	/**
	 * 根据pkid查询排期信息
	 * @param pkid
	 * @return
	 */
	public RmRoomSchedule getRmRoomScheduleByPkid(Long pkid);
	

}