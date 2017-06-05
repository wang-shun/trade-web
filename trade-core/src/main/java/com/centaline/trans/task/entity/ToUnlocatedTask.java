package com.centaline.trans.task.entity;

import java.util.Date;

public class ToUnlocatedTask {
	private Long pkid;
	private String caseCode;
	private String instCode;
	private String taskJobCode;
	private Date createTime;
	private String candidateId;
	private String taskId;
	private String taskDfKey;
	private String name;
	private String isDelete; 

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
	 * @return the taskDfKey
	 */
	public String getTaskDfKey() {
		return taskDfKey;
	}

	/**
	 * @param taskDfKey the taskDfKey to set
	 */
	public void setTaskDfKey(String taskDfKey) {
		this.taskDfKey = taskDfKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
