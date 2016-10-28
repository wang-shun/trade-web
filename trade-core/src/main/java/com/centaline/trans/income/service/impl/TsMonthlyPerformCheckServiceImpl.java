package com.centaline.trans.income.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.income.entity.TsMonthlyPerformCheck;
import com.centaline.trans.income.repository.TsMonthlyPerformCheckMapper;
import com.centaline.trans.income.service.TsMonthlyPerformCheckService;
@Service
public class TsMonthlyPerformCheckServiceImpl implements
		TsMonthlyPerformCheckService {

	@Autowired
	TsMonthlyPerformCheckMapper tsMonthlyPerformCheckMapper;
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return tsMonthlyPerformCheckMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(TsMonthlyPerformCheck record) {
		// TODO Auto-generated method stub
		return tsMonthlyPerformCheckMapper.insert(record);
	}

	@Override
	public int insertSelective(TsMonthlyPerformCheck record) {
		// TODO Auto-generated method stub
		return tsMonthlyPerformCheckMapper.insertSelective(record);
	}

	@Override
	public TsMonthlyPerformCheck selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return tsMonthlyPerformCheckMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(TsMonthlyPerformCheck record) {
		// TODO Auto-generated method stub
		return tsMonthlyPerformCheckMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TsMonthlyPerformCheck record) {
		// TODO Auto-generated method stub
		return tsMonthlyPerformCheckMapper.updateByPrimaryKey(record);
	}

}
