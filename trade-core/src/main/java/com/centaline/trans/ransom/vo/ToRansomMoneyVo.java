package com.centaline.trans.ransom.vo;

import java.math.BigDecimal;

/**
 * 赎楼金额Vo
 * @author wbwumf
 *
 *2017年10月26日
 */
public class ToRansomMoneyVo {

	private String ransomCode;
	
	/**
	 * 借款金额
	 */
	private BigDecimal borrowerMoney;
	
	/**
	 * 面前金额
	 */
	private BigDecimal interViewMoney;
	
	/**
	 * 还贷金额
	 */
	private BigDecimal repayLoanMoney;
	
	/**
	 * 贷款费用(利息:‱每天)
	 */
	private String interest;
	
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

	public BigDecimal getBorrowerMoney() {
		return borrowerMoney;
	}

	public void setBorrowerMoney(BigDecimal borrowerMoney) {
		this.borrowerMoney = borrowerMoney;
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

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getIsEntrust() {
		return isEntrust;
	}

	public void setIsEntrust(String isEntrust) {
		this.isEntrust = isEntrust;
	}
	
	
}
