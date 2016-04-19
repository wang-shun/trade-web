package com.centaline.trans.kpi.vo;

import java.util.Date;

import com.aist.common.utils.excel.annotation.ExcelField;

public class KpiMonthVO {
	@ExcelField(title = "人员")
	private String userName;
	@ExcelField(title = "员工编号")
	private String employeeCode;
	@ExcelField(title = "所在组织")
	private String orgName;
	@ExcelField(title = "岗位")
	private String jobName;
	@ExcelField(title = "当月金融产品完成单数(包含外单)")
	private Long finOrder;
	@ExcelField(title = "归属月份")
	private Date belongMonth;

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

	public Long getFinOrder() {
		return finOrder;
	}

	public void setFinOrder(Long finOrder) {
		this.finOrder = finOrder;
	}

	public Date getBelongMonth() {
		return belongMonth;
	}

	public void setBelongMonth(Date belongMonth) {
		this.belongMonth = belongMonth;
	}

}
