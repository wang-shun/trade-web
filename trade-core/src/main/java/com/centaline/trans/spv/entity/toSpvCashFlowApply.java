package com.centaline.trans.spv.entity;

import java.util.Date;

public class toSpvCashFlowApply {
    private Long pkid;

    private String spvCode;

    private String cashflowApplyCode;

    private String usage;

    private String comment;

    private String status;

    private String isDeleted;

    private String applier;

    private String applyAuditor;

    private String ftPreAuditor;

    private String ftPostAuditor;

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

    public String getCashflowApplyCode() {
        return cashflowApplyCode;
    }

    public void setCashflowApplyCode(String cashflowApplyCode) {
        this.cashflowApplyCode = cashflowApplyCode == null ? null : cashflowApplyCode.trim();
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage == null ? null : usage.trim();
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

    public String getApplier() {
        return applier;
    }

    public void setApplier(String applier) {
        this.applier = applier == null ? null : applier.trim();
    }

    public String getApplyAuditor() {
        return applyAuditor;
    }

    public void setApplyAuditor(String applyAuditor) {
        this.applyAuditor = applyAuditor == null ? null : applyAuditor.trim();
    }

    public String getFtPreAuditor() {
        return ftPreAuditor;
    }

    public void setFtPreAuditor(String ftPreAuditor) {
        this.ftPreAuditor = ftPreAuditor == null ? null : ftPreAuditor.trim();
    }

    public String getFtPostAuditor() {
        return ftPostAuditor;
    }

    public void setFtPostAuditor(String ftPostAuditor) {
        this.ftPostAuditor = ftPostAuditor == null ? null : ftPostAuditor.trim();
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