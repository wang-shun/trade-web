package com.centaline.trans.engine.vo;

import java.util.Date;

public class TaskVo {
	private String assignee;
	private Date createTime;
	private String delegationState;
	private String description;
	private Date dueDate;
	// URL
	private String execution;
	private Long id;
	private String name;
	private String owner;
	// URL
	private String parentTask;
	//需要设置的字段
	private String parentTaskId;
	private String priority;
	// URL
	private String processDefinition;
	//
	private String processInstanceId;
	private Boolean suspended;
	private String taskDefinitionKey;
	private String url;
	private String tenantId;

	private String group;
	/*查询Run的时候不会返回该值**/
	private String formKey;

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
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the delegationState
	 */
	public String getDelegationState() {
		return delegationState;
	}

	/**
	 * @param delegationState
	 *            the delegationState to set
	 */
	public void setDelegationState(String delegationState) {
		this.delegationState = delegationState;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the execution
	 */
	public String getExecution() {
		return execution;
	}

	/**
	 * @param execution
	 *            the execution to set
	 */
	public void setExecution(String execution) {
		this.execution = execution;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the parentTask
	 */
	public String getParentTask() {
		return parentTask;
	}

	/**
	 * @param parentTask
	 *            the parentTask to set
	 */
	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return the processDefinition
	 */
	public String getProcessDefinition() {
		return processDefinition;
	}

	/**
	 * @param processDefinition
	 *            the processDefinition to set
	 */
	public void setProcessDefinition(String processDefinition) {
		this.processDefinition = processDefinition;
	}

	/**
	 * @return the processInstance
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * @param processInstance
	 *            the processInstance to set
	 */
	public void setProcessInstanceId(String processInstance) {
		this.processInstanceId = processInstance;
	}

	/**
	 * @return the suspended
	 */
	public Boolean getSuspended() {
		return suspended;
	}

	/**
	 * @param suspended
	 *            the suspended to set
	 */
	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
}
