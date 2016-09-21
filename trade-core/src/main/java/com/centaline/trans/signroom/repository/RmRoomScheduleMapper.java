package com.centaline.trans.signroom.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.RmRoomSchedule;

@MyBatisRepository
public interface RmRoomScheduleMapper {
	
  
    /**
     * 获取排期信息
     * @param map
     * @return
     */
    List<RmRoomSchedule> getRmRoomSchedules(Map map);

}