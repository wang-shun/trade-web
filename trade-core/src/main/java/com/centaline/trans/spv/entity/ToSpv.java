package com.centaline.trans.spv.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ToSpv {
    private Long pkid;

    private String caseCode;

    private String spvCode;

    private String spvType;

    private BigDecimal amount;

    private String spvInsti;

    private Date signTime;

    private String remark;

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

    public String getSpvInsti() {
        return spvInsti;
    }

    public void setSpvInsti(String spvInsti) {
        this.spvInsti = spvInsti == null ? null : spvInsti.trim();
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}