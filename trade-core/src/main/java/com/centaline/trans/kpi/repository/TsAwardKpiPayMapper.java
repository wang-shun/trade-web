package com.centaline.trans.kpi.repository;

import java.util.List;
import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.kpi.entity.TsAwardKpiPay;

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