package com.centaline.trans.income.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.income.entity.TsIncomeStatistics;
import com.centaline.trans.income.repository.TsIncomeStatisticsMapper;
import com.centaline.trans.income.service.TsIncomeStatisticsService;
@Service
public class TsIncomeStatisticsServiceImpl implements TsIncomeStatisticsService {

	@Autowired
	TsIncomeStatisticsMapper tsIncomeStatisticsMapper;
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return tsIncomeStatisticsMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(TsIncomeStatistics record) {
		// TODO Auto-generated method stub
		return tsIncomeStatisticsMapper.insert(record);
	}

	@Override
	public int insertSelective(TsIncomeStatistics record) {
		// TODO Auto-generated method stub
		return tsIncomeStatisticsMapper.insertSelective(record);
	}

	@Override
	public TsIncomeStatistics selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return tsIncomeStatisticsMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(TsIncomeStatistics record) {
		// TODO Auto-generated method stub
		return tsIncomeStatisticsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TsIncomeStatistics record) {
		// TODO Auto-generated method stub
		return tsIncomeStatisticsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int queryMaxNoByCaseCode(TsIncomeStatistics record) {
		// TODO Auto-generated method stub
		return tsIncomeStatisticsMapper.queryMaxNoByCaseCode(record);
	}

	@Override
	public int queryMonthCountByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return tsIncomeStatisticsMapper.queryMonthCountByCaseCode(caseCode);
	}

}
