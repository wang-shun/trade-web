package com.centaline.trans.perform.vo;

import java.util.Date;

import com.aist.common.utils.excel.annotation.ExcelField;

public class EplusVo {
	@ExcelField(title = "人员")
	private String userNmae;
	@ExcelField(title = "员工编号")
	private String employeeCode;
	@ExcelField(title = "所在组织")
	private String orgName;
	@ExcelField(title = "岗位")
	private String jobName;
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@ExcelField(title = "当月金融产品完成单数（包含外单）")
	private Integer orders;
	@ExcelField(title = "归属月份")
	private Date belongMonth;
	
	private String errorMsg;

	public String getUserNmae() {
		return userNmae;
	}

	public void setUserNmae(String userNmae) {
		this.userNmae = userNmae;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public Date getBelongMonth() {
		return belongMonth;
	}

	public void setBelongMonth(Date belongMonth) {
		this.belongMonth = belongMonth;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
