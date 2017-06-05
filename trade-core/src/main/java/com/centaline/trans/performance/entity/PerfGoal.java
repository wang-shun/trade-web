package com.centaline.trans.performance.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PerfGoal {
	private Long pkid;
	
	private Long mainId;

	private String district;

	private String team;

	private String job;

	private String userId;

	private Date belongMonth;

	private String status;

	private String createBy;

	private Date createTime;

	private String updateBy;

	private Date updateTime;

	private BigDecimal goalPerf;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district == null ? null : district.trim();
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team == null ? null : team.trim();
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job == null ? null : job.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public Date getBelongMonth() {
		return belongMonth;
	}

	public void setBelongMonth(Date belongMonth) {
		this.belongMonth = belongMonth;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getGoalPerf() {
		return goalPerf;
	}

	public void setGoalPerf(BigDecimal goalPerf) {
		this.goalPerf = goalPerf;
	}

	public Long getMainId() {
		return mainId;
	}

	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
}