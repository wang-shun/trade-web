package com.centaline.trans.income.service;



import com.centaline.trans.income.entity.TsIncomeStatistics;

public interface TsIncomeStatisticsService {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsIncomeStatistics record);

    int insertSelective(TsIncomeStatistics record);

    TsIncomeStatistics selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsIncomeStatistics record);

    int updateByPrimaryKey(TsIncomeStatistics record);

    int queryMaxNoByCaseCode(TsIncomeStatistics record);
    int queryMonthCountByCaseCode(String caseCode);
    
}