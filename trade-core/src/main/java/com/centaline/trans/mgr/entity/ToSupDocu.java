package com.centaline.trans.mgr.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ToSupDocu {
    private Long pkid;

    private Date remindTime;

    private String partCode;

    private String caseCode;

    private String supContent;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
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

    public String getSupContent() {
        return supContent;
    }

    public void setSupContent(String supContent) {
        this.supContent = supContent == null ? null : supContent.trim();
    }
}