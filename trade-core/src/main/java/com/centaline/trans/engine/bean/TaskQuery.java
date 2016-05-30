package com.centaline.trans.engine.bean;

public class TaskQuery {

	public TaskQuery() {
	}

	public TaskQuery(String processInstanceId, Boolean active) {
		this.processInstanceId = processInstanceId;
		this.active = active;
	}

	/**
	 * 查询task支持并且可能需要用到的的字段 查询某流程当前节点设置active和processInstanceId即可
	 */
	private String taskDefinitionKey;
	private String assignee;
	private String assigneeLike;
	private String owner;
	private String ownerLike;
	private String candidateUser;
	private String candidateGroup;
	private String candidateGroups;
	private String processInstanceId;
	private String processInstanceBusinessKey;
	private String processInstanceBusinessKeyLike;
	private String processDefinitionKey;
	private String processDefinitionKeyLike;
	private Boolean active;

	private Boolean finished;
	private Boolean processFinished;
	
	private Integer size=9999;

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee
	 *            the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the assigneeLike
	 */
	public String getAssigneeLike() {
		return assigneeLike;
	}

	/**
	 * @param assigneeLike
	 *            the assigneeLike to set
	 */
	public void setAssigneeLike(String assigneeLike) {
		this.assigneeLike = assigneeLike;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the ownerLike
	 */
	public String getOwnerLike() {
		return ownerLike;
	}

	/**
	 * @param ownerLike
	 *            the ownerLike to set
	 */
	public void setOwnerLike(String ownerLike) {
		this.ownerLike = ownerLike;
	}

	/**
	 * @return the candidateUser
	 */
	public String getCandidateUser() {
		return candidateUser;
	}

	/**
	 * @param candidateUser
	 *            the candidateUser to set
	 */
	public void setCandidateUser(String candidateUser) {
		this.candidateUser = candidateUser;
	}

	/**
	 * @return the candidateGroup
	 */
	public String getCandidateGroup() {
		return candidateGroup;
	}

	/**
	 * @param candidateGroup
	 *            the candidateGroup to set
	 */
	public void setCandidateGroup(String candidateGroup) {
		this.candidateGroup = candidateGroup;
	}

	/**
	 * @return the candidateGroups
	 */
	public String getCandidateGroups() {
		return candidateGroups;
	}

	/**
	 * @param candidateGroups
	 *            the candidateGroups to set
	 */
	public void setCandidateGroups(String candidateGroups) {
		this.candidateGroups = candidateGroups;
	}

	/**
	 * @return the processInstanceId
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * @param processInstanceId
	 *            the processInstanceId to set
	 */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * @return the processInstanceBusinessKey
	 */
	public String getProcessInstanceBusinessKey() {
		return processInstanceBusinessKey;
	}

	/**
	 * @param processInstanceBusinessKey
	 *            the processInstanceBusinessKey to set
	 */
	public void setProcessInstanceBusinessKey(String processInstanceBusinessKey) {
		this.processInstanceBusinessKey = processInstanceBusinessKey;
	}

	/**
	 * @return the processInstanceBusinessKeyLike
	 */
	public String getProcessInstanceBusinessKeyLike() {
		return processInstanceBusinessKeyLike;
	}

	/**
	 * @param processInstanceBusinessKeyLike
	 *            the processInstanceBusinessKeyLike to set
	 */
	public void setProcessInstanceBusinessKeyLike(
			String processInstanceBusinessKeyLike) {
		this.processInstanceBusinessKeyLike = processInstanceBusinessKeyLike;
	}

	/**
	 * @return the processDefinitionKey
	 */
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	/**
	 * @param processDefinitionKey
	 *            the processDefinitionKey to set
	 */
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	/**
	 * @return the processDefinitionKeyLike
	 */
	public String getProcessDefinitionKeyLike() {
		return processDefinitionKeyLike;
	}

	/**
	 * @param processDefinitionKeyLike
	 *            the processDefinitionKeyLike to set
	 */
	public void setProcessDefinitionKeyLike(String processDefinitionKeyLike) {
		this.processDefinitionKeyLike = processDefinitionKeyLike;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the taskDefinitionKey
	 */
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	/**
	 * @param taskDefinitionKey
	 *            the taskDefinitionKey to set
	 */
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	/**
	 * @return the finished
	 */
	public Boolean getFinished() {
		return finished;
	}

	/**
	 * @param finished the finished to set
	 */
	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	/**
	 * @return the processFinished
	 */
	public Boolean getProcessFinished() {
		return processFinished;
	}

	/**
	 * @param processFinished the processFinished to set
	 */
	public void setProcessFinished(Boolean processFinished) {
		this.processFinished = processFinished;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
