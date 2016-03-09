package com.centaline.trans.perform.entity;

import java.util.Date;

public class GustFollowEntity {
	private Long pkid;
	private String caseCode;
	private String participantId;
	private String jobId;
	private String orgId;
	private Double phoneAccuracy;
	private Integer satisfyDegree;
	private Date createTime;

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

	public Double getPhoneAccuracy() {
		return phoneAccuracy;
	}

	public void setPhoneAccuracy(Double phoneAccuracy) {
		this.phoneAccuracy = phoneAccuracy;
	}

	public Integer getSatisfyDegree() {
		return satisfyDegree;
	}

	public void setSatisfyDegree(Integer satisfyDegree) {
		this.satisfyDegree = satisfyDegree;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
