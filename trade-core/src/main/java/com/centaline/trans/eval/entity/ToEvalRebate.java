package com.centaline.trans.eval.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToEvalRebate {
    private Long pkid;

    private String caseCode;

    private String evaCode;

    private String evalRecept;

    private Date pricingTime;

    private String oringinCaseCode;

    private BigDecimal evalRealCharges;

    private BigDecimal evalDueCharges;

    private BigDecimal centaComAmount;

    private BigDecimal evaComAmount;

    private Date inputTime;

    private String evaRpocessId;

    private String status;

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

    public String getEvalRecept() {
        return evalRecept;
    }

    public void setEvalRecept(String evalRecept) {
        this.evalRecept = evalRecept == null ? null : evalRecept.trim();
    }

    public Date getPricingTime() {
        return pricingTime;
    }

    public void setPricingTime(Date pricingTime) {
        this.pricingTime = pricingTime;
    }

    public String getOringinCaseCode() {
        return oringinCaseCode;
    }

    public void setOringinCaseCode(String oringinCaseCode) {
        this.oringinCaseCode = oringinCaseCode == null ? null : oringinCaseCode.trim();
    }

    public BigDecimal getEvalRealCharges() {
        return evalRealCharges;
    }

    public void setEvalRealCharges(BigDecimal evalRealCharges) {
        this.evalRealCharges = evalRealCharges;
    }

    public BigDecimal getEvalDueCharges() {
        return evalDueCharges;
    }

    public void setEvalDueCharges(BigDecimal evalDueCharges) {
        this.evalDueCharges = evalDueCharges;
    }

    public BigDecimal getCentaComAmount() {
        return centaComAmount;
    }

    public void setCentaComAmount(BigDecimal centaComAmount) {
        this.centaComAmount = centaComAmount;
    }

    public BigDecimal getEvaComAmount() {
        return evaComAmount;
    }

    public void setEvaComAmount(BigDecimal evaComAmount) {
        this.evaComAmount = evaComAmount;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public String getEvaRpocessId() {
        return evaRpocessId;
    }

    public void setEvaRpocessId(String evaRpocessId) {
        this.evaRpocessId = evaRpocessId == null ? null : evaRpocessId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}