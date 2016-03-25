package com.centaline.trans.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.service.TsTeamScopeGrpService;
import com.centaline.trans.team.entity.TsTeamScopeGrp;
import com.centaline.trans.team.repository.TsTeamScopeGrpMapper;

@Service
public class TsTeamScopeGrpServiceImpl implements TsTeamScopeGrpService {

	@Autowired
	private TsTeamScopeGrpMapper tsTeamScopeGrpMapper;
	
	@Override
	public List<TsTeamScopeGrp> getTsTeamScopeGrpListByProperty(TsTeamScopeGrp record) {
		return tsTeamScopeGrpMapper.getTsTeamScopeGrpListByProperty(record);
	}

	@Override
	public int delTsTeamScopeGrpByProperty(TsTeamScopeGrp tsTeamScopeGrp) {
		return tsTeamScopeGrpMapper.delTsTeamScopeGrpByProperty(tsTeamScopeGrp);
	}

	@Override
	public int saveTsTeamScopeGrp(TsTeamScopeGrp tsTeamScopeGrp) {
		return tsTeamScopeGrpMapper.insertSelective(tsTeamScopeGrp);
	}

}
