package com.centaline.trans.task.entity;

import java.util.Date;

public class AwardBase {
	private Long pkid;
	private String caseCode;
	private String participant;
	private String jobId;
	private String jobCode;
	private String orgId;
	private String orgCode;
	private Double baseAmount;
	private Date createTime;
	private Date belongMonth;
	private Long configId;

	public AwardBase() {
	}

	public AwardBase(String participant, String jobCode, String orgId, String orgCode) {
		this.participant = participant;
		this.jobCode = jobCode;
		this.orgId = orgId;
		this.orgCode = orgCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof AwardBase) {
			AwardBase other = (AwardBase) obj;
			if(other.getParticipant()==null){
				return this.getParticipant()==null;
			}
			return other.getParticipant().equals(this.getParticipant());
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		if(this.getParticipant()==null){
			return -1;
		}
		return this.getParticipant().hashCode();
	}

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

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}
