package com.centaline.trans.common.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.TsTaskPlanSet;

@MyBatisRepository
public interface TsTaskPlanSetMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsTaskPlanSet record);

    int insertSelective(TsTaskPlanSet record);

    TsTaskPlanSet selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsTaskPlanSet record);

    int updateByPrimaryKey(TsTaskPlanSet record);
    
    int getTsTaskPlanSetCount(TsTaskPlanSet record);
}