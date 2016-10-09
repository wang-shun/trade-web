package com.centaline.trans.spv.entity;

import java.math.BigDecimal;

public class ToSpvDeDetailMix {
	
	private String deCondCode;
	
	private BigDecimal totalDeAmount;
	
	private BigDecimal sellerDeAmount;
	
	private BigDecimal fundDeAmount;

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

	public BigDecimal getFundDeAmount() {
		return fundDeAmount;
	}

	public void setFundDeAmount(BigDecimal fundDeAmount) {
		this.fundDeAmount = fundDeAmount;
	} 
	
	

}
