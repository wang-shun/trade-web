package com.centaline.trans.income.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.income.entity.TsIncomeStatistics;

@MyBatisRepository
public interface TsIncomeStatisticsMapper {
	int deleteByPrimaryKey(Long pkid);

	int insert(TsIncomeStatistics record);

	int insertSelective(TsIncomeStatistics record);

	TsIncomeStatistics selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(TsIncomeStatistics record);

	int updateByPrimaryKey(TsIncomeStatistics record);

	int queryMaxNoByCaseCode(TsIncomeStatistics record);

	int queryMonthCountByCaseCode(String caseCode);

	/**
	 * 功能：收益 作者：zhangxb16
	 */
	List<TsIncomeStatistics> selectIncome(TsIncomeStatistics ts);

	/**
	 * 查询待处理数据
	 * 
	 * @return
	 */
	List<TsIncomeStatistics> selectWaitingProcessing();

	TsIncomeStatistics selectByFinCaseCode(String finCaseCode);
}