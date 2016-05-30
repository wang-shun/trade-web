package com.centaline.trans.income.service;

import com.centaline.trans.income.entity.TsMonthlyPerformCheck;

public interface TsMonthlyPerformCheckService {
	 int deleteByPrimaryKey(Long pkid);

	    int insert(TsMonthlyPerformCheck record);

	    int insertSelective(TsMonthlyPerformCheck record);

	    TsMonthlyPerformCheck selectByPrimaryKey(Long pkid);

	    int updateByPrimaryKeySelective(TsMonthlyPerformCheck record);

	    int updateByPrimaryKey(TsMonthlyPerformCheck record);
}
