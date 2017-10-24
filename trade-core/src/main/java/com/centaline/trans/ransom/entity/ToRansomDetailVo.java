package com.centaline.trans.ransom.entity;

import java.math.BigDecimal;

/**
 * 赎楼详情VO
 * 
 * @author wbcaiyx
 *
 */
public class ToRansomDetailVo {

	/**
	 * 赎楼编号
	 */
	private String ransomCode;
	/**
	 * 案件编号
	 */
	private String caseCode;

	/**
	 * 赎楼状态
	 */
	private String ransomStatus;

	/**
	 * 借款金额
	 */
	private BigDecimal borrowMoney;
	/**
	 * 借款人
	 */
	private String borrowName;
	/**
	 * 借款人电话
	 */
	private String borrowTel;
	/**
	 * 房屋地址
	 */
	private String addr;
	/**
	 * 合作机构Code
	 */
	private String comOrgCode;
	/**
	 * 合作机构名
	 */
	private String comOrgName;
	/**
	 * 信贷员
	 */
	private String credit;
	/**
	 * 信贷员电话
	 */
	private String creditTel;
	/**
	 * 金融权证
	 */
	private String financial;
	/**
	 * 金融权证电话
	 */
	private String financialTel;
	/**
	 * 经纪人名
	 */
	private String agentName;
	/**
	 * 经济人电话
	 */
	private String agentPhone;
	/**
	 * 经办人
	 */
	private String leadingProcessId;
	/**
	 * 经纪人名
	 */
	private String leadingProcessName;
	/**
	 * 面签金额
	 */
	private BigDecimal interViewMoney;
	/**
	 * 还贷金额
	 */
	private BigDecimal repayLoanMoney;
	/**
	 * 贷款费用/利息
	 */
	private BigDecimal interest;
	/**
	 * 是否委托公证
	 */
	private String isEntrust;

	public String getRansomCode() {
		return ransomCode;
	}

	public void setRansomCode(String ransomCode) {
		this.ransomCode = ransomCode;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public BigDecimal getBorrowMoney() {
		return borrowMoney;
	}

	public void setBorrowMoney(BigDecimal borrowMoney) {
		this.borrowMoney = borrowMoney;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getBorrowTel() {
		return borrowTel;
	}

	public void setBorrowTel(String borrowTel) {
		this.borrowTel = borrowTel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getComOrgCode() {
		return comOrgCode;
	}

	public void setComOrgCode(String comOrgCode) {
		this.comOrgCode = comOrgCode;
	}

	public String getComOrgName() {
		return comOrgName;
	}

	public void setComOrgName(String comOrgName) {
		this.comOrgName = comOrgName;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getCreditTel() {
		return creditTel;
	}

	public void setCreditTel(String creditTel) {
		this.creditTel = creditTel;
	}

	public String getFinancial() {
		return financial;
	}

	public void setFinancial(String financial) {
		this.financial = financial;
	}

	public String getFinancialTel() {
		return financialTel;
	}

	public void setFinancialTel(String financialTel) {
		this.financialTel = financialTel;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentPhone() {
		return agentPhone;
	}

	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}

	public String getLeadingProcessId() {
		return leadingProcessId;
	}

	public void setLeadingProcessId(String leadingProcessId) {
		this.leadingProcessId = leadingProcessId;
	}

	public String getLeadingProcessName() {
		return leadingProcessName;
	}

	public void setLeadingProcessName(String leadingProcessName) {
		this.leadingProcessName = leadingProcessName;
	}

	public BigDecimal getInterViewMoney() {
		return interViewMoney;
	}

	public void setInterViewMoney(BigDecimal interViewMoney) {
		this.interViewMoney = interViewMoney;
	}

	public BigDecimal getRepayLoanMoney() {
		return repayLoanMoney;
	}

	public void setRepayLoanMoney(BigDecimal repayLoanMoney) {
		this.repayLoanMoney = repayLoanMoney;
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

	public String getRansomStatus() {
		return ransomStatus;
	}

	public void setRansomStatus(String ransomStatus) {
		this.ransomStatus = ransomStatus;
	}

	@Override
	public String toString() {
		return "ToRansomDetailVo [ransomCode=" + ransomCode + ", caseCode=" + caseCode + ", borrowMoney=" + borrowMoney
				+ ", borrowName=" + borrowName + ", borrowTel=" + borrowTel + ", addr=" + addr + ", comOrgCode="
				+ comOrgCode + ", comOrgName=" + comOrgName + ", credit=" + credit + ", creditTel=" + creditTel
				+ ", financial=" + financial + ", financialTel=" + financialTel + ", agentName=" + agentName
				+ ", agentPhone=" + agentPhone + ", leadingProcessId=" + leadingProcessId + ", leadingProcessName="
				+ leadingProcessName + ", interViewMoney=" + interViewMoney + ", repayLoanMoney=" + repayLoanMoney
				+ ", interest=" + interest + ", isEntrust=" + isEntrust + "]";
	}

}
