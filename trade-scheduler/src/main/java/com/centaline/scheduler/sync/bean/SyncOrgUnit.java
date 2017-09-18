package com.centaline.scheduler.sync.bean;

import com.centaline.scheduler.sync.enums.CityType;

import java.sql.Date;

/**
 * 同步用 对应HR系统中的组织架构
 * 初始化设置对应的城市
 *
 * @author yinchao
 */
public class SyncOrgUnit {
	private CityType city;
	private String id;
	private String name;
	private String code;
	private String type;
	private String fullName;
	private String parentId;
	private Date createTime;
	private Date endTime;
	private String orderCode;
	private String tel;
	private String hroc;
	private String ccaiDepId;

	public SyncOrgUnit(CityType city) {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCity() {
		return city.getCode();
	}

	public String getHroc() {
		return hroc;
	}

	public void setHroc(String hroc) {
		this.hroc = hroc;
	}

	public String getCcaiDepId() {
		return ccaiDepId;
	}

	public void setCcaiDepId(String ccaiDepId) {
		this.ccaiDepId = ccaiDepId;
	}

	@Override
	public String toString() {
		return "SyncOrgUnit [city=" + city + ", id=" + id + ", name=" + name + ", code=" + code + ", type=" + type
				+ ", fullName=" + fullName + ", parentId=" + parentId + ", createTime=" + createTime + ", endTime="
				+ endTime + ", orderCode=" + orderCode + ", tel=" + tel + ", hroc=" + hroc + ", ccaiDepId=" + ccaiDepId
				+ "]";
	}
}
