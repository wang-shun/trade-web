package com.centaline.trans.task.entity;

import java.util.Date;

public class TlTaskSubmitLog {
    private Long pkid;

    private Long taskId;

    private String executor;

    private String executorId;

    private String teamId;

    private String districtId;

    private String hqId;

    private String managerId;

    private String directorId;

    private String generalManagerId;
    
    private String seniorManagerId;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor == null ? null : executor.trim();
    }

    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId == null ? null : executorId.trim();
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId == null ? null : teamId.trim();
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId == null ? null : districtId.trim();
    }

    public String getHqId() {
        return hqId;
    }

    public void setHqId(String hqId) {
        this.hqId = hqId == null ? null : hqId.trim();
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
    }

    public String getDirectorId() {
        return directorId;
    }

    public void setDirectorId(String directorId) {
        this.directorId = directorId == null ? null : directorId.trim();
    }

    public String getGeneralManagerId() {
        return generalManagerId;
    }

    public void setGeneralManagerId(String generalManagerId) {
        this.generalManagerId = generalManagerId == null ? null : generalManagerId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
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
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

	public String getSeniorManagerId() {
		return seniorManagerId;
	}

	public void setSeniorManagerId(String seniorManagerId) {
		this.seniorManagerId = seniorManagerId;
	}
}