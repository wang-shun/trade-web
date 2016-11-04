package com.centaline.trans.task.vo;

import java.util.Date;

public class ToUnlocatedTaskVo {
	private Long pkid;
	private String caseCode;
	private String instCode;
	private String taskJobCode;
	private Date crateTime;
	private String candidateId;
	private String taskId;
	private String orgId;

	/**
	 * @return the pkid
	 */
	public Long getPkid() {
		return pkid;
	}

	/**
	 * @param pkid
	 *            the pkid to set
	 */
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	/**
	 * @return the caseCode
	 */
	public String getCaseCode() {
		return caseCode;
	}

	/**
	 * @param caseCode
	 *            the caseCode to set
	 */
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	/**
	 * @return the instCode
	 */
	public String getInstCode() {
		return instCode;
	}

	/**
	 * @param instCode
	 *            the instCode to set
	 */
	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	/**
	 * @return the taskJobCode
	 */
	public String getTaskJobCode() {
		return taskJobCode;
	}

	/**
	 * @param taskJobCode
	 *            the taskJobCode to set
	 */
	public void setTaskJobCode(String taskJobCode) {
		this.taskJobCode = taskJobCode;
	}

	/**
	 * @return the crateTime
	 */
	public Date getCrateTime() {
		return crateTime;
	}

	/**
	 * @param crateTime
	 *            the crateTime to set
	 */
	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}

	/**
	 * @return the candidateId
	 */
	public String getCandidateId() {
		return candidateId;
	}

	/**
	 * @param candidateId
	 *            the candidateId to set
	 */
	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
