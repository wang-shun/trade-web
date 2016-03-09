package com.centaline.trans.income.vo;

import com.aist.common.utils.excel.annotation.ExcelField;

public class BaseAmountVO {
	
	@ExcelField(title = "职位")
	private String position;
	
	@ExcelField(title = "人员名称")
	private String userName;
	
	@ExcelField(title = "员工编号")
	private String employeeCode;
	
	@ExcelField(title = "案件编号")
	private String caseCode;
	
	@ExcelField(title = "案件地址")
	private String propertyAddr;
	
	@ExcelField(title = "所得金额")
	private Double baseAmount;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getPropertyAddr() {
		return propertyAddr;
	}

	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}

	public Double getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(Double baseAmount) {
		this.baseAmount = baseAmount;
	}
	
}
