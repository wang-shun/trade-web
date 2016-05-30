package com.centaline.trans.team.vo;

import java.util.List;

public class TsTeamScopeVO {
	private Long pkid;

	private String yuAgentTeamCode;

	private List<String> yuTeamCode;

	private String yuAgentTeamName;

	private List<String> yuTeamName;

	private String busiArIds;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getYuAgentTeamCode() {
		return yuAgentTeamCode;
	}

	public void setYuAgentTeamCode(String yuAgentTeamCode) {
		this.yuAgentTeamCode = yuAgentTeamCode;
	}

	public List<String> getYuTeamCode() {
		return yuTeamCode;
	}

	public void setYuTeamCode(List<String> yuTeamCode) {
		this.yuTeamCode = yuTeamCode;
	}

	public String getYuAgentTeamName() {
		return yuAgentTeamName;
	}

	public void setYuAgentTeamName(String yuAgentTeamName) {
		this.yuAgentTeamName = yuAgentTeamName;
	}

	public List<String> getYuTeamName() {
		return yuTeamName;
	}

	public void setYuTeamName(List<String> yuTeamName) {
		this.yuTeamName = yuTeamName;
	}

	public String getBusiArIds() {
		return busiArIds;
	}

	public void setBusiArIds(String busiArIds) {
		this.busiArIds = busiArIds;
	}

}
