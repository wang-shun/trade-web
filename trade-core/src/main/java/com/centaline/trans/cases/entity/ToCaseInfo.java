package com.centaline.trans.cases.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ToCaseInfo {
    private Long pkid;

    private String ctmCode;

    private String agentCode;

    private String caseCode;

    private String requireProcessorId;

    private String isResponsed;
    
    private String orgId;

    private Date importTime;
    
    private Date dispatchTime;
    
    private String grpCode;
    
    private String grpName;
    
    private String agentUserName;
    
    private String agentPhone;
    
    private String arCode;
    
    private String arName;
    
    private String targetCode;
    
    private String qjdsName;
    
    private String qyjlName;
    
    private String qyzjName;
    
    private String swzCode;
    
    private String swzName;
    
    private String wzCode;
    
    private String wzName;
    
    private String baCode;
    
    private String baName;
    
    private String updateby;

	private Date updateTime;
	/** refer_consultant_id 意向顾问 refer_consultant_realname **/
	private String referConsultantId;
	/** refer_consultant_id 意向顾问 refer_consultant_realname **/
	private String referConsultantRealname;
	/**
	 *  外单案件的合作来源
	 */
	private String sourceOfCooperation;
	/**
	 *  外单案件的推荐人
	 */
	private String recommendUsername;
	/**
	 *  外单案件的推荐人电话
	 */
	private String recommendPhone;
	
	
    public String getRecommendUsername() {
		return recommendUsername;
	}

	public void setRecommendUsername(String recommendUsername) {
		this.recommendUsername = recommendUsername;
	}

	public String getRecommendPhone() {
		return recommendPhone;
	}

	public void setRecommendPhone(String recommendPhone) {
		this.recommendPhone = recommendPhone;
	}

	public String getSourceOfCooperation() {
		return sourceOfCooperation;
	}

	public void setSourceOfCooperation(String sourceOfCooperation) {
		this.sourceOfCooperation = sourceOfCooperation;
	}

	public String getReferConsultantId() {
		return referConsultantId;
	}

	public void setReferConsultantId(String referConsultantId) {
		this.referConsultantId = referConsultantId;
	}

	public String getReferConsultantRealname() {
		return referConsultantRealname;
	}

	public void setReferConsultantRealname(String referConsultantRealname) {
		this.referConsultantRealname = referConsultantRealname;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}
    
    public String getBaCode() {
		return baCode;
	}

	public void setBaCode(String baCode) {
		this.baCode = baCode;
	}

	public String getBaName() {
		return baName;
	}

	public void setBaName(String baName) {
		this.baName = baName;
	}

	public String getSwzCode() {
		return swzCode;
	}

	public void setSwzCode(String swzCode) {
		this.swzCode = swzCode;
	}

	public String getSwzName() {
		return swzName;
	}

	public void setSwzName(String swzName) {
		this.swzName = swzName;
	}

	public String getWzCode() {
		return wzCode;
	}

	public void setWzCode(String wzCode) {
		this.wzCode = wzCode;
	}

	public String getWzName() {
		return wzName;
	}

	public void setWzName(String wzName) {
		this.wzName = wzName;
	}

	public String getQjdsName() {
		return qjdsName;
	}

	public void setQjdsName(String qjdsName) {
		this.qjdsName = qjdsName;
	}

	public String getQyjlName() {
		return qyjlName;
	}

	public void setQyjlName(String qyjlName) {
		this.qyjlName = qyjlName;
	}

	public String getQyzjName() {
		return qyzjName;
	}

	public void setQyzjName(String qyzjName) {
		this.qyzjName = qyzjName;
	}

	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", locale = "zh", timezone = "GMT+8")
    private Date resDate;


	private String agentName;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getCtmCode() {
        return ctmCode;
    }

    public void setCtmCode(String ctmCode) {
        this.ctmCode = ctmCode == null ? null : ctmCode.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getRequireProcessorId() {
        return requireProcessorId;
    }

    public void setRequireProcessorId(String requireProcessorId) {
        this.requireProcessorId = requireProcessorId == null ? null : requireProcessorId.trim();
    }

    public String getIsResponsed() {
        return isResponsed;
    }

    public void setIsResponsed(String isResponsed) {
        this.isResponsed = isResponsed == null ? null : isResponsed.trim();
    }
    public Date getResDate() {
		return resDate;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setResDate(Date resDate) {
		this.resDate = resDate;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public Date getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(Date dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public String getGrpCode() {
		return grpCode;
	}

	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public String getAgentUserName() {
		return agentUserName;
	}

	public void setAgentUserName(String agentUserName) {
		this.agentUserName = agentUserName;
	}

	public String getAgentPhone() {
		return agentPhone;
	}

	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}

	public String getArCode() {
		return arCode;
	}

	public void setArCode(String arCode) {
		this.arCode = arCode;
	}

	public String getArName() {
		return arName;
	}

	public void setArName(String arName) {
		this.arName = arName;
	}
	
	
}