package com.centaline.trans.cases.vo;

import java.util.List;
import com.centaline.trans.common.entity.TgServItemAndProcessor;


public class TgServItemAndProcessorVo {
	
    private List<String> caseCode;
    private List<String> srvCode;
    private List<String> processorId;
    private List<String> orgId;
    List<TgServItemAndProcessor> servItemList;

    
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

	public List<TgServItemAndProcessor> getServItemList() {
		return servItemList;
	}

	public void setServItemList(List<TgServItemAndProcessor> servItemList) {
		this.servItemList = servItemList;
	}

}
