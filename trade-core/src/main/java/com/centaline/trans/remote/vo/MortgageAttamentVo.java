package com.centaline.trans.remote.vo;

import java.util.List;

public class MortgageAttamentVo {

	private String caseCode;
	
	private String isMainLoanBank;
	
	private List<String> preFileAdress; //附件id
	
	private List<String> picTag; //附件类型
	
	private List<String> picName; //附件名称
		
	private List<String> attachmentIds;  //删除之后的附件id

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public List<String> getPreFileAdress() {
		return preFileAdress;
	}

	public void setPreFileAdress(List<String> preFileAdress) {
		this.preFileAdress = preFileAdress;
	}

	public List<String> getPicTag() {
		return picTag;
	}

	public void setPicTag(List<String> picTag) {
		this.picTag = picTag;
	}

	public List<String> getPicName() {
		return picName;
	}

	public void setPicName(List<String> picName) {
		this.picName = picName;
	}

	public List<String> getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(List<String> attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

	public String getIsMainLoanBank() {
		return isMainLoanBank;
	}

	public void setIsMainLoanBank(String isMainLoanBank) {
		this.isMainLoanBank = isMainLoanBank;
	}
	
	
}
