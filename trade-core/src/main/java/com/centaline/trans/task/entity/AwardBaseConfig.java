package com.centaline.trans.task.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AwardBaseConfig {
    private Long pkId;

    private String jobCode;

    private String srvItemCode;

    private String srvItemName;

    private String teamProperty;

    private Integer teamNoStart;

    private Integer teamNoEnd;

    private Double srvFee;

    private Date createTime;

    private String isAvailable;

    public Long getPkId() {
        return pkId;
    }

    public void setPkId(Long pkId) {
        this.pkId = pkId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode == null ? null : jobCode.trim();
    }

    public String getSrvItemCode() {
        return srvItemCode;
    }

    public void setSrvItemCode(String srvItemCode) {
        this.srvItemCode = srvItemCode == null ? null : srvItemCode.trim();
    }

    public String getSrvItemName() {
        return srvItemName;
    }

    public void setSrvItemName(String srvItemName) {
        this.srvItemName = srvItemName == null ? null : srvItemName.trim();
    }

    public String getTeamProperty() {
        return teamProperty;
    }

    public void setTeamProperty(String teamProperty) {
        this.teamProperty = teamProperty == null ? null : teamProperty.trim();
    }

    public Integer getTeamNoStart() {
        return teamNoStart;
    }

    public void setTeamNoStart(Integer teamNoStart) {
        this.teamNoStart = teamNoStart;
    }

    public Integer getTeamNoEnd() {
        return teamNoEnd;
    }

    public void setTeamNoEnd(Integer teamNoEnd) {
        this.teamNoEnd = teamNoEnd;
    }

    public Double getSrvFee() {
        return srvFee;
    }

    public void setSrvFee(Double srvFee) {
        this.srvFee = srvFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable == null ? null : isAvailable.trim();
    }
}
