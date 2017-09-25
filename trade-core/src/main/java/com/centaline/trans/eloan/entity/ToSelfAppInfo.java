package com.centaline.trans.eloan.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自办申请信息
 * @author wblujian
 *
 */
public class ToSelfAppInfo {

	private Long pkid;
	
	private String ccaiCode;
	
	private String caseCode;
	
	private String type; //申请类型
	
	private String userName ;//申请人域账号
	
	private String realName;//申请人名称
	
	private String grpCode ;// 申请人所属部门HROC ,
	
	private String grpName ;// 申请人所属部门名称 
	
	private Date applyTime;// 申请时间 ,
	
	private String status; //状态 
	
	private String city ;//信息对应的城市行政区划编码 = ['110000-北京', '120000-天津']stringEnum:"110000-北京", "120000-天津"

	private List<ToAppRecordInfo> tasks = new ArrayList<ToAppRecordInfo>();
	
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getCcaiCode() {
		return ccaiCode;
	}

	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGrpCode() {
		return grpCode;
	}

	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<ToAppRecordInfo> getTasks() {
		return tasks;
	}

	public void setTasks(List<ToAppRecordInfo> tasks) {
		this.tasks = tasks;
	}

	
	
	
}
