package com.centaline.trans.engine.entity;

import java.util.List;

public class ToWorkFlow {
	private Long pkid;

	private String caseCode;

	private String instCode;
	private String businessKey;
	private String processDefinitionId;
	private String processOwner;
	private String status;
	private String bizCode;
	private List<String> bizCodes;
	
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode == null ? null : caseCode.trim();
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode == null ? null : instCode.trim();
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	/**
	 * @return the processOwner
	 */
	public String getProcessOwner() {
		return processOwner;
	}

	/**
	 * @param processOwner the processOwner to set
	 */
	public void setProcessOwner(String processOwner) {
		this.processOwner = processOwner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public List<String> getBizCodes() {
		return bizCodes;
	}

	public void setBizCodes(List<String> bizCodes) {
		this.bizCodes = bizCodes;
	}
}