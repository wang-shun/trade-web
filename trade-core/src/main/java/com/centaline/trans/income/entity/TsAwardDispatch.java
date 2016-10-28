package com.centaline.trans.income.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TsAwardDispatch {
    private Long pkid;

    private String caseCode;

    private String participant;

    private BigDecimal baseAmount;

    private Double phoneAccuracy;

    private Integer satisfyRating;

    private Integer eplusCurrentMonth;

    private Double kpi;

    private BigDecimal finalAmount;

    private Date importTime;

    private Date dispatchTime;

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

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant == null ? null : participant.trim();
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Double getPhoneAccuracy() {
        return phoneAccuracy;
    }

    public void setPhoneAccuracy(Double phoneAccuracy) {
        this.phoneAccuracy = phoneAccuracy;
    }

    public Integer getSatisfyRating() {
        return satisfyRating;
    }

    public void setSatisfyRating(Integer satisfyRating) {
        this.satisfyRating = satisfyRating;
    }

    public Integer getEplusCurrentMonth() {
        return eplusCurrentMonth;
    }

    public void setEplusCurrentMonth(Integer eplusCurrentMonth) {
        this.eplusCurrentMonth = eplusCurrentMonth;
    }

    public Double getKpi() {
        return kpi;
    }

    public void setKpi(Double kpi) {
        this.kpi = kpi;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }
}