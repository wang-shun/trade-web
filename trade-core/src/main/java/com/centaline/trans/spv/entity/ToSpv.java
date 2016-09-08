package com.centaline.trans.spv.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToSpv {
    private Long pkid;

    private String caseCode;

    private String spvCode;

    private String spvConCode;

    private String spvInsti;

    private String spvAccount;

    private String prdCode;

    private String spvType;

    private BigDecimal amount;

    private BigDecimal amountOwn;

    private BigDecimal amountMort;

    private BigDecimal amountMortCom;

    private BigDecimal amountMortPsf;

    private Date signTime;

    private String status;

    private String remark;

    private String isDeleted;
    
    private String buyerPayment;
    
    private Date applyTime;
    
    private Date closeTime;
    
    private String applyUser;

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

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getSpvCode() {
        return spvCode;
    }

    public void setSpvCode(String spvCode) {
        this.spvCode = spvCode == null ? null : spvCode.trim();
    }

    public String getSpvConCode() {
        return spvConCode;
    }

    public void setSpvConCode(String spvConCode) {
        this.spvConCode = spvConCode == null ? null : spvConCode.trim();
    }

    public String getSpvInsti() {
        return spvInsti;
    }

    public void setSpvInsti(String spvInsti) {
        this.spvInsti = spvInsti == null ? null : spvInsti.trim();
    }

    public String getSpvAccount() {
        return spvAccount;
    }

    public void setSpvAccount(String spvAccount) {
        this.spvAccount = spvAccount == null ? null : spvAccount.trim();
    }

    public String getPrdCode() {
        return prdCode;
    }

    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode == null ? null : prdCode.trim();
    }

    public String getSpvType() {
        return spvType;
    }

    public void setSpvType(String spvType) {
        this.spvType = spvType == null ? null : spvType.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountOwn() {
        return amountOwn;
    }

    public void setAmountOwn(BigDecimal amountOwn) {
        this.amountOwn = amountOwn;
    }

    public BigDecimal getAmountMort() {
        return amountMort;
    }

    public void setAmountMort(BigDecimal amountMort) {
        this.amountMort = amountMort;
    }

    public BigDecimal getAmountMortCom() {
        return amountMortCom;
    }

    public void setAmountMortCom(BigDecimal amountMortCom) {
        this.amountMortCom = amountMortCom;
    }

    public BigDecimal getAmountMortPsf() {
        return amountMortPsf;
    }

    public void setAmountMortPsf(BigDecimal amountMortPsf) {
        this.amountMortPsf = amountMortPsf;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }

    public String getBuyerPayment() {
		return buyerPayment;
	}

	public void setBuyerPayment(String buyerPayment) {
		this.buyerPayment = buyerPayment;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
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