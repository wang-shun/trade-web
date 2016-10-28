package com.centaline.trans.workspace.entity;

public class SimpleLoanAgent {
	private String realName;
	private String orgName;
	private String loanSevCode;
	private Double loanAmount;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLoanSevCode() {
		return loanSevCode;
	}

	public void setLoanSevCode(String loanSevCode) {
		this.loanSevCode = loanSevCode;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
}
