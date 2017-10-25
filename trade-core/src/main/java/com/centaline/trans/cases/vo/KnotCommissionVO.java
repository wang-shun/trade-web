package com.centaline.trans.cases.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wbzhouht
 * 结佣
 */
public class KnotCommissionVO {
	private String caseCode;//案件编号
	private String partCode;//环节编码
	private String taskId;//任务id
	private String processInstanceId;//流程引擎id
	/**
	 * 佣金折返评估费显示的内容
	 * @return
	 */
	private BigDecimal receivableAssessmentFee;//应收评估费
	private BigDecimal receivedAssessmentFee;//实收评估费
	private String AssessmentFeeReturnable;//评估费折返比例
	private BigDecimal receivableAgencyFee;//应收中介费
	private BigDecimal receivedAgencyFee;//实收中介费
	private String AgenvyFeeReturnable;//中介费折扣比例
	private BigDecimal adjustmentAmount;//调整金额

	//佣金折返审批内容
	private String chargesAppoverStatus;//佣金审批状态
	private Date chargesAppoverTime;//佣金审批时间
	private String chargesAppoverAuthor;//佣金审批人员

	/**
	 * 自办评估
	 * @return
	 */
	private String evaResult;//自办评估的原因
	private String evaCompany;//自办评估公司的名称
	private String evaAppoverStatus;//自办评估审批状态
	private Date evaAppoverTime;//自办评估审批时间
	private String getEvaAppoverAuthor;//自办评估审批人员

	/**
	 * 自办贷款
	 * @return
	 */
	private String morageResult;//自办贷款的原因
	private String morageBank;//自办银行的名称
	private BigDecimal morageDrainAmount;//贷款流失的金额
	private String morageAppoverStatus;//自办贷款的审批状态
	private Date morageAppoverTime;//自办贷款的审批时间
	private String morageAppoverAuthor;//自办贷款的审批人员

	public BigDecimal getReceivableAssessmentFee() {
		return receivableAssessmentFee;
	}

	public void setReceivableAssessmentFee(BigDecimal receivableAssessmentFee) {
		this.receivableAssessmentFee = receivableAssessmentFee;
	}

	public BigDecimal getReceivedAssessmentFee() {
		return receivedAssessmentFee;
	}

	public void setReceivedAssessmentFee(BigDecimal receivedAssessmentFee) {
		this.receivedAssessmentFee = receivedAssessmentFee;
	}

	public String getAssessmentFeeReturnable() {
		return AssessmentFeeReturnable;
	}

	public void setAssessmentFeeReturnable(String assessmentFeeReturnable) {
		AssessmentFeeReturnable = assessmentFeeReturnable;
	}

	public BigDecimal getReceivableAgencyFee() {
		return receivableAgencyFee;
	}

	public void setReceivableAgencyFee(BigDecimal receivableAgencyFee) {
		this.receivableAgencyFee = receivableAgencyFee;
	}

	public BigDecimal getReceivedAgencyFee() {
		return receivedAgencyFee;
	}

	public void setReceivedAgencyFee(BigDecimal receivedAgencyFee) {
		this.receivedAgencyFee = receivedAgencyFee;
	}

	public String getAgenvyFeeReturnable() {
		return AgenvyFeeReturnable;
	}

	public void setAgenvyFeeReturnable(String agenvyFeeReturnable) {
		AgenvyFeeReturnable = agenvyFeeReturnable;
	}

	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}

	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	public String getChargesAppoverStatus() {
		return chargesAppoverStatus;
	}

	public void setChargesAppoverStatus(String chargesAppoverStatus) {
		this.chargesAppoverStatus = chargesAppoverStatus;
	}

	public Date getChargesAppoverTime() {
		return chargesAppoverTime;
	}

	public void setChargesAppoverTime(Date chargesAppoverTime) {
		this.chargesAppoverTime = chargesAppoverTime;
	}

	public String getChargesAppoverAuthor() {
		return chargesAppoverAuthor;
	}

	public void setChargesAppoverAuthor(String chargesAppoverAuthor) {
		this.chargesAppoverAuthor = chargesAppoverAuthor;
	}

	public String getEvaResult() {
		return evaResult;
	}

	public void setEvaResult(String evaResult) {
		this.evaResult = evaResult;
	}

	public String getEvaCompany() {
		return evaCompany;
	}

	public void setEvaCompany(String evaCompany) {
		this.evaCompany = evaCompany;
	}

	public String getEvaAppoverStatus() {
		return evaAppoverStatus;
	}

	public void setEvaAppoverStatus(String evaAppoverStatus) {
		this.evaAppoverStatus = evaAppoverStatus;
	}

	public Date getEvaAppoverTime() {
		return evaAppoverTime;
	}

	public void setEvaAppoverTime(Date evaAppoverTime) {
		this.evaAppoverTime = evaAppoverTime;
	}

	public String getGetEvaAppoverAuthor() {
		return getEvaAppoverAuthor;
	}

	public void setGetEvaAppoverAuthor(String getEvaAppoverAuthor) {
		this.getEvaAppoverAuthor = getEvaAppoverAuthor;
	}

	public String getMorageResult() {
		return morageResult;
	}

	public void setMorageResult(String morageResult) {
		this.morageResult = morageResult;
	}

	public String getMorageBank() {
		return morageBank;
	}

	public void setMorageBank(String morageBank) {
		this.morageBank = morageBank;
	}

	public BigDecimal getMorageDrainAmount() {
		return morageDrainAmount;
	}

	public void setMorageDrainAmount(BigDecimal morageDrainAmount) {
		this.morageDrainAmount = morageDrainAmount;
	}

	public String getMorageAppoverStatus() {
		return morageAppoverStatus;
	}

	public void setMorageAppoverStatus(String morageAppoverStatus) {
		this.morageAppoverStatus = morageAppoverStatus;
	}

	public Date getMorageAppoverTime() {
		return morageAppoverTime;
	}

	public void setMorageAppoverTime(Date morageAppoverTime) {
		this.morageAppoverTime = morageAppoverTime;
	}

	public String getMorageAppoverAuthor() {
		return morageAppoverAuthor;
	}

	public void setMorageAppoverAuthor(String morageAppoverAuthor) {
		this.morageAppoverAuthor = morageAppoverAuthor;
	}

	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
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
	
}
