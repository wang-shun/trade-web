package com.centaline.trans.task.service;

import java.util.List;

import com.centaline.trans.team.entity.TsTeamScopeAr;
import com.centaline.trans.team.entity.TsTeamScopeGrp;

public interface TsTeamScopeGrpService {
	
	List<TsTeamScopeGrp> getTsTeamScopeGrpListByProperty(TsTeamScopeGrp record);
	
	public int delTsTeamScopeGrpByProperty(TsTeamScopeGrp tsTeamScopeGrp);
	
	public int saveTsTeamScopeGrp(TsTeamScopeGrp tsTeamScopeGrp);
	
}
