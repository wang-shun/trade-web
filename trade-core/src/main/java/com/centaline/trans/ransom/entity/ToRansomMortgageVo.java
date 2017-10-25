package com.centaline.trans.ransom.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 赎楼陪同还贷VO
 * @author wbcaiyx
 *
 */
public class ToRansomMortgageVo {
	
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
	 * 陪同还贷时间
	 */
	private Date mortgageTime;
	/**
	 * 陪同还贷金额
	 */
	private BigDecimal repayLoanMoney;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 抵押类型
	 */
	private String diyaType;
	
	/**
	 * 是否有二抵
	 */
	private Integer isEr;
	
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDiyaType() {
		return diyaType;
	}

	public void setDiyaType(String diyaType) {
		this.diyaType = diyaType;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getMortgageTime() {
		return mortgageTime;
	}

	public void setMortgageTime(Date mortgageTime) {
		this.mortgageTime = mortgageTime;
	}

	public BigDecimal getRepayLoanMoney() {
		return repayLoanMoney;
	}

	public void setRepayLoanMoney(BigDecimal repayLoanMoney) {
		this.repayLoanMoney = repayLoanMoney;
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

	public Integer getIsEr() {
		return isEr;
	}

	public void setIsEr(Integer isEr) {
		this.isEr = isEr;
	}

}
