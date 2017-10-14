package com.centaline.trans.eval.vo;

import java.math.BigDecimal;
import java.util.List;


public class EvalAccountShowVO {
	
	private String caseCode;                       //
	private String evaCode;                   
	private String finOrgId;           
	private String applyDate;            
	private String issueDate;
	private BigDecimal evaPrice; 
	
	//实收评估费
	private BigDecimal evalRealCharges;
	
	private String propertyAddr;
	
	private String feeChangeReason;				//费用调整类型
	private BigDecimal settleFee;                          //
	private String rejectCause;			//驳回意见
	private String rejectPerson;       //驳回人
    private String approTime;			//驳回时间
	
	private List<Integer>taskIds;
	private List<String>caseCodes;
	private String userId;
	private BigDecimal evalComAmount;//结算费用  wbzhouht添加
	private String updateReason;       //修改原因

	public BigDecimal getEvalComAmount() {
		return evalComAmount;
	}

	public void setEvalComAmount(BigDecimal evalComAmount) {
		this.evalComAmount = evalComAmount;
	}

	public List<Integer> getTaskIds() {
		return taskIds;
	}
	public void setTaskIds(List<Integer> taskIds) {
		this.taskIds = taskIds;
	}
	public List<String> getCaseCodes() {
		return caseCodes;
	}
	public void setCaseCodes(List<String> caseCodes) {
		this.caseCodes = caseCodes;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getFinOrgId() {
		return finOrgId;
	}
	public void setFinOrgId(String finOrgId) {
		this.finOrgId = finOrgId;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public BigDecimal getEvaPrice() {
		return evaPrice;
	}
	public void setEvaPrice(BigDecimal evaPrice) {
		this.evaPrice = evaPrice;
	}
	public BigDecimal getEvalRealCharges() {
		return evalRealCharges;
	}
	public void setEvalRealCharges(BigDecimal evalRealCharges) {
		this.evalRealCharges = evalRealCharges;
	}
	public String getPropertyAddr() {
		return propertyAddr;
	}
	public void setPropertyAddr(String propertyAddr) {
		this.propertyAddr = propertyAddr;
	}
	public String getFeeChangeReason() {
		return feeChangeReason;
	}
	public void setFeeChangeReason(String feeChangeReason) {
		this.feeChangeReason = feeChangeReason;
	}
	public BigDecimal getSettleFee() {
		return settleFee;
	}
	public void setSettleFee(BigDecimal settleFee) {
		this.settleFee = settleFee;
	}
	public String getRejectCause() {
		return rejectCause;
	}
	public void setRejectCause(String rejectCause) {
		this.rejectCause = rejectCause;
	}
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public String getRejectPerson() {
		return rejectPerson;
	}

	public void setRejectPerson(String rejectPerson) {
		this.rejectPerson = rejectPerson;
	}

	public String getApproTime() {
		return approTime;
	}

	public void setApproTime(String approTime) {
		this.approTime = approTime;
	}
	
	
	
}
