package com.centaline.trans.signroom.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.RmSignRoom;

@MyBatisRepository
public interface RmSignRoomMapper {
	
	/**
	 * 新增一个新的签约室
	 * @param rmSignRoom
	 * @return
	 */
	int addRmSignRoom(RmSignRoom rmSignRoom);
	
	/**
	 * 更新签约室
	 * @param rmSignRoom
	 */
	int updateRmSignRoom(RmSignRoom rmSignRoom);
	/**
	 * 删除一个签约室
	 * @param rmSignRoom
	 */
	int deleteRmSignRoomById(Long pkid);
	
	/**
	 * 查询签约室列表
	 * @return
	 */
	List<RmSignRoom> findRmSignRooms(RmSignRoom rmSignRoom);

}
