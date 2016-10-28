package com.centaline.trans.team.vo;

import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.entity.TsTeamScope;

public class TsTeamScopePropertyVO {
	TsTeamScope tsTeamScope;
	TsTeamProperty tsTeamProperty;

	public TsTeamScope getTsTeamScope() {
		return tsTeamScope;
	}

	public void setTsTeamScope(TsTeamScope tsTeamScope) {
		this.tsTeamScope = tsTeamScope;
	}

	public TsTeamProperty getTsTeamProperty() {
		return tsTeamProperty;
	}

	public void setTsTeamProperty(TsTeamProperty tsTeamProperty) {
		this.tsTeamProperty = tsTeamProperty;
	}

}
