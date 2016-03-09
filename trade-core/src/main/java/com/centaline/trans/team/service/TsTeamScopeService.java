package com.centaline.trans.team.service;

import java.util.List;

import com.centaline.trans.team.entity.TsTeamScope;
import com.centaline.trans.team.vo.TsTeamScopePropertyVO;
import com.centaline.trans.team.vo.TsTeamScopeVO;

public interface TsTeamScopeService {

	public List<TsTeamScope> findTeamScope(TsTeamScope tsTeamScope);
	
	public List<TsTeamScope> selectCooperativeOrganization(TsTeamScope tsTeamScope);
	
	void saveTsTeamScope(TsTeamScope tsTeamScope);
	
	int saveTsTeamScopeVo(TsTeamScopeVO tsTeamScopeVO);
	
	TsTeamScope findById(Long id);

	void deleteTsTeamScope(Long id);
	
	// 根据orgCode 到 T_TS_TEAM_SCOPE 表中去查询合作组的 orgCode 
	List<TsTeamScope> selectByOrgCode(String orgCode);
	
	List<TsTeamScopePropertyVO> getTeamScopeListByProperty(TsTeamScope tsTeamScope);
}
