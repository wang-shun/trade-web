package com.centaline.trans.cases.entity;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 案件参与人信息
 * @author yinchao
 *
 */
public class ToCaseParticipant {
    private Long pkid;

    private String ccaiCode;

    private String caseCode;

    private String position;

    private String userName;

    private String realName;

    private String mobile;

    private String grpCode;

    private String grpName;

    private String grpMgrUsername;

    private String grpMgrRealname;

    private String grpMgrMobile;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getCcaiCode() {
        return ccaiCode;
    }

    public void setCcaiCode(String ccaiCode) {
        this.ccaiCode = ccaiCode == null ? null : ccaiCode.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }
    @NotBlank(message="")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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

    public String getGrpMgrUsername() {
        return grpMgrUsername;
    }

    public void setGrpMgrUsername(String grpMgrUsername) {
        this.grpMgrUsername = grpMgrUsername == null ? null : grpMgrUsername.trim();
    }

    public String getGrpMgrRealname() {
        return grpMgrRealname;
    }

    public void setGrpMgrRealname(String grpMgrRealname) {
        this.grpMgrRealname = grpMgrRealname == null ? null : grpMgrRealname.trim();
    }

    public String getGrpMgrMobile() {
        return grpMgrMobile;
    }

    public void setGrpMgrMobile(String grpMgrMobile) {
        this.grpMgrMobile = grpMgrMobile == null ? null : grpMgrMobile.trim();
    }
}