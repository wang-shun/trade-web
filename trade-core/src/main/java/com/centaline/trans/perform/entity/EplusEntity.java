package com.centaline.trans.perform.entity;

import java.util.Date;

public class EplusEntity {
	private Long pkid;
	private String participantName;
	private String participantId;
	private String jobId;
	private String orgId;
	private Integer orders;
	private Integer kpiOrders;
	private Date belongMonth;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public Integer getKpiOrders() {
		return kpiOrders;
	}

	public void setKpiOrders(Integer kpiOrders) {
		this.kpiOrders = kpiOrders;
	}

	public Date getBelongMonth() {
		return belongMonth;
	}

	public void setBelongMonth(Date belongMonth) {
		this.belongMonth = belongMonth;
	}
}
