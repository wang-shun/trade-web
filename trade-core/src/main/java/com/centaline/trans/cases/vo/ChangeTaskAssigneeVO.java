package com.centaline.trans.cases.vo;

import java.util.List;

public class ChangeTaskAssigneeVO {
	private List<Integer>taskIds;
	private List<String>caseCodes;
	private String userId;
	public List<Integer> getTaskIds() {
		return taskIds;
	}
	public void setTaskIds(List<Integer> taskIds) {
		this.taskIds = taskIds;
	}
	public List<String> getCaseCodes() {
		return caseCodes;
	}
	public void setCaseCodes(List<String> caseCodes) {
		this.caseCodes = caseCodes;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
