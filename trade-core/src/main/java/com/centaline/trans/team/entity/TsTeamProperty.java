package com.centaline.trans.team.entity;

public class TsTeamProperty {
	private Long pkid;

	private String yuTeamName;
	
	private String yuTeamCode;

	private String isResponseTeam;
	private String freeSelect;

	private String teamProperty;
	private String finTeamCode;
	
	// 冗余字段,表中不存在
	private String isSelect;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getYuTeamCode() {
		return yuTeamCode;
	}

	public void setYuTeamCode(String yuTeamCode) {
		this.yuTeamCode = yuTeamCode == null ? null : yuTeamCode.trim();
	}

	public String getIsResponseTeam() {
		return isResponseTeam;
	}

	public void setIsResponseTeam(String isResponseTeam) {
		this.isResponseTeam = isResponseTeam == null ? null : isResponseTeam
				.trim();
	}

	public String getTeamProperty() {
		return teamProperty;
	}

	public void setTeamProperty(String teamProperty) {
		this.teamProperty = teamProperty == null ? null : teamProperty.trim();
	}

	public String getFreeSelect() {
		return freeSelect;
	}

	public void setFreeSelect(String freeSelect) {
		this.freeSelect = freeSelect;
	}

	/**
	 * @return the finTeamCode
	 */
	public String getFinTeamCode() {
		return finTeamCode;
	}

	/**
	 * @param finTeamCode
	 *            the finTeamCode to set
	 */
	public void setFinTeamCode(String finTeamCode) {
		this.finTeamCode = finTeamCode;
	}

	public String getYuTeamName() {
		return yuTeamName;
	}

	public void setYuTeamName(String yuTeamName) {
		this.yuTeamName = yuTeamName;
	}

	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}
	
}