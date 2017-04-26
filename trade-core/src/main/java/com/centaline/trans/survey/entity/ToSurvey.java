package com.centaline.trans.survey.entity;

import java.util.Date;

public class ToSurvey {
    private Long pkid;

    private String transferHandleStatus;

    private String sellerPhoneCorrect;

    private String sellerPhoneResult;

    private String signHandleStatus;

    private String leadingProcessorid;

    private String comment;

    private String isDeleted;

    private String status;

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

    public String getTransferHandleStatus() {
        return transferHandleStatus;
    }

    public void setTransferHandleStatus(String transferHandleStatus) {
        this.transferHandleStatus = transferHandleStatus == null ? null : transferHandleStatus.trim();
    }

    public String getSellerPhoneCorrect() {
        return sellerPhoneCorrect;
    }

    public void setSellerPhoneCorrect(String sellerPhoneCorrect) {
        this.sellerPhoneCorrect = sellerPhoneCorrect == null ? null : sellerPhoneCorrect.trim();
    }

    public String getSellerPhoneResult() {
        return sellerPhoneResult;
    }

    public void setSellerPhoneResult(String sellerPhoneResult) {
        this.sellerPhoneResult = sellerPhoneResult == null ? null : sellerPhoneResult.trim();
    }

    public String getSignHandleStatus() {
        return signHandleStatus;
    }

    public void setSignHandleStatus(String signHandleStatus) {
        this.signHandleStatus = signHandleStatus == null ? null : signHandleStatus.trim();
    }

    public String getLeadingProcessorid() {
        return leadingProcessorid;
    }

    public void setLeadingProcessorid(String leadingProcessorid) {
        this.leadingProcessorid = leadingProcessorid == null ? null : leadingProcessorid.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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