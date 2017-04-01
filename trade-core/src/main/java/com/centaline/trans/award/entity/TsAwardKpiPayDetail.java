package com.centaline.trans.award.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TsAwardKpiPayDetail {
    private Long pkid;

    private Date belongMonth;

    private Long awardKpiPayId;

    private String type;

    private String caseCode;

    private String srvCode;

    private String participant;

    private Long awardBaseId;

    private Long kpiRateId;

    private BigDecimal awardKpiMoney;

    private String remark;

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

    public Date getBelongMonth() {
        return belongMonth;
    }

    public void setBelongMonth(Date belongMonth) {
        this.belongMonth = belongMonth;
    }

    public Long getAwardKpiPayId() {
        return awardKpiPayId;
    }

    public void setAwardKpiPayId(Long awardKpiPayId) {
        this.awardKpiPayId = awardKpiPayId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getSrvCode() {
        return srvCode;
    }

    public void setSrvCode(String srvCode) {
        this.srvCode = srvCode == null ? null : srvCode.trim();
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant == null ? null : participant.trim();
    }

    public Long getAwardBaseId() {
        return awardBaseId;
    }

    public void setAwardBaseId(Long awardBaseId) {
        this.awardBaseId = awardBaseId;
    }

    public Long getKpiRateId() {
        return kpiRateId;
    }

    public void setKpiRateId(Long kpiRateId) {
        this.kpiRateId = kpiRateId;
    }

    public BigDecimal getAwardKpiMoney() {
        return awardKpiMoney;
    }

    public void setAwardKpiMoney(BigDecimal awardKpiMoney) {
        this.awardKpiMoney = awardKpiMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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