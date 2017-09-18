package com.centaline.scheduler.sync.bean;

import com.centaline.scheduler.sync.enums.CityType;

/**
 * 同步用 对应HR系统中的员工部门职务
 * 初始化设置对应的城市
 *
 * @author yinchao
 */
public class SyncEmpOrgPos {
	private CityType city;
	private String empId;
	private String empName;
	private String deptId;
	private String deptName;
	private String positionId;
	private String positionName;
	private int primary;
	private int leader;

	public SyncEmpOrgPos(CityType city) {
		this.city = city;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
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

	public int getPrimary() {
		return primary;
	}

	public void setPrimary(int primary) {
		this.primary = primary;
	}

	public String getCity() {
		return city.getCode();
	}

	public int getLeader() {
		return leader;
	}

	public void setLeader(int leader) {
		this.leader = leader;
	}
}
