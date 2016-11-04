package com.centaline.trans.income.service;

import com.centaline.trans.income.entity.TsIncomeStatistics;

public interface ProfitService {

	/**
	 * 功能：收益 作者：zhangxb16
	 */
	public int profitOperate(TsIncomeStatistics ts);

	public void doProcess();
}
