package com.centaline.trans.workspace.entity;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class LoanStaDetails {
	// 统计金额
	private Double amount;
	// 统计数量
	private Integer count;
	// 转化率
	private Double convRate;
	// 统计项
	private String staItem;

	private String staItemStr;
	NumberFormat formatter = new DecimalFormat("###,##0.00");

	/**
	 * @return the amount
	 */
	public String getAmount() {
		this.amount = this.amount == null ? 0d : this.amount;
		return formatter.format(this.amount/10000)+"万";
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the convRate
	 */
	public Double getConvRate() {
		return convRate;
	}

	/**
	 * @param convRate
	 *            the convRate to set
	 */
	public void setConvRate(Double convRate) {
		this.convRate = convRate;
	}

	/**
	 * @return the staItem
	 */
	public String getStaItem() {
		return staItem;
	}

	/**
	 * @param staItem
	 *            the staItem to set
	 */
	public void setStaItem(String staItem) {
		this.staItem = staItem;
	}

	/**
	 * @return the staItemStr
	 */
	public String getStaItemStr() {
		return staItemStr;
	}

	/**
	 * @param staItemStr
	 *            the staItemStr to set
	 */
	public void setStaItemStr(String staItemStr) {
		this.staItemStr = staItemStr;
	}

}
