package com.centaline.trans.award.vo;

import com.aist.common.utils.excel.annotation.ExcelField;

public class KpiSrvCaseVo {
	/**
	 * 是否是当月
	 */
	private boolean currentMonth;
	
	private String msg;
	
	private String createBy;

	@ExcelField(title = "序号")
	private String snNo;

	@ExcelField(title = "誉萃编号")
	private String caseCode;
	
	/**
	 * 上家签约
	 */
	@ExcelField(title = "上家签约")
	private String salesSignScore;
	/**
	 * 上家陪同还贷
	 */
	@ExcelField(title = "上家陪同还贷")
	private String accompanyRepayLoanScore;
	/**
	 * 上家过户
	 */
	@ExcelField(title = "上家过户")
	private String salesTransferScore;

	/**
	 * 下家签约
	 */
	@ExcelField(title = "下家签约")
	private String signScore;
	/**
	 * 下家贷款
	 */
	@ExcelField(title = "下家贷款")
	private String comLoanScore;
	/**
	 * 下家纯公积金
	 */
	@ExcelField(title = "下家纯公积金")
	private String accuFundScore;


	/**
	 * 下家过户
	 */
	@ExcelField(title = "下家过户")
	private String transferScore;
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
	@ExcelField(title = "上家备注")
	private String salerComment;

	@ExcelField(title = "下家备注")
	private String buyerComment;

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	

	public String getSalesSignScore() {
		return salesSignScore;
	}

	public void setSalesSignScore(String salesSignScore) {
		this.salesSignScore = salesSignScore;
	}

	public String getAccompanyRepayLoanScore() {
		return accompanyRepayLoanScore;
	}

	public void setAccompanyRepayLoanScore(String accompanyRepayLoanScore) {
		this.accompanyRepayLoanScore = accompanyRepayLoanScore;
	}

	public String getSalesTransferScore() {
		return salesTransferScore;
	}

	public void setSalesTransferScore(String salesTransferScore) {
		this.salesTransferScore = salesTransferScore;
	}

	public String getSignScore() {
		return signScore;
	}

	public void setSignScore(String signScore) {
		this.signScore = signScore;
	}

	public String getComLoanScore() {
		return comLoanScore;
	}

	public void setComLoanScore(String comLoanScore) {
		this.comLoanScore = comLoanScore;
	}

	public String getAccuFundScore() {
		return accuFundScore;
	}

	public void setAccuFundScore(String accuFundScore) {
		this.accuFundScore = accuFundScore;
	}

	public String getTransferScore() {
		return transferScore;
	}

	public void setTransferScore(String transferScore) {
		this.transferScore = transferScore;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
