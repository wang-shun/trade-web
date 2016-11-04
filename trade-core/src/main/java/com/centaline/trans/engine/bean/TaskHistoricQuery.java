package com.centaline.trans.engine.bean;

public class TaskHistoricQuery {

	public TaskHistoricQuery() {
	}

	private String taskId;
	private String processInstanceId;
	private String processDefinitionKey;
	private String taskDefinitionKey;
	private String taskName;
	private String taskDeleteReason;
	private String taskAssignee;


	/**
	 * 查询task支持并且可能需要用到的的字段 查询某流程当前节点设置active和processInstanceId即可
	 */
	
	private Boolean active;

	private Boolean finished;
	
	private Boolean processFinished;
	
	
	private Integer size=9999;


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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDeleteReason() {
		return taskDeleteReason;
	}

	public void setTaskDeleteReason(String taskDeleteReason) {
		this.taskDeleteReason = taskDeleteReason;
	}

	public String getTaskAssignee() {
		return taskAssignee;
	}

	public void setTaskAssignee(String taskAssignee) {
		this.taskAssignee = taskAssignee;
	}
}
