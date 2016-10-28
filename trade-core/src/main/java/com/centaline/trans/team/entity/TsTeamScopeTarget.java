package com.centaline.trans.team.entity;

public class TsTeamScopeTarget {
    private String yuTeamCode;

    private String yuTeamName;

    private String isResponseTeam;

    private String grpCode;

    private String grpName;

    public String getYuTeamCode() {
        return yuTeamCode;
    }

    public void setYuTeamCode(String yuTeamCode) {
        this.yuTeamCode = yuTeamCode == null ? null : yuTeamCode.trim();
    }

    public String getYuTeamName() {
        return yuTeamName;
    }

    public void setYuTeamName(String yuTeamName) {
        this.yuTeamName = yuTeamName == null ? null : yuTeamName.trim();
    }

    public String getIsResponseTeam() {
        return isResponseTeam;
    }

    public void setIsResponseTeam(String isResponseTeam) {
        this.isResponseTeam = isResponseTeam == null ? null : isResponseTeam.trim();
    }

    public String getGrpCode() {
        return grpCode;
    }

    public void setGrpCode(String grpCode) {
        this.grpCode = grpCode == null ? null : grpCode.trim();
    }

    public String getGrpName() {
        return grpName;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName == null ? null : grpName.trim();
    }
}