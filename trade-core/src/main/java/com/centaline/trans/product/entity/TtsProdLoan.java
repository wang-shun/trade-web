package com.centaline.trans.product.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TtsProdLoan {
    private Long pkid;

    private Long prodId;

    private BigDecimal maxAmout;

    private String firstIntCharge;

    private String earlyPay;

    private BigDecimal earlyPayLd;

    private BigDecimal latePayLd;

    private String extendTerm;

    private String loanBank;

    private String paymentAnd;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public BigDecimal getMaxAmout() {
        return maxAmout;
    }

    public void setMaxAmout(BigDecimal maxAmout) {
        this.maxAmout = maxAmout;
    }

    public String getFirstIntCharge() {
        return firstIntCharge;
    }

    public void setFirstIntCharge(String firstIntCharge) {
        this.firstIntCharge = firstIntCharge == null ? null : firstIntCharge.trim();
    }

    public String getEarlyPay() {
        return earlyPay;
    }

    public void setEarlyPay(String earlyPay) {
        this.earlyPay = earlyPay == null ? null : earlyPay.trim();
    }

    public BigDecimal getEarlyPayLd() {
        return earlyPayLd;
    }

    public void setEarlyPayLd(BigDecimal earlyPayLd) {
        this.earlyPayLd = earlyPayLd;
    }

    public BigDecimal getLatePayLd() {
        return latePayLd;
    }

    public void setLatePayLd(BigDecimal latePayLd) {
        this.latePayLd = latePayLd;
    }

    public String getExtendTerm() {
        return extendTerm;
    }

    public void setExtendTerm(String extendTerm) {
        this.extendTerm = extendTerm == null ? null : extendTerm.trim();
    }

    public String getLoanBank() {
        return loanBank;
    }

    public void setLoanBank(String loanBank) {
        this.loanBank = loanBank == null ? null : loanBank.trim();
    }

    public String getPaymentAnd() {
        return paymentAnd;
    }

    public void setPaymentAnd(String paymentAnd) {
        this.paymentAnd = paymentAnd == null ? null : paymentAnd.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
}