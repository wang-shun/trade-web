package com.centaline.trans.cases.entity;

import java.util.Date;

public class ToCase {
    private Long pkid;

    private String caseCode;

    private String ctmCode;

    private Date createTime;

    private String status;

    private String caseProperty;

    private String leadingProcessId;

    private String time;
    
    private String orgId;
    
    private Date closeTime;
    
    private String startDate;
    private String endDate;
    
  

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

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

    public String getCtmCode() {
        return ctmCode;
    }

    public void setCtmCode(String ctmCode) {
        this.ctmCode = ctmCode == null ? null : ctmCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCaseProperty() {
        return caseProperty;
    }

    public void setCaseProperty(String caseProperty) {
        this.caseProperty = caseProperty == null ? null : caseProperty.trim();
    }


    public String getLeadingProcessId() {
        return leadingProcessId;
    }

    public void setLeadingProcessId(String leadingProcessId) {
        this.leadingProcessId = leadingProcessId == null ? null : leadingProcessId.trim();
    }

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
    
    
}