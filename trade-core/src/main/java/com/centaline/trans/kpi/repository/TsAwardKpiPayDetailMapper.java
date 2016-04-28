package com.centaline.trans.kpi.repository;

import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.kpi.entity.TsAwardKpiPayDetail;

@MyBatisRepository
public interface TsAwardKpiPayDetailMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsAwardKpiPayDetail record);

    int insertSelective(TsAwardKpiPayDetail record);

    TsAwardKpiPayDetail selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsAwardKpiPayDetail record);

    int updateByPrimaryKey(TsAwardKpiPayDetail record);
    
    void getPAwardKpiRate(Map map);
}