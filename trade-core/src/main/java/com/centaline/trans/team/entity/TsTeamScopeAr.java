package com.centaline.trans.team.entity;

import java.util.Date;

public class TsTeamScopeAr {
    private Long pkid;

    private String arCode;

    private String yuTeamCode;

    private String arName;

    private String yuTeamName;

    private Date updateTime;

    private String isResponseTeam;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getArCode() {
        return arCode;
    }

    public void setArCode(String arCode) {
        this.arCode = arCode == null ? null : arCode.trim();
    }

    public String getYuTeamCode() {
        return yuTeamCode;
    }

    public void setYuTeamCode(String yuTeamCode) {
        this.yuTeamCode = yuTeamCode == null ? null : yuTeamCode.trim();
    }

    public String getArName() {
        return arName;
    }

    public void setArName(String arName) {
        this.arName = arName == null ? null : arName.trim();
    }

    public String getYuTeamName() {
        return yuTeamName;
    }

    public void setYuTeamName(String yuTeamName) {
        this.yuTeamName = yuTeamName == null ? null : yuTeamName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsResponseTeam() {
        return isResponseTeam;
    }

    public void setIsResponseTeam(String isResponseTeam) {
        this.isResponseTeam = isResponseTeam == null ? null : isResponseTeam.trim();
    }
}