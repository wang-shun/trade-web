package com.centaline.trans.award.vo;

import java.util.Date;

import com.aist.common.utils.excel.annotation.ExcelField;

public class BaseImportVo {
	@ExcelField(title="案件编号")
	private String caseCode;
	@ExcelField(title="案件地址")
	private String caseAddress;
	@ExcelField(title="人员编号")
	private String employeeCode;
	@ExcelField(title="人员姓名")
	private String userName;
	@ExcelField(title="岗位")
	private String jobName;
	@ExcelField(title="组织")
	private String orgName;
	@ExcelField(title="所得金额")
	private Double amount;
	@ExcelField(title="归属月份")
	private Date belongMonth;
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getCaseAddress() {
		return caseAddress;
	}
	public void setCaseAddress(String caseAddress) {
		this.caseAddress = caseAddress;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getBelongMonth() {
		return belongMonth;
	}
	public void setBelongMonth(Date belongMonth) {
		this.belongMonth = belongMonth;
	}
	
}
