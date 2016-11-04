package com.centaline.trans.team.entity;

public class TsTeamScope {
    private Long pkid;

    private String yuAgentTeamCode;

    private String yuTeamCode;

    private String yuAgentTeamName;

    private String yuTeamName;

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

	public String getYuTeamCode() {
        return yuTeamCode;
    }

    public void setYuTeamCode(String yuTeamCode) {
        this.yuTeamCode = yuTeamCode == null ? null : yuTeamCode.trim();
    }

	public String getYuAgentTeamName() {
		return yuAgentTeamName;
	}

	public void setYuAgentTeamName(String yuAgentTeamName) {
		this.yuAgentTeamName = yuAgentTeamName;
	}

	public String getYuTeamName() {
		return yuTeamName;
	}

	public void setYuTeamName(String yuTeamName) {
		this.yuTeamName = yuTeamName;
	}
}