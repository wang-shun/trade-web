package com.centaline.trans.cases.vo;

import java.util.List;
import com.centaline.trans.common.entity.TgServItemAndProcessor;


public class TgServItemAndProcessorVo {
	
    private List<String> caseCode;
    private List<String> srvCode;
    private List<String> preProcessorId;
    private List<String> preOrgId;
    private List<String> processorId;
    private List<String> orgId;
    private List<String> srvName;
    List<TgServItemAndProcessor> servItemList;
    
	public List<String> getSrvCode() {
		return srvCode;
	}

	public void setSrvCode(List<String> srvCode) {
		this.srvCode = srvCode;
	}

	public List<String> getPreProcessorId() {
		return preProcessorId;
	}

	public void setPreProcessorId(List<String> preProcessorId) {
		this.preProcessorId = preProcessorId;
	}

	public List<String> getPreOrgId() {
		return preOrgId;
	}

	public void setPreOrgId(List<String> preOrgId) {
		this.preOrgId = preOrgId;
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

	public List<String> getSrvName() {
		return srvName;
	}

	public void setSrvName(List<String> srvName) {
		this.srvName = srvName;
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

	public List<TgServItemAndProcessor> getServItemList() {
		return servItemList;
	}

	public void setServItemList(List<TgServItemAndProcessor> servItemList) {
		this.servItemList = servItemList;
	}

}
