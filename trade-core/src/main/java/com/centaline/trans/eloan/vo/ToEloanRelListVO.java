package com.centaline.trans.eloan.vo;

import java.util.ArrayList;
import java.util.List;

import com.centaline.trans.eloan.entity.ToEloanRel;

public class ToEloanRelListVO {
	private List<ToEloanRel> eloanRelList = new ArrayList<ToEloanRel>();

	private String isRelFinish;

	private String taskId;

	public List<ToEloanRel> getEloanRelList() {
		return eloanRelList;
	}

	public void setEloanRelList(List<ToEloanRel> eloanRelList) {
		this.eloanRelList = eloanRelList;
	}

	public String getIsRelFinish() {
		return isRelFinish;
	}

	public void setIsRelFinish(String isRelFinish) {
		this.isRelFinish = isRelFinish;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
