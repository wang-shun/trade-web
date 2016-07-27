package com.centaline.trans.team.service;

import java.util.List;

import com.centaline.trans.team.entity.TsTeamScopeTarget;

public interface TsTeamScopeTargetService {
	
	List<TsTeamScopeTarget> getTeamScopeTargetListByProperty(TsTeamScopeTarget record);

}
