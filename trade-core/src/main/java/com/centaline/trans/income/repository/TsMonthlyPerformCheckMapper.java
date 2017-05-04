package com.centaline.trans.income.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.income.entity.TsMonthlyPerformCheck;
@MyBatisRepository
public interface TsMonthlyPerformCheckMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsMonthlyPerformCheck record);

    int insertSelective(TsMonthlyPerformCheck record);

    TsMonthlyPerformCheck selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsMonthlyPerformCheck record);

    int updateByPrimaryKey(TsMonthlyPerformCheck record);
}