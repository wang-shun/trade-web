package com.centaline.trans.wdcase.vo;

import java.math.BigDecimal;
import java.util.List;

import com.centaline.trans.wdcase.entity.TpdCommSubsDetals;

/**
 * 应收费用VO
 * 
 * @author jjm
 *
 */
public class CommSubsVo {
	/**
	 * 案件编号
	 */
	private String caseCode;
	/**
	 * 业绩编号
	 */
	private String bizCode;
	/**
	 * 应收金额
	 * 
	 */
	private BigDecimal commCost;
	/**
	 * 买方应付
	 */
	private BigDecimal buyerCost;
	/**
	 * 买方已付
	 */
	private BigDecimal buyerPaid;
	/**
	 * 卖方已付
	 */
	private BigDecimal sellerCost;
	/**
	 * 卖方已付
	 */
	private BigDecimal sellerPaid;
	/**
	 * 收款公司
	 */
	private String payee;
	/**
	 * 应收详细
	 */
	private List<TpdCommSubsDetals> commSubsDetals;

	public List<TpdCommSubsDetals> getCommSubsDetals() {
		return commSubsDetals;
	}

	public void setCommSubsDetals(List<TpdCommSubsDetals> commSubsDetals) {
		this.commSubsDetals = commSubsDetals;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public BigDecimal getCommCost() {
		return commCost;
	}

	public void setCommCost(BigDecimal commCost) {
		this.commCost = commCost;
	}

	public BigDecimal getBuyerCost() {
		return buyerCost;
	}

	public void setBuyerCost(BigDecimal buyerCost) {
		this.buyerCost = buyerCost;
	}

	public BigDecimal getBuyerPaid() {
		return buyerPaid;
	}

	public void setBuyerPaid(BigDecimal buyerPaid) {
		this.buyerPaid = buyerPaid;
	}

	public BigDecimal getSellerCost() {
		return sellerCost;
	}

	public void setSellerCost(BigDecimal sellerCost) {
		this.sellerCost = sellerCost;
	}

	public BigDecimal getSellerPaid() {
		return sellerPaid;
	}

	public void setSellerPaid(BigDecimal sellerPaid) {
		this.sellerPaid = sellerPaid;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}
}
