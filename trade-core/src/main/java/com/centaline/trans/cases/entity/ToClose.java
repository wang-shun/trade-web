package com.centaline.trans.cases.entity;

import java.util.Date;

public class ToClose {
    private Long pkid;

    private String partCode;

    private String caseCode;

    private String partApprover;

    private Date approveTime;

    private String comment;

    private String approveCommet;

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
        this.partCode = partCode == null ? null : partCode.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getPartApprover() {
        return partApprover;
    }

    public void setPartApprover(String partApprover) {
        this.partApprover = partApprover == null ? null : partApprover.trim();
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getApproveCommet() {
        return approveCommet;
    }

    public void setApproveCommet(String approveCommet) {
        this.approveCommet = approveCommet == null ? null : approveCommet.trim();
    }
}