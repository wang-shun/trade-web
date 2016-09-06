package com.centaline.trans.eloan.entity;

import java.util.Date;

public class ToRcMortgage {
    private Long pkid;

    private Long rcId;

    private String mortgageContractCode;

    private Date mortgageTime;

    private String mortgageAttachment;

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

    public String getMortgageAttachment() {
        return mortgageAttachment;
    }

    public void setMortgageAttachment(String mortgageAttachment) {
        this.mortgageAttachment = mortgageAttachment == null ? null : mortgageAttachment.trim();
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