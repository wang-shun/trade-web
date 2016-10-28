package com.centaline.trans.common.vo;

import java.util.List;

public class FileUploadVO {

	private List<String> pictureNo;      /**文件id*/  
	private List<String> framePart;      /**附件编码*/
	private List<String> picName;        /**文件名*/  
	private List<Long> pkIdArr;		 	 /**被删除的图片ID*/
	private String caseCode;             /**交易单编号*/
	private String partCode;             /**环节编码*/

	public List<String> getPictureNo() {
		return pictureNo;
	}
	public void setPictureNo(List<String> pictureNo) {
		this.pictureNo = pictureNo;
	}
	public List<String> getFramePart() {
		return framePart;
	}
	public void setFramePart(List<String> framePart) {
		this.framePart = framePart;
	}
	public List<String> getPicName() {
		return picName;
	}
	public void setPicName(List<String> picName) {
		this.picName = picName;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public List<Long> getPkIdArr() {
		return pkIdArr;
	}
	public void setPkIdArr(List<Long> pkIdArr) {
		this.pkIdArr = pkIdArr;
	}
	
	
}
