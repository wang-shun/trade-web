package com.centaline.trans.task.entity;

public class ToFirstFollow {
    private Long pkid;

    private String caseCode;

    private String partCode;

    private String isPerchaseReserachNeed;

    private String isLoanClose;

    private String comment;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
    }

    public String getIsPerchaseReserachNeed() {
        return isPerchaseReserachNeed;
    }

    public void setIsPerchaseReserachNeed(String isPerchaseReserachNeed) {
        this.isPerchaseReserachNeed = isPerchaseReserachNeed == null ? null : isPerchaseReserachNeed.trim();
    }

    public String getIsLoanClose() {
        return isLoanClose;
    }

    public void setIsLoanClose(String isLoanClose) {
        this.isLoanClose = isLoanClose == null ? null : isLoanClose.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}