package com.centaline.trans.income.entity;

import java.math.BigDecimal;

public class TsOrTemplate {
    private Long pkid;

    private String roleCode;

    private String servCat;

    private BigDecimal amount;

    private Double percentage;

    private Integer teamNoStart;

    private Integer teamNoEnd;

    private String isTeamRelated;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getServCat() {
        return servCat;
    }

    public void setServCat(String servCat) {
        this.servCat = servCat == null ? null : servCat.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
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

    public String getIsTeamRelated() {
        return isTeamRelated;
    }

    public void setIsTeamRelated(String isTeamRelated) {
        this.isTeamRelated = isTeamRelated == null ? null : isTeamRelated.trim();
    }
}