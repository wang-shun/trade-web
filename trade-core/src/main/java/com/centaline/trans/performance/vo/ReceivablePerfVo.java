package com.centaline.trans.performance.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 生成应收业绩所需求封装的VO
 * 
 * @author jjm
 *
 */
public class ReceivablePerfVo {

	/**
	 * 产品编号
	 */
	private String prodCode;
	/**
	 * 业绩分拆比例
	 */
	private BigDecimal sharesRate;
	/**
	 * 案件编号
	 */
	private String caseCode;
	/**
	 * 业务编号
	 */
	private String bizCode;
	/**
	 * 业务PKID
	 */
	private String bizPkid;
	/**
	 * 业绩科目
	 */
	private String subject;
	/**
	 * 
	 * 角色类型
	 */
	private String roleType;
	/**
	 * 业绩基数
	 * 
	 * 
	 */
	private BigDecimal salesAmount;
	/**
	 * 业绩人
	 * 
	 */
	private String userId;
	/**
	 * 业绩时间
	 */
	private Date sharesTime;
	/**
	 * 业绩人组织
	 */
	private String orgId;

	public BigDecimal getSharesRate() {
		return sharesRate;
	}

	public void setSharesRate(BigDecimal sharesRate) {
		this.sharesRate = sharesRate;
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

	public String getBizPkid() {
		return bizPkid;
	}

	public void setBizPkid(String bizPkid) {
		this.bizPkid = bizPkid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public BigDecimal getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(BigDecimal salesAmount) {
		this.salesAmount = salesAmount;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Date getSharesTime() {
		return sharesTime;
	}

	public void setSharesTime(Date sharesTime) {
		this.sharesTime = sharesTime;
	}
}
