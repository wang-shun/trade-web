package com.centaline.trans.mgr.entity;

public class TsFinOrg {
	private Long pkid;

	private String finOrgName;

	private String finOrgNameYc;

	private String finOrgCode;

	private String faFinOrgCode;

	private String faFinOrgName;

	
	private String coLevel;
	private String coLevelStr;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getFinOrgName() {
		return finOrgName;
	}

	public void setFinOrgName(String finOrgName) {
		this.finOrgName = finOrgName == null ? null : finOrgName.trim();
	}

	public String getFinOrgCode() {
		return finOrgCode;
	}

	public void setFinOrgCode(String finOrgCode) {
		this.finOrgCode = finOrgCode == null ? null : finOrgCode.trim();
	}

	public String getFaFinOrgCode() {
		return faFinOrgCode;
	}

	public void setFaFinOrgCode(String faFinOrgCode) {
		this.faFinOrgCode = faFinOrgCode == null ? null : faFinOrgCode.trim();
	}

	public String getFaFinOrgName() {
		return faFinOrgName;
	}

	public void setFaFinOrgName(String faFinOrgName) {
		this.faFinOrgName = faFinOrgName;
	}

	public String getFinOrgNameYc() {
		return finOrgNameYc;
	}

	public void setFinOrgNameYc(String finOrgNameYc) {
		this.finOrgNameYc = finOrgNameYc;
	}

	public String getCoLevel() {
		return coLevel;
	}

	public void setCoLevel(String coLevel) {
		this.coLevel = coLevel;
	}

	public String getCoLevelStr() {
		return coLevelStr;
	}

	public void setCoLevelStr(String coLevelStr) {
		this.coLevelStr = coLevelStr;
	}

}