package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToPricing;

@MyBatisRepository
public interface ToPricingMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToPricing record);

    int insertSelective(ToPricing record);

    ToPricing selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToPricing record);

    int updateByPrimaryKeyWithBLOBs(ToPricing record);

    int updateByPrimaryKey(ToPricing record);
    
    ToPricing findToPricingByCaseCode(String caseCode);
}