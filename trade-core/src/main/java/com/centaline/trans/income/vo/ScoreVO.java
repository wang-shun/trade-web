package com.centaline.trans.income.vo;

import com.aist.common.utils.excel.annotation.ExcelField;

public class ScoreVO {

	@ExcelField(title = "人员")
	private String userName;
	
	@ExcelField(title = "人员编号")
	private String employeeCode;
	
	@ExcelField(title = "角色")
	private String roleName;
	
	@ExcelField(title = "案件编号")
	private String caseCode;
	
	@ExcelField(title = "案件地址")
	private String propertyAddr;
	
	@ExcelField(title = "满意度评分")
	private Integer satisfyRating;
	
	@ExcelField(title = "电话准确率")
	private String phoneAccuracy;
	
	@ExcelField(title = "所属主管")
	private String masterName;
	
	@ExcelField(title = "电话准确率")
	private String masterPhoneAccuracy;
	
	@ExcelField(title = "所属总监")
	private String chiefName;
	
	@ExcelField(title = "电话准确率")
	private String chiefPhoneAccuracy;
	
	@ExcelField(title = "所属总经理")
	private String managerName;
	
	@ExcelField(title = "电话准确率")
	private String managerPhoneAccuracy;

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getPhoneAccuracy() {
		return phoneAccuracy;
	}

	public void setPhoneAccuracy(String phoneAccuracy) {
		this.phoneAccuracy = phoneAccuracy;
	}

	public Integer getSatisfyRating() {
		return satisfyRating;
	}

	public void setSatisfyRating(Integer satisfyRating) {
		this.satisfyRating = satisfyRating;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getMasterPhoneAccuracy() {
		return masterPhoneAccuracy;
	}

	public void setMasterPhoneAccuracy(String masterPhoneAccuracy) {
		this.masterPhoneAccuracy = masterPhoneAccuracy;
	}

	public String getChiefName() {
		return chiefName;
	}

	public void setChiefName(String chiefName) {
		this.chiefName = chiefName;
	}

	public String getChiefPhoneAccuracy() {
		return chiefPhoneAccuracy;
	}

	public void setChiefPhoneAccuracy(String chiefPhoneAccuracy) {
		this.chiefPhoneAccuracy = chiefPhoneAccuracy;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhoneAccuracy() {
		return managerPhoneAccuracy;
	}

	public void setManagerPhoneAccuracy(String managerPhoneAccuracy) {
		this.managerPhoneAccuracy = managerPhoneAccuracy;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPropertyAddr() {
		return propertyAddr;
	}

	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}

}
