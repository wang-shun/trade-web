package com.centaline.trans.satisfaction.entity;

import java.util.Date;

public class ToSatisfaction {
    private Long pkid;

    private String caseCode;

    private String castsatCode;

    private Integer salerPhoneOk;

    private String salerPhoneRes;

    private Integer salerSignSat;

    private String salerSignCom;

    private Integer salerLoancloseSat;

    private String salerLoancloseCom;

    private Integer salerGuohuSat;

    private String salerGuohuCom;

    private Integer buyerPhoneOk;

    private String buyerPhoneRes;

    private Integer buyerSignSat;

    private String buyerSignCom;

    private Integer buyerComloanSat;

    private String buyerComloanCom;

    private Integer buyerPsfloanSat;

    private String buyerPsfloanCom;

    private Integer buyerGuohuSat;

    private String buyerGuohuCom;

    private Integer agentPhoneOk;

    private String agentPhoneRes;

    private Integer agentSignSat;

    private String agentSignCom;

    private Integer agentComloanSat;

    private String agentComloanCom;

    private Integer agentPsfloanSat;

    private String agentPsfloanCom;

    private Integer agentGuohuSat;

    private String agentGuohuCom;

    private String status;

    private String callerId;
    
    private Date signTime;
    
    private Date guohuTime;
    
    private Date closeTime;

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

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getCastsatCode() {
        return castsatCode;
    }

    public void setCastsatCode(String castsatCode) {
        this.castsatCode = castsatCode == null ? null : castsatCode.trim();
    }

    public Integer getSalerPhoneOk() {
        return salerPhoneOk;
    }

    public void setSalerPhoneOk(Integer salerPhoneOk) {
        this.salerPhoneOk = salerPhoneOk;
    }

    public String getSalerPhoneRes() {
        return salerPhoneRes;
    }

    public void setSalerPhoneRes(String salerPhoneRes) {
        this.salerPhoneRes = salerPhoneRes == null ? null : salerPhoneRes.trim();
    }

    public Integer getSalerSignSat() {
        return salerSignSat;
    }

    public void setSalerSignSat(Integer salerSignSat) {
        this.salerSignSat = salerSignSat;
    }

    public String getSalerSignCom() {
        return salerSignCom;
    }

    public void setSalerSignCom(String salerSignCom) {
        this.salerSignCom = salerSignCom == null ? null : salerSignCom.trim();
    }

    public Integer getSalerLoancloseSat() {
        return salerLoancloseSat;
    }

    public void setSalerLoancloseSat(Integer salerLoancloseSat) {
        this.salerLoancloseSat = salerLoancloseSat;
    }

    public String getSalerLoancloseCom() {
        return salerLoancloseCom;
    }

    public void setSalerLoancloseCom(String salerLoancloseCom) {
        this.salerLoancloseCom = salerLoancloseCom == null ? null : salerLoancloseCom.trim();
    }

    public Integer getSalerGuohuSat() {
        return salerGuohuSat;
    }

    public void setSalerGuohuSat(Integer salerGuohuSat) {
        this.salerGuohuSat = salerGuohuSat;
    }

    public String getSalerGuohuCom() {
        return salerGuohuCom;
    }

    public void setSalerGuohuCom(String salerGuohuCom) {
        this.salerGuohuCom = salerGuohuCom == null ? null : salerGuohuCom.trim();
    }

    public Integer getBuyerPhoneOk() {
        return buyerPhoneOk;
    }

    public void setBuyerPhoneOk(Integer buyerPhoneOk) {
        this.buyerPhoneOk = buyerPhoneOk;
    }

    public String getBuyerPhoneRes() {
        return buyerPhoneRes;
    }

    public void setBuyerPhoneRes(String buyerPhoneRes) {
        this.buyerPhoneRes = buyerPhoneRes == null ? null : buyerPhoneRes.trim();
    }

    public Integer getBuyerSignSat() {
        return buyerSignSat;
    }

    public void setBuyerSignSat(Integer buyerSignSat) {
        this.buyerSignSat = buyerSignSat;
    }

    public String getBuyerSignCom() {
        return buyerSignCom;
    }

    public void setBuyerSignCom(String buyerSignCom) {
        this.buyerSignCom = buyerSignCom == null ? null : buyerSignCom.trim();
    }

    public Integer getBuyerComloanSat() {
        return buyerComloanSat;
    }

    public void setBuyerComloanSat(Integer buyerComloanSat) {
        this.buyerComloanSat = buyerComloanSat;
    }

    public String getBuyerComloanCom() {
        return buyerComloanCom;
    }

    public void setBuyerComloanCom(String buyerComloanCom) {
        this.buyerComloanCom = buyerComloanCom == null ? null : buyerComloanCom.trim();
    }

    public Integer getBuyerPsfloanSat() {
        return buyerPsfloanSat;
    }

    public void setBuyerPsfloanSat(Integer buyerPsfloanSat) {
        this.buyerPsfloanSat = buyerPsfloanSat;
    }

    public String getBuyerPsfloanCom() {
        return buyerPsfloanCom;
    }

    public void setBuyerPsfloanCom(String buyerPsfloanCom) {
        this.buyerPsfloanCom = buyerPsfloanCom == null ? null : buyerPsfloanCom.trim();
    }

    public Integer getBuyerGuohuSat() {
        return buyerGuohuSat;
    }

    public void setBuyerGuohuSat(Integer buyerGuohuSat) {
        this.buyerGuohuSat = buyerGuohuSat;
    }

    public String getBuyerGuohuCom() {
        return buyerGuohuCom;
    }

    public void setBuyerGuohuCom(String buyerGuohuCom) {
        this.buyerGuohuCom = buyerGuohuCom == null ? null : buyerGuohuCom.trim();
    }

    public Integer getAgentPhoneOk() {
        return agentPhoneOk;
    }

    public void setAgentPhoneOk(Integer agentPhoneOk) {
        this.agentPhoneOk = agentPhoneOk;
    }

    public String getAgentPhoneRes() {
        return agentPhoneRes;
    }

    public void setAgentPhoneRes(String agentPhoneRes) {
        this.agentPhoneRes = agentPhoneRes == null ? null : agentPhoneRes.trim();
    }

    public Integer getAgentSignSat() {
        return agentSignSat;
    }

    public void setAgentSignSat(Integer agentSignSat) {
        this.agentSignSat = agentSignSat;
    }

    public String getAgentSignCom() {
        return agentSignCom;
    }

    public void setAgentSignCom(String agentSignCom) {
        this.agentSignCom = agentSignCom == null ? null : agentSignCom.trim();
    }

    public Integer getAgentComloanSat() {
        return agentComloanSat;
    }

    public void setAgentComloanSat(Integer agentComloanSat) {
        this.agentComloanSat = agentComloanSat;
    }

    public String getAgentComloanCom() {
        return agentComloanCom;
    }

    public void setAgentComloanCom(String agentComloanCom) {
        this.agentComloanCom = agentComloanCom == null ? null : agentComloanCom.trim();
    }

    public Integer getAgentPsfloanSat() {
        return agentPsfloanSat;
    }

    public void setAgentPsfloanSat(Integer agentPsfloanSat) {
        this.agentPsfloanSat = agentPsfloanSat;
    }

    public String getAgentPsfloanCom() {
        return agentPsfloanCom;
    }

    public void setAgentPsfloanCom(String agentPsfloanCom) {
        this.agentPsfloanCom = agentPsfloanCom == null ? null : agentPsfloanCom.trim();
    }

    public Integer getAgentGuohuSat() {
        return agentGuohuSat;
    }

    public void setAgentGuohuSat(Integer agentGuohuSat) {
        this.agentGuohuSat = agentGuohuSat;
    }

    public String getAgentGuohuCom() {
        return agentGuohuCom;
    }

    public void setAgentGuohuCom(String agentGuohuCom) {
        this.agentGuohuCom = agentGuohuCom == null ? null : agentGuohuCom.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId == null ? null : callerId.trim();
    }

    public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Date getGuohuTime() {
		return guohuTime;
	}

	public void setGuohuTime(Date guohuTime) {
		this.guohuTime = guohuTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
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