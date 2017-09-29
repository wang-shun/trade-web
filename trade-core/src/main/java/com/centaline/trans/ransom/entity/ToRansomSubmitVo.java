package com.centaline.trans.ransom.entity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 赎楼提交VO
 * @author wbcaiyx
 *
 */
public class ToRansomSubmitVo {
	
	/**
	 * 案件编号
	 */
	private String caseCode;
	/**
	 * 任务id
	 */
	private String taskId; 
	/**
	 * 赎楼流程实例id
	 */
	private String processInstanceId;
	/**
	 * 赎楼编号
	 */
	private String ransomCode; 
	/**
	 * 抵押类型，1：一抵;2：二抵
	 */
	private Integer diyaType;
	/**
	 * 注销抵押时间
	 */
	private Date cancelDiyaTime;
	/**
	 * 领取产证时间
	 */
	private Date permitTime;
	/**
	 * 回款结清时间
	 */
	private Date paymentTime;
	/**
	 * 陪同还贷时间
	 */
	private Date mortgageTime;
	/**
	 * 还贷金额
	 */
	private BigDecimal repayLoanMoney;
	/**
	 * 注销抵押计划时间
	 */
	private Date planCancelTime;
	/**
	 * 领取产证计划时间
	 */
	private Date planPermitTime;
	/**
	 * 回款时间
	 */
	private Date planPaymentTime;
	/**
	 * 面签时间
	 */
	private Date  signTime;
	/**
	 * 计划陪同还贷时间
	 */
	private Date planMortgageTime;
	/**
	 * 面签金额
	 */
	private BigDecimal signMoney;
	/**
	 * 价格(利息)
	 */
	private BigDecimal interest;
	/**
	 * 是否委托公证
	 */
	private String isEntrust;
	/**
	 * 申请机构code
	 */
	private String applyOrgCode;
	/**
	 * 信贷员
	 */
	private String loanOfficer;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 计划面签时间
	 */
	private Date planSignTime;
	/**
	 * 主贷人
	 */
	private String borrowName;
	/**
	 * 更新人
	 */
	private String updateUser;
	
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
	public String getRansomCode() {
		return ransomCode;
	}
	public void setRansomCode(String ransomCode) {
		this.ransomCode = ransomCode;
	}
	public Integer getDiyaType() {
		return diyaType;
	}
	public void setDiyaType(Integer diyaType) {
		this.diyaType = diyaType;
	}
	public Date getCancelDiyaTime() {
		return cancelDiyaTime;
	}
	public void setCancelDiyaTime(Date cancelDiyaTime) {
		this.cancelDiyaTime = cancelDiyaTime;
	}
	public Date getPermitTime() {
		return permitTime;
	}
	public void setPermitTime(Date permitTime) {
		this.permitTime = permitTime;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public Date getMortgageTime() {
		return mortgageTime;
	}
	public void setMortgageTime(Date mortgageTime) {
		this.mortgageTime = mortgageTime;
	}
	public BigDecimal getRepayLoanMoney() {
		return repayLoanMoney;
	}
	public void setRepayLoanMoney(BigDecimal repayLoanMoney) {
		this.repayLoanMoney = repayLoanMoney;
	}
	public Date getPlanCancelTime() {
		return planCancelTime;
	}
	public void setPlanCancelTime(Date planCancelTime) {
		this.planCancelTime = planCancelTime;
	}
	public Date getPlanPermitTime() {
		return planPermitTime;
	}
	public void setPlanPermitTime(Date planPermitTime) {
		this.planPermitTime = planPermitTime;
	}
	public Date getPlanPaymentTime() {
		return planPaymentTime;
	}
	public void setPlanPaymentTime(Date planPaymentTime) {
		this.planPaymentTime = planPaymentTime;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public BigDecimal getSignMoney() {
		return signMoney;
	}
	public void setSignMoney(BigDecimal signMoney) {
		this.signMoney = signMoney;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public String getIsEntrust() {
		return isEntrust;
	}
	public void setIsEntrust(String isEntrust) {
		this.isEntrust = isEntrust;
	}
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	public String getLoanOfficer() {
		return loanOfficer;
	}
	public void setLoanOfficer(String loanOfficer) {
		this.loanOfficer = loanOfficer;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getPlanMortgageTime() {
		return planMortgageTime;
	}
	public void setPlanMortgageTime(Date planMortgageTime) {
		this.planMortgageTime = planMortgageTime;
	}
	public Date getPlanSignTime() {
		return planSignTime;
	}
	public void setPlanSignTime(Date planSignTime) {
		this.planSignTime = planSignTime;
	}
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	
	
	
}
