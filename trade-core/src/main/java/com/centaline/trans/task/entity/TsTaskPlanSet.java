package com.centaline.trans.task.entity;

import java.util.Date;

public class TsTaskPlanSet {
	private Long pkid;
	private String partCode;
	private Integer planDays;
	private String isManualSet;
	private Date createTime;
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public Integer getPlanDays() {
		return planDays;
	}
	public void setPlanDays(Integer planDays) {
		this.planDays = planDays;
	}
	public String getIsManualSet() {
		return isManualSet;
	}
	public void setIsManualSet(String isManualSet) {
		this.isManualSet = isManualSet;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
