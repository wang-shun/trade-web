package com.centaline.trans.ransom.entity;

import java.math.BigDecimal;
/**
 * 赎楼详情VO
 * @author wbcaiyx
 *
 */
public class ToRansomTailVo {
	/**
	 * 尾款机构Code
	 */
	private String       finOrgCode;
	/**
	 * 尾款机构名
	 */
	private String      tailOrgName;
	/**
	 * 尾款类型
	 */
	private String      mortgageType;
	/**
	 * 抵押类型
	 */
	private String      diyaType;
	/**
	 * 贷款金额
	 */
	private BigDecimal  loanMoney;
	/**
	 * 剩余部分金额
	 */
	private BigDecimal  restMoney;
	/**
	 * 实际还款金额
	 */
	private BigDecimal  actualMoney;
	
	
	public String getFinOrgCode() {
		return finOrgCode;
	}
	public void setFinOrgCode(String finOrgCode) {
		this.finOrgCode = finOrgCode;
	}
	public String getTailOrgName() {
		return tailOrgName;
	}
	public void setTailOrgName(String tailOrgName) {
		this.tailOrgName = tailOrgName;
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
	public BigDecimal getActualMoney() {
		return actualMoney;
	}
	public void setActualMoney(BigDecimal actualMoney) {
		this.actualMoney = actualMoney;
	}
	
	
}
