package com.centaline.trans.mortgage.repository;

import java.util.Map;

import com.centaline.trans.mortgage.entity.ToMortLoaner;

public interface ToMortLoanerMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToMortLoaner record);

    int insertSelective(ToMortLoaner record);

    ToMortLoaner selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToMortLoaner record);

    int updateByPrimaryKey(ToMortLoaner record);
    
    ToMortLoaner findToMortLoanerByCaseCodeAndLoanerStatus(Map<String,String> map);
}