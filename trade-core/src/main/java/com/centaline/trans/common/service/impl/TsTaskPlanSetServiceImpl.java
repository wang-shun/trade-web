package com.centaline.trans.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.entity.TsTaskPlanSet;
import com.centaline.trans.common.repository.TsTaskPlanSetMapper;
import com.centaline.trans.common.service.TsTaskPlanSetService;

@Service
public class TsTaskPlanSetServiceImpl implements TsTaskPlanSetService {

	@Autowired
	TsTaskPlanSetMapper tsTaskPlanSetMapper;
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		return tsTaskPlanSetMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(TsTaskPlanSet tsTaskPlanSet) {
		return tsTaskPlanSetMapper.updateByPrimaryKeySelective(tsTaskPlanSet);
	}

	@Override
	public int addTsTaskPlanSet(TsTaskPlanSet tsTaskPlanSet) {
		return tsTaskPlanSetMapper.insertSelective(tsTaskPlanSet);
	}

	@Override
	public int getTsTaskPlanSetCountByProperty(TsTaskPlanSet tsTaskPlanSet) {
		return tsTaskPlanSetMapper.getTsTaskPlanSetCount(tsTaskPlanSet);
	}

}
