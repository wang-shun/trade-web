package com.centaline.trans.cases.vo;

import java.util.List;

public class CooperUpdateVo {
	
    private List<String> caseCode;
    private List<String> srvCode;
    private List<String> processorId;
    private List<String> orgId;
    private List<String> srvName;   
    private List<String> oldProcessorId;   
    
	public List<String> getSrvCode() {
		return srvCode;
	}

	public void setSrvCode(List<String> srvCode) {
		this.srvCode = srvCode;
	}

	public List<String> getProcessorId() {
		return processorId;
	}

	public void setProcessorId(List<String> processorId) {
		this.processorId = processorId;
	}

	public List<String> getOrgId() {
		return orgId;
	}

	public void setOrgId(List<String> orgId) {
		this.orgId = orgId;
	}

	public List<String> getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(List<String> caseCode) {
		this.caseCode = caseCode;
	}

	public List<String> getSrvName() {
		return srvName;
	}

	public void setSrvName(List<String> srvName) {
		this.srvName = srvName;
	}

	public List<String> getOldProcessorId() {
		return oldProcessorId;
	}

	public void setOldProcessorId(List<String> oldProcessorId) {
		this.oldProcessorId = oldProcessorId;
	}



}
