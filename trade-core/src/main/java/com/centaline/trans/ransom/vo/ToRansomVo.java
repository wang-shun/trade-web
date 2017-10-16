package com.centaline.trans.ransom.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ToRansomVo {
	
	private String caseCode;
	
	private String ransomCode;
	
	private String borrowerName;
	
	private String borrowerPhone;
	
	private String signTime;
	
	private String finOrgCode;
	
	private String mortgageType;
	
	private String diyaType;
	
	private BigDecimal loanMoney;
	
	private BigDecimal restMoney;
	
	private BigDecimal borrowerMoney;

	private Date applyTime;
	
	private String applyRemake;
	
	private Date interviewTime;
	
	private String interviewRemake;
	
	/**
	 * 陪同还贷时间
	 */
	private Date repayTime;
	
	private String repayRemake;
	
	/**
	 * 注销抵押时间
	 */
	private Date cancelTime;
	
	private String cancelRemake;
	
	/**
	 * 赎回产证时间
	 */
	private Date redeemTime;
	
	private String redeemRemake;
	
	/**
	 * 回款结清时间
	 */
	private Date paymentTime;
	
	private String paymentRemake;

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getRansomCode() {
		return ransomCode;
	}

	public void setRansomCode(String ransomCode) {
		this.ransomCode = ransomCode;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBorrowerPhone() {
		return borrowerPhone;
	}

	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getFinOrgCode() {
		return finOrgCode;
	}

	public void setFinOrgCode(String finOrgCode) {
		this.finOrgCode = finOrgCode;
	}

	public String getMortgageType() {
		return mortgageType;
	}

	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}

	public String getDiyaType() {
		return diyaType;
	}

	public void setDiyaType(String diyaType) {
		this.diyaType = diyaType;
	}

	public BigDecimal getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}

	public BigDecimal getRestMoney() {
		return restMoney;
	}

	public void setRestMoney(BigDecimal restMoney) {
		this.restMoney = restMoney;
	}

	public BigDecimal getBorrowerMoney() {
		return borrowerMoney;
	}

	public void setBorrowerMoney(BigDecimal borrowerMoney) {
		this.borrowerMoney = borrowerMoney;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyRemake() {
		return applyRemake;
	}

	public void setApplyRemake(String applyRemake) {
		this.applyRemake = applyRemake;
	}

	public Date getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getInterviewRemake() {
		return interviewRemake;
	}

	public void setInterviewRemake(String interviewRemake) {
		this.interviewRemake = interviewRemake;
	}

	public Date getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}

	public String getRepayRemake() {
		return repayRemake;
	}

	public void setRepayRemake(String repayRemake) {
		this.repayRemake = repayRemake;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelRemake() {
		return cancelRemake;
	}

	public void setCancelRemake(String cancelRemake) {
		this.cancelRemake = cancelRemake;
	}

	public Date getRedeemTime() {
		return redeemTime;
	}

	public void setRedeemTime(Date redeemTime) {
		this.redeemTime = redeemTime;
	}

	public String getRedeemRemake() {
		return redeemRemake;
	}

	public void setRedeemRemake(String redeemRemake) {
		this.redeemRemake = redeemRemake;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getPaymentRemake() {
		return paymentRemake;
	}

	public void setPaymentRemake(String paymentRemake) {
		this.paymentRemake = paymentRemake;
	}
	
}
