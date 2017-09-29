package com.centaline.trans.ransom.entity;

import java.util.Date;

public class ToRansomCaseVo {
    private Long pkid;

    private String ransomCode;

    private String caseCode;

    private String ransomStatus;

    private String ransomProperty;

    private String comOrgCode;

    private double borroMoney;

    private String borrowerName;

    private String borrowerTel;

    private String isstop;

    private String stopType;

    private String stopReason;

    private Date acceptTime;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String finOrgId;

    private String remark;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getRansomCode() {
        return ransomCode;
    }

    public void setRansomCode(String ransomCode) {
        this.ransomCode = ransomCode == null ? null : ransomCode.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getRansomStatus() {
        return ransomStatus;
    }

    public void setRansomStatus(String ransomStatus) {
        this.ransomStatus = ransomStatus == null ? null : ransomStatus.trim();
    }

    public String getRansomProperty() {
        return ransomProperty;
    }

    public void setRansomProperty(String ransomProperty) {
        this.ransomProperty = ransomProperty == null ? null : ransomProperty.trim();
    }

    public String getComOrgCode() {
        return comOrgCode;
    }

    public void setComOrgCode(String comOrgCode) {
        this.comOrgCode = comOrgCode == null ? null : comOrgCode.trim();
    }

    public double getBorroMoney() {
        return borroMoney;
    }

    public void setBorroMoney(double borroMoney) {
        this.borroMoney = borroMoney;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName == null ? null : borrowerName.trim();
    }

    public String getBorrowerTel() {
        return borrowerTel;
    }

    public void setBorrowerTel(String borrowerTel) {
        this.borrowerTel = borrowerTel == null ? null : borrowerTel.trim();
    }

    public String getIsstop() {
        return isstop;
    }

    public void setIsstop(String isstop) {
        this.isstop = isstop == null ? null : isstop.trim();
    }

    public String getStopType() {
        return stopType;
    }

    public void setStopType(String stopType) {
        this.stopType = stopType == null ? null : stopType.trim();
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason == null ? null : stopReason.trim();
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getFinOrgId() {
        return finOrgId;
    }

    public void setFinOrgId(String finOrgId) {
        this.finOrgId = finOrgId == null ? null : finOrgId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}