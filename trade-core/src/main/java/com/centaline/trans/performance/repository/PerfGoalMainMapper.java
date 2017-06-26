package com.centaline.trans.performance.repository;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.performance.entity.PerfGoalMain;
@MyBatisRepository
public interface PerfGoalMainMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(PerfGoalMain record);

    int insertSelective(PerfGoalMain record);

    PerfGoalMain selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(PerfGoalMain record);

    int updateByPrimaryKey(PerfGoalMain record);
    
    PerfGoalMain selectByBelongMonthAndDistrict(@Param("belongMonth")Date belongMonth,@Param("district")String district);
}