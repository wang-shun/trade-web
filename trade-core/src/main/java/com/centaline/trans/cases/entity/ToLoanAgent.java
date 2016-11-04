package com.centaline.trans.cases.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToLoanAgent {
    private Long pkid;

    private String caseCode;

    private String loanSrvCode;

    private String finOrgCode;

    private String custName;

    private String custPhone;

    private BigDecimal loanAmount;

    private BigDecimal actualAmount;

    private Integer month;

    private String zjName;

    private String zjCode;

    private String coName;

    private String coCode;

    private Double awardPer;

    private String applyStatus;

    private Date confirmTime;

    private String confirmStatus;

    private Date lastExceedExportTime;

    private Date applyTime;

    private Date signTime;

    private Date releaseTime;

    private Date incomeConfirmTime;

    private Date incomeArriveTime;

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

    public String getLoanSrvCode() {
        return loanSrvCode;
    }

    public void setLoanSrvCode(String loanSrvCode) {
        this.loanSrvCode = loanSrvCode == null ? null : loanSrvCode.trim();
    }

    public String getFinOrgCode() {
        return finOrgCode;
    }

    public void setFinOrgCode(String finOrgCode) {
        this.finOrgCode = finOrgCode == null ? null : finOrgCode.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone == null ? null : custPhone.trim();
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getZjName() {
        return zjName;
    }

    public void setZjName(String zjName) {
        this.zjName = zjName == null ? null : zjName.trim();
    }

    public String getZjCode() {
        return zjCode;
    }

    public void setZjCode(String zjCode) {
        this.zjCode = zjCode == null ? null : zjCode.trim();
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName == null ? null : coName.trim();
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode == null ? null : coCode.trim();
    }

    public Double getAwardPer() {
        return awardPer;
    }

    public void setAwardPer(Double awardPer) {
        this.awardPer = awardPer;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus == null ? null : applyStatus.trim();
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus == null ? null : confirmStatus.trim();
    }

    public Date getLastExceedExportTime() {
        return lastExceedExportTime;
    }

    public void setLastExceedExportTime(Date lastExceedExportTime) {
        this.lastExceedExportTime = lastExceedExportTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Date getIncomeConfirmTime() {
        return incomeConfirmTime;
    }

    public void setIncomeConfirmTime(Date incomeConfirmTime) {
        this.incomeConfirmTime = incomeConfirmTime;
    }

    public Date getIncomeArriveTime() {
        return incomeArriveTime;
    }

    public void setIncomeArriveTime(Date incomeArriveTime) {
        this.incomeArriveTime = incomeArriveTime;
    }
}