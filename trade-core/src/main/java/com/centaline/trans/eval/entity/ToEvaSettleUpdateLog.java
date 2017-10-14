package com.centaline.trans.eval.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ToEvaSettleUpdateLog {
    private Long pkid;

    private String caseCode;

    private String evaCode;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date updateTime;

    private String updateReason;

    private String rejectPerson;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date approTime;

    private String rejectCause;

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

    public String getEvaCode() {
        return evaCode;
    }

    public void setEvaCode(String evaCode) {
        this.evaCode = evaCode == null ? null : evaCode.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateReason() {
        return updateReason;
    }

    public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason == null ? null : updateReason.trim();
    }

    public String getRejectPerson() {
        return rejectPerson;
    }

    public void setRejectPerson(String rejectPerson) {
        this.rejectPerson = rejectPerson == null ? null : rejectPerson.trim();
    }

    public Date getApproTime() {
        return approTime;
    }

    public void setApproTime(Date approTime) {
        this.approTime = approTime;
    }

    public String getRejectCause() {
        return rejectCause;
    }

    public void setRejectCause(String rejectCause) {
        this.rejectCause = rejectCause == null ? null : rejectCause.trim();
    }
}