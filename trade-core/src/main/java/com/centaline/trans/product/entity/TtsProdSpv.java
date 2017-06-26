package com.centaline.trans.product.entity;

import java.util.Date;

public class TtsProdSpv {
    private Long pkid;

    private Long prodId;

    private String officeAffair;

    private String moneyArrive;

    private String warn;

    private String chargeType;

    private String fixPrice;

    private String ratePrice;

    private String ratePriceLow;

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

    public String getOfficeAffair() {
        return officeAffair;
    }

    public void setOfficeAffair(String officeAffair) {
        this.officeAffair = officeAffair == null ? null : officeAffair.trim();
    }

    public String getMoneyArrive() {
        return moneyArrive;
    }

    public void setMoneyArrive(String moneyArrive) {
        this.moneyArrive = moneyArrive == null ? null : moneyArrive.trim();
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn == null ? null : warn.trim();
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType == null ? null : chargeType.trim();
    }

    public String getFixPrice() {
        return fixPrice;
    }

    public void setFixPrice(String fixPrice) {
        this.fixPrice = fixPrice == null ? null : fixPrice.trim();
    }

    public String getRatePrice() {
        return ratePrice;
    }

    public void setRatePrice(String ratePrice) {
        this.ratePrice = ratePrice == null ? null : ratePrice.trim();
    }

    public String getRatePriceLow() {
        return ratePriceLow;
    }

    public void setRatePriceLow(String ratePriceLow) {
        this.ratePriceLow = ratePriceLow == null ? null : ratePriceLow.trim();
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