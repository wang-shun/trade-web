package com.centaline.trans.cases.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ToEvaRefundVO {
	private Long pkid;

    private String caseCode;

    private String evaCode;

    private String refundKinds;

    private Date pricingTime;

    private String proposer;

    private String applyDepart;

    private Date applyTime;

    private BigDecimal refundAmount;

    private String refundTarget;

    private String refundCause;

    private String finOrgId;

    private Date toRefundTime;

    private Integer reportBackNum;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date backTime;

    private String backCause;

    private BigDecimal evalRealCharges;

    private String evaProcessId;

    private String status;
    
    private String ccaiCode;
    
    private String applyId;
    
    private String proposerId;
    
    private String applyDepartCode;
    
    private String evalCompany;
    
    private String city;
    
	private String taskId;
	private String processInstanceId;
	private String processDefinitionId;
	
	private String partCode;
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
		this.caseCode = caseCode;
	}
	public String getEvaCode() {
		return evaCode;
	}
	public void setEvaCode(String evaCode) {
		this.evaCode = evaCode;
	}
	public String getRefundKinds() {
		return refundKinds;
	}
	public void setRefundKinds(String refundKinds) {
		this.refundKinds = refundKinds;
	}
	public Date getPricingTime() {
		return pricingTime;
	}
	public void setPricingTime(Date pricingTime) {
		this.pricingTime = pricingTime;
	}
	public String getProposer() {
		return proposer;
	}
	public void setProposer(String proposer) {
		this.proposer = proposer;
	}
	public String getApplyDepart() {
		return applyDepart;
	}
	public void setApplyDepart(String applyDepart) {
		this.applyDepart = applyDepart;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRefundTarget() {
		return refundTarget;
	}
	public void setRefundTarget(String refundTarget) {
		this.refundTarget = refundTarget;
	}
	public String getRefundCause() {
		return refundCause;
	}
	public void setRefundCause(String refundCause) {
		this.refundCause = refundCause;
	}
	public String getFinOrgId() {
		return finOrgId;
	}
	public void setFinOrgId(String finOrgId) {
		this.finOrgId = finOrgId;
	}
	public Date getToRefundTime() {
		return toRefundTime;
	}
	public void setToRefundTime(Date toRefundTime) {
		this.toRefundTime = toRefundTime;
	}
	public Integer getReportBackNum() {
		return reportBackNum;
	}
	public void setReportBackNum(Integer reportBackNum) {
		this.reportBackNum = reportBackNum;
	}
	public Date getBackTime() {
		return backTime;
	}
	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
	public String getBackCause() {
		return backCause;
	}
	public void setBackCause(String backCause) {
		this.backCause = backCause;
	}
	public BigDecimal getEvalRealCharges() {
		return evalRealCharges;
	}
	public void setEvalRealCharges(BigDecimal evalRealCharges) {
		this.evalRealCharges = evalRealCharges;
	}
	public String getEvaProcessId() {
		return evaProcessId;
	}
	public void setEvaProcessId(String evaProcessId) {
		this.evaProcessId = evaProcessId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCcaiCode() {
		return ccaiCode;
	}
	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getProposerId() {
		return proposerId;
	}
	public void setProposerId(String proposerId) {
		this.proposerId = proposerId;
	}
	public String getApplyDepartCode() {
		return applyDepartCode;
	}
	public void setApplyDepartCode(String applyDepartCode) {
		this.applyDepartCode = applyDepartCode;
	}
	public String getEvalCompany() {
		return evalCompany;
	}
	public void setEvalCompany(String evalCompany) {
		this.evalCompany = evalCompany;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	
	
}
