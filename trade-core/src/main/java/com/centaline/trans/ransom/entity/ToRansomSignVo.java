package com.centaline.trans.ransom.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 赎楼陪同还贷VO
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
	 * 更新人员
	 */
	private String updateUser;
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
	

	

	
	
}
