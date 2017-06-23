package com.centaline.trans.wdcase.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TpdCommSubsDetals {
    private Long pkid;

    private Long commSubsId;

    private String caseCode;

    private String bizCode;

    private String commSubject;

    private String srvDesc;

    private BigDecimal stdRate;

    private BigDecimal stdCost;

    private BigDecimal discount;

    private BigDecimal commCost;

    private BigDecimal buyerCost;

    private BigDecimal buyerPaid;

    private BigDecimal sellerCost;

    private BigDecimal sellerPaid;

    private String payee;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private Integer isDeleted;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public Long getCommSubsId() {
        return commSubsId;
    }

    public void setCommSubsId(Long commSubsId) {
        this.commSubsId = commSubsId;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode == null ? null : bizCode.trim();
    }

    public String getCommSubject() {
        return commSubject;
    }

    public void setCommSubject(String commSubject) {
        this.commSubject = commSubject == null ? null : commSubject.trim();
    }

    public String getSrvDesc() {
        return srvDesc;
    }

    public void setSrvDesc(String srvDesc) {
        this.srvDesc = srvDesc == null ? null : srvDesc.trim();
    }

    public BigDecimal getStdRate() {
        return stdRate;
    }

    public void setStdRate(BigDecimal stdRate) {
        this.stdRate = stdRate;
    }

    public BigDecimal getStdCost() {
        return stdCost;
    }

    public void setStdCost(BigDecimal stdCost) {
        this.stdCost = stdCost;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getCommCost() {
        return commCost;
    }

    public void setCommCost(BigDecimal commCost) {
        this.commCost = commCost;
    }

    public BigDecimal getBuyerCost() {
        return buyerCost;
    }

    public void setBuyerCost(BigDecimal buyerCost) {
        this.buyerCost = buyerCost;
    }

    public BigDecimal getBuyerPaid() {
        return buyerPaid;
    }

    public void setBuyerPaid(BigDecimal buyerPaid) {
        this.buyerPaid = buyerPaid;
    }

    public BigDecimal getSellerCost() {
        return sellerCost;
    }

    public void setSellerCost(BigDecimal sellerCost) {
        this.sellerCost = sellerCost;
    }

    public BigDecimal getSellerPaid() {
        return sellerPaid;
    }

    public void setSellerPaid(BigDecimal sellerPaid) {
        this.sellerPaid = sellerPaid;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee == null ? null : payee.trim();
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}