package com.centaline.trans.task.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wbzhouht
 * 缴税
 */
public class ToRatePayment {
    private Long pkid;

    private String caseCode;//交易编号

    private BigDecimal personalIncomeTax;//个人所得税

    private BigDecimal businessTax;//增值税及附加税

    private BigDecimal contractTax;//买方契税

    private BigDecimal landIncrementTax;//土地增值税

    private BigDecimal pricingTax;//核税价

    private String comment;//备注

    private String createBy;//创建人

    private Date createTime;//创建时间

    private Date paymentTime;//缴税时间

    private String partCode;//环节编码

    private String autualOperatorId;//实际操作者id

    private String autualOperatorName;//实际操作者姓名

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