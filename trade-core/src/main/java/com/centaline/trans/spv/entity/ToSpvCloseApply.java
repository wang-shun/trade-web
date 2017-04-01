package com.centaline.trans.spv.entity;

import java.util.Date;

public class ToSpvCloseApply {
    private Long pkid;

    private String spvCode;

    private String spvCloseCode;

    private String closeType;

    private Date applyTime;

    private String applier;

    private Date auditTime;

    private String auditor;

    private Date reAuditTime;

    private String reAuditor;

    private String comment;

    private String status;

    private String isDeleted;

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

    public String getSpvCode() {
        return spvCode;
    }

    public void setSpvCode(String spvCode) {
        this.spvCode = spvCode == null ? null : spvCode.trim();
    }

    public String getSpvCloseCode() {
        return spvCloseCode;
    }

    public void setSpvCloseCode(String spvCloseCode) {
        this.spvCloseCode = spvCloseCode == null ? null : spvCloseCode.trim();
    }

    public String getCloseType() {
        return closeType;
    }

    public void setCloseType(String closeType) {
        this.closeType = closeType == null ? null : closeType.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplier() {
        return applier;
    }

    public void setApplier(String applier) {
        this.applier = applier == null ? null : applier.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public Date getReAuditTime() {
        return reAuditTime;
    }

    public void setReAuditTime(Date reAuditTime) {
        this.reAuditTime = reAuditTime;
    }

    public String getReAuditor() {
        return reAuditor;
    }

    public void setReAuditor(String reAuditor) {
        this.reAuditor = reAuditor == null ? null : reAuditor.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
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