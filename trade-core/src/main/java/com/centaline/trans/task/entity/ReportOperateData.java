package com.centaline.trans.task.entity;

import java.math.BigDecimal;
/**
 * 大数据报表
 * @author hejf10
 *
 */
public class ReportOperateData {
	
	/**
     * 商贷金额
     */
  private BigDecimal mortComAmount;
  /**
   * 公积金金额
   */
  private BigDecimal mortPrfAmount;
  /**
   * 合计
   */
  private BigDecimal mortAllAmount;
  /**
     * 商贷金额
 */
  private BigDecimal dkmortComAmount;
  /**
   * 公积金金额
   */
  private BigDecimal dkmortPrfAmount;
  /**
   * 合计
   */
  private BigDecimal dkmortAllAmount;
	 
	/**
	   * 贷款签约数据
	   */
	  private String mortType;
   /**
   * 月份
   */
	private int month;

	/**
	 * 派单量
	 */
	private int dispatchSum;
	/**
	 * 签约量（买卖）
	 */
	private int realConSum;
	/**
	 * 过户量
	 */
	private int transferAppPassSum;
	/**
	 * 商贷签约量
	 */
	private int comSum;
	/**
	 * 公积金签约量
	 */
	private int prfSum;
	/**
	 * 案件合同价
	 */
	private BigDecimal conPrice;
	/**
	 * 无贷款单数
	 */
	private int loanReqNum;
	/**
	 * 公积金单数
	 */
	private int prfNum;
	/**
	 * 商贷单数
	 */
	private int comNum;
	/**
	 * 总单数
	 */
	private int allNum;
	/**
	 * 过户房价
	 */
	private BigDecimal allRealPrice;
	/**
	 * 有商贷案件房价
	 */
	private BigDecimal realPrice;
	/**
	 * 商贷收单（商贷）
	 */
	private int comRec;
	/**
	 * 流失单数（商贷）
	 */
	private int lsRec;
	/**
	 * 收单金额（商贷）
	 */
	private int sdAmount;
	/**
	 * 流失金额（商贷
	 */
	private int lsAmount;
	/**
	 * 商贷
	 */
	private int Allcom;
	
    public int getDispatchSum() {
		return dispatchSum;
	}
	public void setDispatchSum(int dispatchSum) {
		this.dispatchSum = dispatchSum;
	}
	public int getRealConSum() {
		return realConSum;
	}
	public void setRealConSum(int realConSum) {
		this.realConSum = realConSum;
	}
	public int getTransferAppPassSum() {
		return transferAppPassSum;
	}
	public void setTransferAppPassSum(int transferAppPassSum) {
		this.transferAppPassSum = transferAppPassSum;
	}
	public int getComSum() {
		return comSum;
	}
	public void setComSum(int comSum) {
		this.comSum = comSum;
	}
	public int getPrfSum() {
		return prfSum;
	}
	public void setPrfSum(int prfSum) {
		this.prfSum = prfSum;
	}
	public int getLoanReqNum() {
		return loanReqNum;
	}
	public void setLoanReqNum(int loanReqNum) {
		this.loanReqNum = loanReqNum;
	}
	public int getPrfNum() {
		return prfNum;
	}
	public void setPrfNum(int prfNum) {
		this.prfNum = prfNum;
	}
	public int getComNum() {
		return comNum;
	}
	public void setComNum(int comNum) {
		this.comNum = comNum;
	}
	public int getAllNum() {
		return allNum;
	}
	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}
	public BigDecimal getAllRealPrice() {
		return allRealPrice;
	}
	public void setAllRealPrice(BigDecimal allRealPrice) {
		this.allRealPrice = allRealPrice;
	}
	public BigDecimal getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}
	public int getComRec() {
		return comRec;
	}
	public void setComRec(int comRec) {
		this.comRec = comRec;
	}
	public int getLsRec() {
		return lsRec;
	}
	public void setLsRec(int lsRec) {
		this.lsRec = lsRec;
	}
	public int getSdAmount() {
		return sdAmount;
	}
	public void setSdAmount(int sdAmount) {
		this.sdAmount = sdAmount;
	}
	public int getLsAmount() {
		return lsAmount;
	}
	public void setLsAmount(int lsAmount) {
		this.lsAmount = lsAmount;
	}
	public int getAllcom() {
		return Allcom;
	}
	public void setAllcom(int allcom) {
		Allcom = allcom;
	}
	public BigDecimal getMortComAmount() {
		return mortComAmount;
	}
	public void setMortComAmount(BigDecimal mortComAmount) {
		this.mortComAmount = mortComAmount;
	}
	public BigDecimal getMortPrfAmount() {
		return mortPrfAmount;
	}
	public void setMortPrfAmount(BigDecimal mortPrfAmount) {
		this.mortPrfAmount = mortPrfAmount;
	}
	public BigDecimal getMortAllAmount() {
		return mortAllAmount;
	}
	public void setMortAllAmount(BigDecimal mortAllAmount) {
		this.mortAllAmount = mortAllAmount;
	}
	public String getMortType() {
		return mortType;
	}
	public void setMortType(String mortType) {
		this.mortType = mortType;
	}
	public BigDecimal getDkmortComAmount() {
			return dkmortComAmount;
	}
	public void setDkmortComAmount(BigDecimal dkmortComAmount) {
		this.dkmortComAmount = dkmortComAmount;
	}
	public BigDecimal getDkmortPrfAmount() {
		return dkmortPrfAmount;
	}
	public void setDkmortPrfAmount(BigDecimal dkmortPrfAmount) {
		this.dkmortPrfAmount = dkmortPrfAmount;
	}
	public BigDecimal getDkmortAllAmount() {
		return dkmortAllAmount;
	}
	public void setDkmortAllAmount(BigDecimal dkmortAllAmount) {
		this.dkmortAllAmount = dkmortAllAmount;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public BigDecimal getConPrice() {
		return conPrice;
	}
	public void setConPrice(BigDecimal conPrice) {
		this.conPrice = conPrice;
	}
	  
}