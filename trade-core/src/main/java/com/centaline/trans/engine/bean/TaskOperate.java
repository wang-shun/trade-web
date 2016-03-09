package com.centaline.trans.engine.bean;

import java.util.List;

public class TaskOperate {
	public TaskOperate() {
	}

	public TaskOperate(String taskId, String action) {
		this.taskId = taskId;
		this.action = action;
	}

	private String taskId;
	private List<RestVariable> variables;

	/**
	 * action可选值 complete claim delegate resolve
	 */
	private String action;
	private String assignee;

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
	 * @return the variables
	 */
	public List<RestVariable> getVariables() {
		return variables;
	}

	/**
	 * @param variables
	 *            the variables to set
	 */
	public void setVariables(List<RestVariable> variables) {
		this.variables = variables;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

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
}
