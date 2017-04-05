package com.centaline.trans.spv.entity;

import java.math.BigDecimal;

public class ToSpvDeDetailMix {
	
	private String deCondCode;
	
	private BigDecimal totalDeAmount;
	
	private BigDecimal sellerDeAmount;
	
	private String sellerName;
	
	private BigDecimal fundDeAmount;
	
	private String fundName;

	public String getDeCondCode() {
		return deCondCode;
	}

	public void setDeCondCode(String deCondCode) {
		this.deCondCode = deCondCode;
	}

	public BigDecimal getTotalDeAmount() {
		return totalDeAmount;
	}

	public void setTotalDeAmount(BigDecimal totalDeAmount) {
		this.totalDeAmount = totalDeAmount;
	}

	public BigDecimal getSellerDeAmount() {
		return sellerDeAmount;
	}

	public void setSellerDeAmount(BigDecimal sellerDeAmount) {
		this.sellerDeAmount = sellerDeAmount;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public BigDecimal getFundDeAmount() {
		return fundDeAmount;
	}

	public void setFundDeAmount(BigDecimal fundDeAmount) {
		this.fundDeAmount = fundDeAmount;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	} 

}
