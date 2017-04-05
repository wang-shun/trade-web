package com.centaline.trans.mortgage.entity;

import java.util.Date;

public class ToEvaReport {
    private Long pkid;

    private String caseCode;

    private String reportType;

    private Date reportAriseTime;

    private Date reportResponseTime;

    private String status;

    private String finOrgCode;

    private String isEvalFeeGet;

    private String isFinalReport;

    private String evaCode;

    private String serialNumber;

    private String feedback;
    
    private String isMainLoanBank;
    
    private String evaProcessId;
    
    private Double expectRate;
    
    private String comment;
    
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

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType == null ? null : reportType.trim();
    }

    public Date getReportAriseTime() {
        return reportAriseTime;
    }

    public void setReportAriseTime(Date reportAriseTime) {
        this.reportAriseTime = reportAriseTime;
    }

    public Date getReportResponseTime() {
        return reportResponseTime;
    }

    public void setReportResponseTime(Date reportResponseTime) {
        this.reportResponseTime = reportResponseTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFinOrgCode() {
        return finOrgCode;
    }

    public void setFinOrgCode(String finOrgCode) {
        this.finOrgCode = finOrgCode == null ? null : finOrgCode.trim();
    }

    public String getIsEvalFeeGet() {
        return isEvalFeeGet;
    }

    public void setIsEvalFeeGet(String isEvalFeeGet) {
        this.isEvalFeeGet = isEvalFeeGet == null ? null : isEvalFeeGet.trim();
    }

    public String getIsFinalReport() {
        return isFinalReport;
    }

    public void setIsFinalReport(String isFinalReport) {
        this.isFinalReport = isFinalReport == null ? null : isFinalReport.trim();
    }

    public String getEvaCode() {
        return evaCode;
    }

    public void setEvaCode(String evaCode) {
        this.evaCode = evaCode == null ? null : evaCode.trim();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback == null ? null : feedback.trim();
    }

	public String getIsMainLoanBank() {
		return isMainLoanBank;
	}

	public void setIsMainLoanBank(String isMainLoanBank) {
		this.isMainLoanBank = isMainLoanBank;
	}

	public String getEvaProcessId() {
		return evaProcessId;
	}

	public void setEvaProcessId(String evaProcessId) {
		this.evaProcessId = evaProcessId;
	}

	public Double getExpectRate() {
		return expectRate;
	}

	public void setExpectRate(Double expectRate) {
		this.expectRate = expectRate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
    
}