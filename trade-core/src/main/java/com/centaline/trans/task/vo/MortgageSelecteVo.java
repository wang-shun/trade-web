package com.centaline.trans.task.vo;

public class MortgageSelecteVo {
	private String mortageService;
	private String partner ;
	private String caseCode;
	private String taskId;
	private String processInstanceId;
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
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
	public String getMortageService() {
		return mortageService;
	}
	public void setMortageService(String mortageService) {
		this.mortageService = mortageService;
	}
}
