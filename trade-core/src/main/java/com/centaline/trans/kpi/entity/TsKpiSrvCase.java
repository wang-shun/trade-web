package com.centaline.trans.kpi.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TsKpiSrvCase {
    private Long pkid;

    private Date belongMonth;

    private String caseCode;

    private String srvCode;

    private BigDecimal salerSatis;

    private BigDecimal buyerSatis;

    private BigDecimal satisfaction;

    private String salerCallback;

    private String buyerCallback;

    private String canCallback;

    private String salerComment;

    private String buyerComment;

    private String teamId;

    private String districtId;

    private String type;
	private BigDecimal srvPart;

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

    public BigDecimal getSalerSatis() {
        return salerSatis;
    }

    public void setSalerSatis(BigDecimal salerSatis) {
        this.salerSatis = salerSatis;
    }

    public BigDecimal getBuyerSatis() {
        return buyerSatis;
    }

    public void setBuyerSatis(BigDecimal buyerSatis) {
        this.buyerSatis = buyerSatis;
    }

    public BigDecimal getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(BigDecimal satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getSalerCallback() {
        return salerCallback;
    }

    public void setSalerCallback(String salerCallback) {
        this.salerCallback = salerCallback == null ? null : salerCallback.trim();
    }

    public String getBuyerCallback() {
        return buyerCallback;
    }

    public void setBuyerCallback(String buyerCallback) {
        this.buyerCallback = buyerCallback == null ? null : buyerCallback.trim();
    }

    public String getCanCallback() {
        return canCallback;
    }

    public void setCanCallback(String canCallback) {
        this.canCallback = canCallback == null ? null : canCallback.trim();
    }

    public String getSalerComment() {
        return salerComment;
    }

    public void setSalerComment(String salerComment) {
        this.salerComment = salerComment == null ? null : salerComment.trim();
    }

    public String getBuyerComment() {
        return buyerComment;
    }

    public void setBuyerComment(String buyerComment) {
        this.buyerComment = buyerComment == null ? null : buyerComment.trim();
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId == null ? null : teamId.trim();
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId == null ? null : districtId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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

	public BigDecimal getSrvPart() {
		return srvPart;
	}

	public void setSrvPart(BigDecimal srvPart) {
		this.srvPart = srvPart;
	}
}