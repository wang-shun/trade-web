package com.centaline.trans.eloan.entity;

import java.util.Date;

public class ToRcMortgageCard {
    private Long pkid;

    private Long rcId;

    private String cardPerson;

    private Date cardTime;

    private String isModifyPhone;

    private String phoneNumber;

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

    public String getCardPerson() {
        return cardPerson;
    }

    public void setCardPerson(String cardPerson) {
        this.cardPerson = cardPerson == null ? null : cardPerson.trim();
    }

    public Date getCardTime() {
        return cardTime;
    }

    public void setCardTime(Date cardTime) {
        this.cardTime = cardTime;
    }

    public String getIsModifyPhone() {
        return isModifyPhone;
    }

    public void setIsModifyPhone(String isModifyPhone) {
        this.isModifyPhone = isModifyPhone == null ? null : isModifyPhone.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
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