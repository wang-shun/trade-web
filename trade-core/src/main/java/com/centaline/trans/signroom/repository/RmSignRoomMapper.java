package com.centaline.trans.signroom.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.RmSignRoom;

@MyBatisRepository
public interface RmSignRoomMapper {

	/**
	 * 新增一个新的签约室
	 * 
	 * @param rmSignRoom
	 * @return
	 */
	int addRmSignRoom(RmSignRoom rmSignRoom);

	/**
	 * 更新签约室
	 * 
	 * @param rmSignRoom
	 */
	int updateRmSignRoom(RmSignRoom rmSignRoom);

	/**
	 * 删除一个签约室
	 * 
	 * @param rmSignRoom
	 */
	int deleteRmSignRoomById(Long pkid);

	/**
	 * 查询签约室列表
	 * 
	 * @return
	 */
	List<RmSignRoom> findRmSignRooms(RmSignRoom rmSignRoom);

	/**
	 * 获取某天某个贵宾服务部下的房间信息
	 * 
	 * @param map
	 * @return
	 */
	List<RmSignRoom> getSignRoomInfos(Map map);

	/**
	 * 获取排期时间段
	 */
	String getTimeSlots();

	/**
	 * 根据房屋id获取对应的房屋信息
	 * 
	 * @param roomId
	 *            房屋标识
	 * @return 房屋信息
	 */
	public RmSignRoom getSignRoomInfoById(Long roomId);

	/**
	 * 获取签约室信息及策略值
	 * 
	 * @return
	 */
	List<RmSignRoom> getRmSignRoomAndStragegy(Map map);

}
