package com.centaline.trans.perform.vo;

import com.aist.common.utils.excel.annotation.ExcelField;

public class GustFollowVo {
	@ExcelField(title = "人员")
	private String userName;
	@ExcelField(title = "人员编号")
	private String employeeCode;
	/**
	 * 角色
	 */
	@ExcelField(title = "角色")
	private String jobName;
	@ExcelField(title = "案件编号")
	private String caseCode;
	@ExcelField(title = "案件地址")
	private String caseAddress;
	/**
	 * 满意度评分
	 */
	@ExcelField(title = "满意度评分")
	private Integer satisfyDegree;
	/**
	 * 电话准确率
	 */
	@ExcelField(title = "电话准确率")
	private Double phoneAccuracy;
	/**
	 * 主管
	 */
	@ExcelField(title = "所属主管")
	private String director;
	/**
	 * 主管电话准确率
	 */
	@ExcelField(title = "主管电话准确率")
	private Double directorPhoneAccuracy;
	/**
	 * 总监
	 */
	@ExcelField(title = "所属总监")
	private String chiefInspector;
	/**
	 * 总监电话准确率
	 */
	@ExcelField(title = "总监电话准确率")
	private Double ciPhoneAccuracy;
	/**
	 * 总经理
	 */
	@ExcelField(title = "所属总经理")
	private String generalManager;
	/**
	 * 总经理电话准确率
	 */
	@ExcelField(title = "总经理电话准确率")
	private Double gmPhoneAccuracy;
	private String errorMsg;

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

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

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

	public Integer getSatisfyDegree() {
		return satisfyDegree;
	}

	public void setSatisfyDegree(Integer satisfyDegree) {
		this.satisfyDegree = satisfyDegree;
	}

	public Double getPhoneAccuracy() {
		return phoneAccuracy;
	}

	public void setPhoneAccuracy(Double phoneAccuracy) {
		this.phoneAccuracy = phoneAccuracy;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Double getDirectorPhoneAccuracy() {
		return directorPhoneAccuracy;
	}

	public void setDirectorPhoneAccuracy(Double directorPhoneAccuracy) {
		this.directorPhoneAccuracy = directorPhoneAccuracy;
	}

	public String getChiefInspector() {
		return chiefInspector;
	}

	public void setChiefInspector(String chiefInspector) {
		this.chiefInspector = chiefInspector;
	}

	public Double getCiPhoneAccuracy() {
		return ciPhoneAccuracy;
	}

	public void setCiPhoneAccuracy(Double ciPhoneAccuracy) {
		this.ciPhoneAccuracy = ciPhoneAccuracy;
	}

	public String getGeneralManager() {
		return generalManager;
	}

	public void setGeneralManager(String generalManager) {
		this.generalManager = generalManager;
	}

	public Double getGmPhoneAccuracy() {
		return gmPhoneAccuracy;
	}

	public void setGmPhoneAccuracy(Double gmPhoneAccuracy) {
		this.gmPhoneAccuracy = gmPhoneAccuracy;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
