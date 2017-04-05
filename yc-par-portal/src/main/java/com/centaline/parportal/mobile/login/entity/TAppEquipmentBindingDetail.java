package com.centaline.parportal.mobile.login.entity;

import java.util.Date;


/**
 * app登录  手机绑定
 * @author yumin3
 * @date 2016-08-10
 *
 */
public class TAppEquipmentBindingDetail {

	/**
	 * 编号
	 */
	private long id;
	
	/**
	 * 三级市场 填 Agency
	 */
	private String appName;
	
	/**
	 * 员工ID
	 */
	private String userId;
	
	/**
	 * 员工登录名
	 */
	private String userName;
	
	/**
	 * 员工编号
	 */
	private String empCode;
	
	/**
	 * 员工组织
	 */
	private String orgId;
	
	/**
	 * 设备ID
	 */
	private String equipmentId;
	
	/**
	 * 手机系统
	 */
	private String equipmentSystem;
	
	/**
	 * 手机品牌
	 */
	private String equipmentName;
	
	
	/**
	 * 状态(1 有效、0 无效)
	 */
	private int status;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 数据生成时间
	 */
	private Date createTime;
	
	/**
	 * 用户名
	 */
	private String appUserName;
	

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEquipmentSystem() {
		return equipmentSystem;
	}

	public void setEquipmentSystem(String equipmentSystem) {
		this.equipmentSystem = equipmentSystem;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	
}