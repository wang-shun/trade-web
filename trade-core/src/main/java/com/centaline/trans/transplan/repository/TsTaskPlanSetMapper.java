package com.centaline.trans.transplan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.transplan.entity.TsTaskPlanSet;

@MyBatisRepository
public interface TsTaskPlanSetMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsTaskPlanSet record);

    int insertSelective(TsTaskPlanSet record);

    TsTaskPlanSet selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsTaskPlanSet record);

    int updateByPrimaryKey(TsTaskPlanSet record);
    
    int getTsTaskPlanSetCount(TsTaskPlanSet record);
    
    TsTaskPlanSet getAutoTsTaskPlanSetByPartCode(String partCode);
	TsTaskPlanSet getTsTaskPlanSetByPartCode(String code);
}