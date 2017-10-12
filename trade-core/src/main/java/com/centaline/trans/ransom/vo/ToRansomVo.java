package com.centaline.trans.ransom.vo;

import java.util.Date;

public class ToRansomVo {

	private String ransomCode;
	
	private String borrowerName;
	
	private String borrowerPhone;
	
	private String signTime;
	
	private String finOrgCode;
	
	private String mortgageType;
	
	private String diyaType;
	
	private Double loanMoney;
	
	private Double restMoney;
	
	private Double borrowerMoney;

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

	public Double getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(Double loanMoney) {
		this.loanMoney = loanMoney;
	}

	public Double getRestMoney() {
		return restMoney;
	}

	public void setRestMoney(Double restMoney) {
		this.restMoney = restMoney;
	}

	public Double getBorrowerMoney() {
		return borrowerMoney;
	}

	public void setBorrowerMoney(Double borrowerMoney) {
		this.borrowerMoney = borrowerMoney;
	}

}
