package com.centaline.trans.task.service;

import java.util.List;
import com.centaline.trans.team.entity.TsTeamScopeAr;

public interface TsTeamScopeArService {
	
	 public List<TsTeamScopeAr> getTsTeamScopeArListByProperty(TsTeamScopeAr tsTeamScopeAr);
	 
	 public int saveTsTeamScopeAr(TsTeamScopeAr tsTeamScopeAr);
	 
	 public int delTsTeamScopeArByProperty(TsTeamScopeAr tsTeamScopeAr);
	 
}
