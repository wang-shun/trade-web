package com.centaline.trans.performance.entity;

import java.util.Date;

public class Subject {
	private long pkId;
	private long parentId;
	private String subjectCode;
	private String subjectName;
	private String isForagent;
	private String createBy;
	private Date createTime;
	private Date updateTime;
	private String updateBy;
	private String isActive;
	private Date inactiveTime;
	public long getPkId() {
		return pkId;
	}
	public void setPkId(long pkId) {
		this.pkId = pkId;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getIsForagent() {
		return isForagent;
	}
	public void setIsForagent(String isForagent) {
		this.isForagent = isForagent;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Date getInactiveTime() {
		return inactiveTime;
	}
	public void setInactiveTime(Date inactiveTime) {
		this.inactiveTime = inactiveTime;
	}
}
