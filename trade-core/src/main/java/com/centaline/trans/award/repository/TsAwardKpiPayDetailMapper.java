package com.centaline.trans.award.repository;

import java.util.Date;
import java.util.Map;

import com.centaline.trans.award.entity.TsAwardKpiPayDetail;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface TsAwardKpiPayDetailMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsAwardKpiPayDetail record);

    int insertSelective(TsAwardKpiPayDetail record);

    TsAwardKpiPayDetail selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsAwardKpiPayDetail record);

    int updateByPrimaryKey(TsAwardKpiPayDetail record);
    
    void getPAwardKpiRate(Map map);
    
    void getPAwardKpiRateStatic(Map map);
    
    Double getPersonBonusTotal(Date  belongM);
}