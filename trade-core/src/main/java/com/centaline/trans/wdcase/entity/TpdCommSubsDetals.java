package com.centaline.trans.wdcase.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 应收费用详情
 * 
 * @author jjm
 *
 */
public class TpdCommSubsDetals {
	/**
	 * 详情ID
	 */
	private Long pkid;
	/**
	 * 应收费用ID
	 */
	private Long commSubsId;
	/**
	 * 案件编号
	 */
	private String caseCode;
	/**
	 * 业绩编号
	 */
	private String bizCode;
	/**
	 * 应收科目编号
	 */
	private String commSubject;
	/**
	 * 服务描述
	 */
	private String srvDesc;

	/**
	 * 标准费率
	 */
	private BigDecimal stdRate;
	/**
	 * 标准费用
	 */
	private BigDecimal stdCost;
	/**
	 * 折扣
	 */
	private BigDecimal discount;
	/**
	 * 应收金额
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
	 * 卖方应付
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

	private String createBy;

	private Date createTime;

	private String updateBy;

	private Date updateTime;
	/**
	 * 是否删除
	 */
	private Integer isDeleted;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public Long getCommSubsId() {
		return commSubsId;
	}

	public void setCommSubsId(Long commSubsId) {
		this.commSubsId = commSubsId;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode == null ? null : caseCode.trim();
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode == null ? null : bizCode.trim();
	}

	public String getCommSubject() {
		return commSubject;
	}

	public void setCommSubject(String commSubject) {
		this.commSubject = commSubject == null ? null : commSubject.trim();
	}

	public String getSrvDesc() {
		return srvDesc;
	}

	public void setSrvDesc(String srvDesc) {
		this.srvDesc = srvDesc == null ? null : srvDesc.trim();
	}

	public BigDecimal getStdRate() {
		return stdRate;
	}

	public void setStdRate(BigDecimal stdRate) {
		this.stdRate = stdRate;
	}

	public BigDecimal getStdCost() {
		return stdCost;
	}

	public void setStdCost(BigDecimal stdCost) {
		this.stdCost = stdCost;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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
		this.payee = payee == null ? null : payee.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
}