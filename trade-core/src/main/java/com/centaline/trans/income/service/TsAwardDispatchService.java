package com.centaline.trans.income.service;

import java.util.List;

import com.centaline.trans.income.entity.TsAwardDispatch;

public interface TsAwardDispatchService {
	
	/**
	 * 保存佣金分配信息
	 * @param tsAwardDispatch
	 */
	void saveTsAwardDispatch(TsAwardDispatch tsAwardDispatch);

	/**
	 * 批量保存佣金分配信息
	 * @param tsAwardDispatchList
	 */
	void saveTsAwardDispatchBatch(List<TsAwardDispatch> tsAwardDispatchList);
	
}
