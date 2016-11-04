package com.centaline.trans.task.entity;

import java.util.Date;

public class ToGetPropertyBook {
    private Long pkid;

    private String partCode;

    private String caseCode;

    private Date realPropertyGetTime;

    private String commet;

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

    public Date getRealPropertyGetTime() {
        return realPropertyGetTime;
    }

    public void setRealPropertyGetTime(Date realPropertyGetTime) {
        this.realPropertyGetTime = realPropertyGetTime;
    }

    public String getCommet() {
        return commet;
    }

    public void setCommet(String commet) {
        this.commet = commet == null ? null : commet.trim();
    }
}