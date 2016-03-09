package com.centaline.trans.team.vo;

import java.util.Date;

public class CaseInfoVO {
	private String propertyAddr;
	private String agentName;
	private String grpName;
	private Date importTime;
	private String orgId;
	private String leadingProcessId;

	public String getPropertyAddr() {
		return propertyAddr;
	}

	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getLeadingProcessId() {
		return leadingProcessId;
	}

	public void setLeadingProcessId(String leadingProcessId) {
		this.leadingProcessId = leadingProcessId;
	}

}
