package com.centaline.trans.spv.entity;

import java.util.Date;

public class ToSpvCust {
    private Long pkid;

    private String spvCode;

    private String tradePosition;

    private String name;

    private String gender;

    private String idCode;

    private Date idValiDate;

    private String idIssueInst;

    private String phone;

    private String homeAddr;

    private String hasDele;

    private String agentName;

    private String agentIdType;

    private String agentIdCode;

    private String isDeleted;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String idType;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getSpvCode() {
        return spvCode;
    }

    public void setSpvCode(String spvCode) {
        this.spvCode = spvCode == null ? null : spvCode.trim();
    }

    public String getTradePosition() {
        return tradePosition;
    }

    public void setTradePosition(String tradePosition) {
        this.tradePosition = tradePosition == null ? null : tradePosition.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode == null ? null : idCode.trim();
    }

    public Date getIdValiDate() {
        return idValiDate;
    }

    public void setIdValiDate(Date idValiDate) {
        this.idValiDate = idValiDate;
    }

    public String getIdIssueInst() {
        return idIssueInst;
    }

    public void setIdIssueInst(String idIssueInst) {
        this.idIssueInst = idIssueInst == null ? null : idIssueInst.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getHomeAddr() {
        return homeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr == null ? null : homeAddr.trim();
    }

    public String getHasDele() {
        return hasDele;
    }

    public void setHasDele(String hasDele) {
        this.hasDele = hasDele == null ? null : hasDele.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getAgentIdType() {
        return agentIdType;
    }

    public void setAgentIdType(String agentIdType) {
        this.agentIdType = agentIdType == null ? null : agentIdType.trim();
    }

    public String getAgentIdCode() {
        return agentIdCode;
    }

    public void setAgentIdCode(String agentIdCode) {
        this.agentIdCode = agentIdCode == null ? null : agentIdCode.trim();
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }
}