package com.centaline.trans.ransom.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 赎楼面签VO
 * @author wbcaiyx
 *
 */
public class ToRansomSignVo {
	
	private Long pkid;
	/**
	 * 赎楼编号 
	 */
	private String ransomCode;
	/**
	 * 环节编号
	 */
	private String partCode;
	/**
	 * 面签时间
	 */
	private Date signTime;
	/**
	 * 面签金额
	 */
	private BigDecimal signMoney;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 价格(利息)
	 */
	private BigDecimal interest;
	/**
	 * 是否委托公证
	 */
	private String isEntrust;
	
	/**
	 * 预计陪同还贷时间
	 */
	private Date planPayloanTime;
	
	/**
	 * 更新人员
	 */
	private String updateUser;
	
	private Date updateTime;
	
	private String createUser;
	
	private Date createTime;
	
	public String getRansomCode() {
		return ransomCode;
	}
	public void setRansomCode(String ransomCode) {
		this.ransomCode = ransomCode;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public BigDecimal getSignMoney() {
		return signMoney;
	}
	public void setSignMoney(BigDecimal signMoney) {
		this.signMoney = signMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public String getIsEntrust() {
		return isEntrust;
	}
	public void setIsEntrust(String isEntrust) {
		this.isEntrust = isEntrust;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getPlanPayloanTime() {
		return planPayloanTime;
	}
	public void setPlanPayloanTime(Date planPayloanTime) {
		this.planPayloanTime = planPayloanTime;
	}
	
}
