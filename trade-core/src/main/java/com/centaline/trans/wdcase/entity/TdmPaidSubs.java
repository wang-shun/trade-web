package com.centaline.trans.wdcase.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TdmPaidSubs {
    private Long pkid;

    private String paymentCode;

    private String commSubject;

    private BigDecimal commAmount;

    private BigDecimal paidAmount;

    private BigDecimal buyerComm;

    private BigDecimal buyerThisPaid;

    private BigDecimal sellerAmount;

    private BigDecimal sellerThisPaid;

    private String paymentMethod;

    private String receiptCode;

    private String receiptPic;

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

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode == null ? null : paymentCode.trim();
    }

    public String getCommSubject() {
        return commSubject;
    }

    public void setCommSubject(String commSubject) {
        this.commSubject = commSubject == null ? null : commSubject.trim();
    }

    public BigDecimal getCommAmount() {
        return commAmount;
    }

    public void setCommAmount(BigDecimal commAmount) {
        this.commAmount = commAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getBuyerComm() {
        return buyerComm;
    }

    public void setBuyerComm(BigDecimal buyerComm) {
        this.buyerComm = buyerComm;
    }

    public BigDecimal getBuyerThisPaid() {
        return buyerThisPaid;
    }

    public void setBuyerThisPaid(BigDecimal buyerThisPaid) {
        this.buyerThisPaid = buyerThisPaid;
    }

    public BigDecimal getSellerAmount() {
        return sellerAmount;
    }

    public void setSellerAmount(BigDecimal sellerAmount) {
        this.sellerAmount = sellerAmount;
    }

    public BigDecimal getSellerThisPaid() {
        return sellerThisPaid;
    }

    public void setSellerThisPaid(BigDecimal sellerThisPaid) {
        this.sellerThisPaid = sellerThisPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod == null ? null : paymentMethod.trim();
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode == null ? null : receiptCode.trim();
    }

    public String getReceiptPic() {
        return receiptPic;
    }

    public void setReceiptPic(String receiptPic) {
        this.receiptPic = receiptPic == null ? null : receiptPic.trim();
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