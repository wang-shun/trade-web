package com.centaline.trans.eval.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToEvaFeeRecord {
    private Long pkid;

    private String caseCode;

    private Date recordTime;

    private BigDecimal evalFee;

    private String isEvalFeeGet;

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

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public BigDecimal getEvalFee() {
        return evalFee;
    }

    public void setEvalFee(BigDecimal evalFee) {
        this.evalFee = evalFee;
    }

    public String getIsEvalFeeGet() {
        return isEvalFeeGet;
    }

    public void setIsEvalFeeGet(String isEvalFeeGet) {
        this.isEvalFeeGet = isEvalFeeGet == null ? null : isEvalFeeGet.trim();
    }
}