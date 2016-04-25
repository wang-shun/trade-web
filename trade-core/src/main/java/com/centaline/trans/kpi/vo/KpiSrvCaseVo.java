package com.centaline.trans.kpi.vo;

import com.aist.common.utils.excel.annotation.ExcelField;

public class KpiSrvCaseVo {
	/**
	 * 是否是当月
	 */
	private boolean currentMonth;
	
	private String createBy;

	@ExcelField(title = "序号")
	private String snNo;

	@ExcelField(title = "誉萃编号")
	private String caseCode;
	/**
	 * 下家签约
	 */
	@ExcelField(title = "下家签约")
	private Double signScore;
	/**
	 * 上家签约
	 */
	@ExcelField(title = "上家签约")
	private Double salesSignScore;
	/**
	 * 下家过户
	 */
	@ExcelField(title = "下家过户")
	private Double transferScore;
	/**
	 * 上家过户
	 */
	@ExcelField(title = "上家过户")
	private Double salesTransferScore;
	/**
	 * 下家贷款
	 */
	@ExcelField(title = "下家贷款")
	private Double comLoanScore;
	/**
	 * 下家纯公积金
	 */
	@ExcelField(title = "下家纯公积金")
	private Double accuFundScore;
	/**
	 * 上家陪同还贷
	 */
	@ExcelField(title = "上家陪同还贷")
	private Double accompanyRepayLoanScore;
	/**
	 * 上家电话接通
	 */
	@ExcelField(title = "上家电话接通")
	private String salesCallBack;
	/**
	 * 下家电话接通
	 * 
	 */
	@ExcelField(title = "下家电话接通")
	private String callBack;
	
	@ExcelField(title = "下家备注")
	private String buyerComment;
	@ExcelField(title = "上家备注")
	private String salerComment;

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public Double getSignScore() {
		return signScore;
	}

	public void setSignScore(Double signScore) {
		this.signScore = signScore;
	}

	public Double getSalesSignScore() {
		return salesSignScore;
	}

	public void setSalesSignScore(Double salesSignScore) {
		this.salesSignScore = salesSignScore;
	}

	public Double getTransferScore() {
		return transferScore;
	}

	public void setTransferScore(Double transferScore) {
		this.transferScore = transferScore;
	}

	public Double getSalesTransferScore() {
		return salesTransferScore;
	}

	public void setSalesTransferScore(Double salesTransferScore) {
		this.salesTransferScore = salesTransferScore;
	}

	public Double getComLoanScore() {
		return comLoanScore;
	}

	public void setComLoanScore(Double comLoanScore) {
		this.comLoanScore = comLoanScore;
	}

	public Double getAccuFundScore() {
		return accuFundScore;
	}

	public void setAccuFundScore(Double accuFundScore) {
		this.accuFundScore = accuFundScore;
	}

	public Double getAccompanyRepayLoanScore() {
		return accompanyRepayLoanScore;
	}

	public void setAccompanyRepayLoanScore(Double accompanyRepayLoanScore) {
		this.accompanyRepayLoanScore = accompanyRepayLoanScore;
	}

	public String getSalesCallBack() {
		return salesCallBack;
	}

	public void setSalesCallBack(String salesCallBack) {
		this.salesCallBack = salesCallBack;
	}

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public boolean isCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(boolean currentMonth) {
		this.currentMonth = currentMonth;
	}

	public String getBuyerComment() {
		return buyerComment;
	}

	public void setBuyerComment(String buyerComment) {
		this.buyerComment = buyerComment;
	}

	public String getSalerComment() {
		return salerComment;
	}

	public void setSalerComment(String salerComment) {
		this.salerComment = salerComment;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

}
