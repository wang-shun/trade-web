package com.centaline.trans.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.team.repository.TsTeamScopeArMapper;
import com.centaline.trans.task.service.TsTeamScopeArService;
import com.centaline.trans.team.entity.TsTeamScopeAr;

@Service
public class TsTeamScopeArServiceImpl implements TsTeamScopeArService {
	
	@Autowired
	private TsTeamScopeArMapper tsTeamScopeArMapper;
	
	@Override
	public List<TsTeamScopeAr> getTsTeamScopeArListByProperty(TsTeamScopeAr tsTeamScopeAr) {
		return tsTeamScopeArMapper.getTsTeamScopeArListByProperty(tsTeamScopeAr);
	}

	@Override
	public int saveTsTeamScopeAr(TsTeamScopeAr tsTeamScopeAr) {
		return tsTeamScopeArMapper.insertSelective(tsTeamScopeAr);
	}

	@Override
	public int delTsTeamScopeArByProperty(TsTeamScopeAr tsTeamScopeAr) {
		return tsTeamScopeArMapper.delTsTeamScopeArByProperty(tsTeamScopeAr);
	}

}
