package com.centaline.trans.team.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centaline.trans.team.entity.TsTeamScopeTarget;
import com.centaline.trans.team.repository.TsTeamScopeTargetMapper;
import com.centaline.trans.team.service.TsTeamScopeTargetService;

@Service
public class TsTeamScopeTargetServiceImpl implements TsTeamScopeTargetService {
	
	@Autowired
	private TsTeamScopeTargetMapper tsTeamScopeTargetMapper;

	@Override
	public List<TsTeamScopeTarget> getTeamScopeTargetListByProperty(TsTeamScopeTarget record) {
		return tsTeamScopeTargetMapper.getTeamScopeTargetListByProperty(record);
	}

}
