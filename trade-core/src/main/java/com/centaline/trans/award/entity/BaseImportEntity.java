package com.centaline.trans.award.entity;

import java.util.Date;

public class BaseImportEntity {
	private Long pkid;
	private String caseCode;
	private String participant;
	private String jobId;
	private String orgId;
	private Double baseAmount;
	private Date createTime;
	private Date belongMonth;
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getParticipant() {
		return participant;
	}
	public void setParticipant(String participant) {
		this.participant = participant;
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
	public Double getBaseAmount() {
		return baseAmount;
	}
	public void setBaseAmount(Double baseAmount) {
		this.baseAmount = baseAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getBelongMonth() {
		return belongMonth;
	}
	public void setBelongMonth(Date belongMonth) {
		this.belongMonth = belongMonth;
	}
}
