package com.centaline.trans.task.vo;

import java.util.Date;

public class MortgageToSaveVO {
	
	private String pkid;
	
	private String caseCode;
	/**
	 * 银行
	 */
	private String bankName;
	/**
	 * 流失原因
	 */
	private String content;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	private String bank_type;
	
	private String finOrgCode;
	/**
	 * 贷款方式
	 * @return
	 */
	private String selfMort;//1自办0代办

	
	
	private String mortageService;
	private String partner ;
	private String taskId;
	private String processInstanceId;
	private String processDefinitionId;
	
	private Date estPartTime;
	
	
	
	public String getSelfMort() {
		return selfMort;
	}

	public void setSelfMort(String selfMort) {
		this.selfMort = selfMort;
	}

	public String getPkid() {
		return pkid;
	}

	public void setPkid(String pkid) {
		this.pkid = pkid;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMortageService() {
		return mortageService;
	}

	public void setMortageService(String mortageService) {
		this.mortageService = mortageService;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
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

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public Date getEstPartTime() {
		return estPartTime;
	}

	public void setEstPartTime(Date estPartTime) {
		this.estPartTime = estPartTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getFinOrgCode() {
		return finOrgCode;
	}

	public void setFinOrgCode(String finOrgCode) {
		this.finOrgCode = finOrgCode;
	}
	
	
	
}
