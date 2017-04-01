package com.centaline.trans.team.entity;

import java.util.List;

import com.centaline.trans.team.vo.TsTeamScopePropertyVO;

public class TeamScopeAllVO {
	List<TsTeamScopePropertyVO> tsTeamScopePropertyVOList;
	List<TsTeamProperty> tsTeamPropertyList;

	public List<TsTeamScopePropertyVO> getTsTeamScopePropertyVOList() {
		return tsTeamScopePropertyVOList;
	}

	public void setTsTeamScopePropertyVOList(List<TsTeamScopePropertyVO> tsTeamScopePropertyVOList) {
		this.tsTeamScopePropertyVOList = tsTeamScopePropertyVOList;
	}

	public List<TsTeamProperty> getTsTeamPropertyList() {
		return tsTeamPropertyList;
	}

	public void setTsTeamPropertyList(List<TsTeamProperty> tsTeamPropertyList) {
		this.tsTeamPropertyList = tsTeamPropertyList;
	}

}
