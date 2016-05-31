package com.centaline.trans.common.vo;

import java.util.List;

public class QureyAttachmentVO {
	
	private String caseCode;
	private String partCode;
	private List<String> accessoryCode;
	
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public List<String> getAccessoryCode() {
		return accessoryCode;
	}
	public void setAccessoryCode(List<String> accessoryCode) {
		this.accessoryCode = accessoryCode;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
}
