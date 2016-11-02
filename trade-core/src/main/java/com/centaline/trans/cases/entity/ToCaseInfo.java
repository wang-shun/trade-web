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