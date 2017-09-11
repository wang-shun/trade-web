package com.centaline.trans.task.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToRatePayment {
    private Long pkid;

    private String caseCode;

    private BigDecimal personalIncomeTax;

    private BigDecimal businessTax;

    private BigDecimal contractTax;

    private BigDecimal landIncrementTax;

    private BigDecimal pricingTax;

    private String comment;

    private String createBy;

    private Date createTime;

    private Date paymentTime;

    private String partCode;

    private String autualOperatorId;

    private String autualOperatorName;

    public String getAutualOperatorName() {
        return autualOperatorName;
    }

    public void setAutualOperatorName(String autualOperatorName) {
        this.autualOperatorName = autualOperatorName;
    }

    public String getAutualOperatorId() {
        return autualOperatorId;
    }

    public void setAutualOperatorId(String autualOperatorId) {
        this.autualOperatorId = autualOperatorId;
    }

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

    public BigDecimal getPricingTax() {
        return pricingTax;
    }

    public void setPricingTax(BigDecimal pricingTax) {
        this.pricingTax = pricingTax;
    }

    public BigDecimal getPersonalIncomeTax() {
        return personalIncomeTax;
    }

    public void setPersonalIncomeTax(BigDecimal personalIncomeTax) {
        this.personalIncomeTax = personalIncomeTax;
    }

    public BigDecimal getBusinessTax() {
        return businessTax;
    }

    public void setBusinessTax(BigDecimal businessTax) {
        this.businessTax = businessTax;
    }

    public BigDecimal getContractTax() {
        return contractTax;
    }

    public void setContractTax(BigDecimal contractTax) {
        this.contractTax = contractTax;
    }

    public BigDecimal getLandIncrementTax() {
        return landIncrementTax;
    }

    public void setLandIncrementTax(BigDecimal landIncrementTax) {
        this.landIncrementTax = landIncrementTax;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
    }
}