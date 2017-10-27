package com.centaline.trans.common.vo;

import java.util.List;

public class FileUploadVO {

	private List<String> pictureNo;      /**文件id*/  
	private List<String> framePart;      /**附件编码*/
	private List<String> picName;        /**文件名*/  
	private List<Long> pkIdArr;		 	 /**被删除的图片ID*/
	private String caseCode;             /**交易单编号*/
	private String partCode;             /**环节编码*/
	private String id;                   
	private String preFileCode;
	private String fileCat;
	private String fileName;
	private String bizCode;

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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPreFileCode() {
		return preFileCode;
	}
	public void setPreFileCode(String preFileCode) {
		this.preFileCode = preFileCode;
	}
	public String getFileCat() {
		return fileCat;
	}
	public void setFileCat(String fileCat) {
		this.fileCat = fileCat;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the bizCode
	 */
	public String getBizCode() {
		return bizCode;
	}
	/**
	 * @param bizCode the bizCode to set
	 */
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	
	
}
