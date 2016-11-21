package com.centaline.trans.award.repository;

import java.util.List;

import com.centaline.trans.award.entity.TsAwardKpiPay;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface TsAwardKpiPayMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsAwardKpiPay record);

    int insertSelective(TsAwardKpiPay record);

    TsAwardKpiPay selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsAwardKpiPay record);

    int updateByPrimaryKey(TsAwardKpiPay record);
    
    
    List<TsAwardKpiPay> getTsAwardKpiPayByProperty(TsAwardKpiPay record);
    
    int updateTsAwardKpiPayStatus(TsAwardKpiPay record);
}