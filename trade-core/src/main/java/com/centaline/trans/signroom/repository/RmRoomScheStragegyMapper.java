package com.centaline.trans.signroom.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.RmRoomScheStragegy;
import com.centaline.trans.signroom.entity.RmSignRoom;

@MyBatisRepository
public interface RmRoomScheStragegyMapper {
	/**
	 * 跟进房间id删除一个策略值
	 * @param pkid
	 * @return
	 */
    int deleteRmRoomScheStragegyByRoomId(Long roomId);

    /**
     * 插入策略值
     * @param record
     * @return
     */
    int insertSelective(RmRoomScheStragegy record);

    /**
     * 策略值列表
     * @param rmRoomScheStragegy
     * @return
     */
    List<RmRoomScheStragegy> selectByRmRoomScheStragegy(RmRoomScheStragegy rmRoomScheStragegy);

    /**
     * 更新策略值
     * @param record
     * @return
     */
    int updateRmRoomScheStragegy(RmRoomScheStragegy record);
    
}