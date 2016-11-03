package com.centaline.trans.mortgage.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mortgage.entity.ToEguPricing;

@MyBatisRepository
public interface ToEguPricingMapper {
    int insert(ToEguPricing toEguPricing);

    int insertSelective(ToEguPricing toEguPricing);
    
    List<ToEguPricing> findToEguPricingByEvaCode(String evaCode);
    
    int update(ToEguPricing toEguPricing);
    
    ToEguPricing findById(Long pkid);
    
    List<ToEguPricing> findIsFinalEguPricing(String caseCode);
}