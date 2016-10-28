package com.centaline.trans.eloan.entity;

import java.util.Date;

public class ToRcMortgage {
    private Long pkid;

    private Long rcId;

    private String mortgageContractCode;

    private Date mortgageTime;

    private String mortgagePropertyAddress;

    private String propertyName;

    private String propertyCode;

    private String otherCode;

    private String createBy;

    private Date createTime;

    private Date updateTime;

    private String updateBy;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public Long getRcId() {
        return rcId;
    }

    public void setRcId(Long rcId) {
        this.rcId = rcId;
    }

    public String getMortgageContractCode() {
        return mortgageContractCode;
    }

    public void setMortgageContractCode(String mortgageContractCode) {
        this.mortgageContractCode = mortgageContractCode == null ? null : mortgageContractCode.trim();
    }

    public Date getMortgageTime() {
        return mortgageTime;
    }

    public void setMortgageTime(Date mortgageTime) {
        this.mortgageTime = mortgageTime;
    }

    public String getMortgagePropertyAddress() {
        return mortgagePropertyAddress;
    }

    public void setMortgagePropertyAddress(String mortgagePropertyAddress) {
        this.mortgagePropertyAddress = mortgagePropertyAddress == null ? null : mortgagePropertyAddress.trim();
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName == null ? null : propertyName.trim();
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode == null ? null : propertyCode.trim();
    }

    public String getOtherCode() {
        return otherCode;
    }

    public void setOtherCode(String otherCode) {
        this.otherCode = otherCode == null ? null : otherCode.trim();
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