package com.centaline.scheduler.sync.bean;

import java.sql.Date;

import com.centaline.scheduler.sync.enums.CityType;

/**
 * 同步用 对应HR系统中的员工
 * 初始化设置对应的城市
 *
 * @author yinchao
 */
public class SyncEmployee {
	private CityType city;
	private String id;
	private String name;
	private int sex;
	private Date birthday;
	private String email;
	private String account;
	private String code;
	private String mobile;
	private String deptId;
	private String deptCode;
	private String deptName;
	private String positionId;
	private String positionName;

	public SyncEmployee(CityType city) {
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getCity() {
		return city.getCode();
	}

	@Override
	public String toString() {
		return "BJEmployee [id=" + id + ", name=" + name + ", sex=" + sex + ", birthday=" + birthday + ", email="
				+ email + ", account=" + account + ", code=" + code + ", mobile=" + mobile + ", deptId=" + deptId
				+ ", deptCode=" + deptCode + ", deptName=" + deptName + ", positionId=" + positionId + ", positionName="
				+ positionName + "]";
	}
}
