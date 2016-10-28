package com.centaline.trans.spv.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToSpvProperty {
    private Long pkid;

    private String spvCode;

    private String houseId;

    private String houseAddr;

    private BigDecimal signAmount;

    private String signNo;

    private String prNo;

    private String prAddr;

    private String prOwnerName;

    private BigDecimal prSize;

    private String isMortClear;

    private String mortgageeName;

    private String mortgageeBank;

    private String mortgageeAccount;

    private BigDecimal leftAmount;

    private String isDeleted;

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

    public String getSpvCode() {
        return spvCode;
    }

    public void setSpvCode(String spvCode) {
        this.spvCode = spvCode == null ? null : spvCode.trim();
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId == null ? null : houseId.trim();
    }

    public String getHouseAddr() {
        return houseAddr;
    }

    public void setHouseAddr(String houseAddr) {
        this.houseAddr = houseAddr == null ? null : houseAddr.trim();
    }

    public BigDecimal getSignAmount() {
        return signAmount;
    }

    public void setSignAmount(BigDecimal signAmount) {
        this.signAmount = signAmount;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo == null ? null : signNo.trim();
    }

    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo == null ? null : prNo.trim();
    }

    public String getPrAddr() {
        return prAddr;
    }

    public void setPrAddr(String prAddr) {
        this.prAddr = prAddr == null ? null : prAddr.trim();
    }

    public String getPrOwnerName() {
        return prOwnerName;
    }

    public void setPrOwnerName(String prOwnerName) {
        this.prOwnerName = prOwnerName == null ? null : prOwnerName.trim();
    }

    public BigDecimal getPrSize() {
        return prSize;
    }

    public void setPrSize(BigDecimal prSize) {
    	
        this.prSize = prSize == null ? new BigDecimal(0) :prSize;
    }

    public String getIsMortClear() {
        return isMortClear;
    }

    public void setIsMortClear(String isMortClear) {
        this.isMortClear = isMortClear == null ? null : isMortClear.trim();
    }

    public String getMortgageeName() {
        return mortgageeName;
    }

    public void setMortgageeName(String mortgageeName) {
        this.mortgageeName = mortgageeName == null ? null : mortgageeName.trim();
    }

    public String getMortgageeBank() {
        return mortgageeBank;
    }

    public void setMortgageeBank(String mortgageeBank) {
        this.mortgageeBank = mortgageeBank == null ? null : mortgageeBank.trim();
    }

    public String getMortgageeAccount() {
        return mortgageeAccount;
    }

    public void setMortgageeAccount(String mortgageeAccount) {
        this.mortgageeAccount = mortgageeAccount == null ? null : mortgageeAccount.trim();
    }

    public BigDecimal getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(BigDecimal leftAmount) {
        this.leftAmount = leftAmount;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
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