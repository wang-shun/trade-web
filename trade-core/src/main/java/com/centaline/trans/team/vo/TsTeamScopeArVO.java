package com.centaline.trans.team.vo;

import java.util.List;
import com.centaline.trans.team.entity.TsTeamProperty;

public class TsTeamScopeArVO {
	
	 private String arCode;
	 
	 private String arName;
	 
	 private List<TsTeamProperty> tsTeamPropertyList;

	public String getArCode() {
		return arCode;
	}

	public void setArCode(String arCode) {
		this.arCode = arCode;
	}

	public String getArName() {
		return arName;
	}

	public void setArName(String arName) {
		this.arName = arName;
	}

	public List<TsTeamProperty> getTsTeamPropertyList() {
		return tsTeamPropertyList;
	}

	public void setTsTeamPropertyList(List<TsTeamProperty> tsTeamPropertyList) {
		this.tsTeamPropertyList = tsTeamPropertyList;
	}
	 
}
