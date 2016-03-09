package com.centaline.trans.task.entity;

import java.util.Date;
/**查限购*/
public class ToPurchaseLimitSearch {
    private Long pkid;

    private String partId;

    private String caseCode;

    private Date realPlsTime;

    private String comment;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId == null ? null : partId.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public Date getRealPlsTime() {
        return realPlsTime;
    }

    public void setRealPlsTime(Date realPlsTime) {
        this.realPlsTime = realPlsTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}