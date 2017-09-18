package com.centaline.scheduler.sync.bean;

import com.centaline.scheduler.sync.enums.CityType;

/**
 * 同步用 对应HR系统中的部门职务
 * 初始化设置对应的城市
 *
 * @author yinchao
 */
public class SyncPosition {
	private CityType city;
	private String id;
	private String name;
	private String deptId;
	private String deptName;
	private String deptCode;
	private String deptStatus;

	public SyncPosition(CityType city) {
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptStatus() {
		return deptStatus;
	}

	public void setDeptStatus(String deptStatus) {
		this.deptStatus = deptStatus;
	}

	public String getCity() {
		return city.getCode();
	}

	@Override
	public String toString() {
		return "BJPosition [id=" + id + ", name=" + name + ", deptId=" + deptId + ", deptName=" + deptName
				+ ", deptCode=" + deptCode + ", deptStatus=" + deptStatus + "]";
	}
}
