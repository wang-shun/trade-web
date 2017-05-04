package com.centaline.trans.task.entity;

import java.util.Date;

public class TlTaskReassigntLog {
    private Integer pkid;

    private String caseCode;

    private String procInstId;

    private String taskId;

    private String taskDefKey;

    private String oriAssignee;

    private String newAssignee;

    private String remark;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    public Integer getPkid() {
        return pkid;
    }

    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId == null ? null : procInstId.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey == null ? null : taskDefKey.trim();
    }

    public String getOriAssignee() {
        return oriAssignee;
    }

    public void setOriAssignee(String oriAssignee) {
        this.oriAssignee = oriAssignee == null ? null : oriAssignee.trim();
    }

    public String getNewAssignee() {
        return newAssignee;
    }

    public void setNewAssignee(String newAssignee) {
        this.newAssignee = newAssignee == null ? null : newAssignee.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}