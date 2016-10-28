package com.centaline.trans.common.service;

import com.centaline.trans.common.entity.TsTaskPlanSet;

public interface TsTaskPlanSetService {
	int deleteByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(TsTaskPlanSet tsTaskPlanSet);

	int addTsTaskPlanSet(TsTaskPlanSet tsTaskPlanSet);

	int getTsTaskPlanSetCountByProperty(TsTaskPlanSet tsTaskPlanSet);

}