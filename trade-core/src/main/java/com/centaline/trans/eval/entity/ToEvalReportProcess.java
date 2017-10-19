package com.centaline.trans.eval.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ToEvalReportProcess {
    private Long pkid;

    private String caseCode;

    private String evaCode;

    private String reportType;

    private String status;

    private String proposeer;

    private String transactor;

    private BigDecimal ornginPrice;

    private String finOrgId;

    private String evaComContact;

    private String contactWay;

    private Integer houseAgeApply;

    private BigDecimal inquiryResult;

    private Integer reportNum;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date applyDate;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date forwardDate;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date toIssueDate;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date issueDate;
    
    private BigDecimal evaPrice;

    private Date reportGetDate;

    private Integer houseAgeIssue;

    private Integer reportNumIssue;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date reportRevDate;

    private String receiver;

    private Integer receiveNum;
    
    private Date sysCreateTime;
    
    private Date sysFinshTime;
    
    private String evalProperty;
    

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

    public String getEvaCode() {
        return evaCode;
    }

    public void setEvaCode(String evaCode) {
        this.evaCode = evaCode == null ? null : evaCode.trim();
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType == null ? null : reportType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getProposeer() {
        return proposeer;
    }

    public void setProposeer(String proposeer) {
        this.proposeer = proposeer == null ? null : proposeer.trim();
    }

    public String getTransactor() {
        return transactor;
    }

    public void setTransactor(String transactor) {
        this.transactor = transactor == null ? null : transactor.trim();
    }

    public BigDecimal getOrnginPrice() {
        return ornginPrice;
    }

    public void setOrnginPrice(BigDecimal ornginPrice) {
        this.ornginPrice = ornginPrice;
    }

    public String getFinOrgId() {
        return finOrgId;
    }

    public void setFinOrgId(String finOrgId) {
        this.finOrgId = finOrgId == null ? null : finOrgId.trim();
    }

    public String getEvaComContact() {
        return evaComContact;
    }

    public void setEvaComContact(String evaComContact) {
        this.evaComContact = evaComContact == null ? null : evaComContact.trim();
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay == null ? null : contactWay.trim();
    }

    public Integer getHouseAgeApply() {
        return houseAgeApply;
    }

    public void setHouseAgeApply(Integer houseAgeApply) {
        this.houseAgeApply = houseAgeApply;
    }

    public BigDecimal getInquiryResult() {
        return inquiryResult;
    }

    public void setInquiryResult(BigDecimal inquiryResult) {
        this.inquiryResult = inquiryResult;
    }

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getForwardDate() {
        return forwardDate;
    }

    public void setForwardDate(Date forwardDate) {
        this.forwardDate = forwardDate;
    }

    public Date getToIssueDate() {
        return toIssueDate;
    }

    public void setToIssueDate(Date toIssueDate) {
        this.toIssueDate = toIssueDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getEvaPrice() {
        return evaPrice;
    }

    public void setEvaPrice(BigDecimal evaPrice) {
        this.evaPrice = evaPrice;
    }

    public Date getReportGetDate() {
        return reportGetDate;
    }

    public void setReportGetDate(Date reportGetDate) {
        this.reportGetDate = reportGetDate;
    }

    public Integer getHouseAgeIssue() {
        return houseAgeIssue;
    }

    public void setHouseAgeIssue(Integer houseAgeIssue) {
        this.houseAgeIssue = houseAgeIssue;
    }

    public Integer getReportNumIssue() {
        return reportNumIssue;
    }

    public void setReportNumIssue(Integer reportNumIssue) {
        this.reportNumIssue = reportNumIssue;
    }

    public Date getReportRevDate() {
        return reportRevDate;
    }

    public void setReportRevDate(Date reportRevDate) {
        this.reportRevDate = reportRevDate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

	public String getEvalProperty() {
		return evalProperty;
	}

	public void setEvalProperty(String evalProperty) {
		this.evalProperty = evalProperty;
	}

	public Date getSysCreateTime() {
		return sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

	public Date getSysFinshTime() {
		return sysFinshTime;
	}

	public void setSysFinshTime(Date sysFinshTime) {
		this.sysFinshTime = sysFinshTime;
	}
	
	
    
}