package com.centaline.trans.task.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class ToTransPlanOrToPropertyInfo {
	
	/*环节编号*/
	private String partCode;
	/*交易单编号*/
	private String caseCode;
	/*执行时间*/
	@JSONField(format= "yyyy-MM-dd" )
	private Date estPartTime;
	/*物业地址*/
	private String propertyAddr;
	
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	
	public Date getEstPartTime() {
		return estPartTime;
	}
	public void setEstPartTime(Date estPartTime) {
		this.estPartTime = estPartTime;
	}
	public String getPropertyAddr() {
		return propertyAddr;
	}
	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}
	
	
}
