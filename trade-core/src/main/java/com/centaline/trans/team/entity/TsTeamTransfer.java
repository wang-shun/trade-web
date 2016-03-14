package com.centaline.trans.team.entity;

import java.util.Date;

public class TsTeamTransfer {
    private Long pkid;

    private String caseCode;

    private String teamOrigin;

    private String teamNow;

    private Date createTime;

    private String isDelete;

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

    public String getTeamOrigin() {
        return teamOrigin;
    }

    public void setTeamOrigin(String teamOrigin) {
        this.teamOrigin = teamOrigin == null ? null : teamOrigin.trim();
    }

    public String getTeamNow() {
        return teamNow;
    }

    public void setTeamNow(String teamNow) {
        this.teamNow = teamNow == null ? null : teamNow.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }
}