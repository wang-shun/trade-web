package com.centaline.trans.ransom.entity;

import java.util.Date;

/**
 * 回款结清产证VO
 * @author wbcaiyx
 *
 */
public class ToRansomPaymentVo {
	
	/**
	 * 赎楼编号 
	 */
	private String ransomCode;
	/**
	 * 环节编号
	 */
	private String partCode;
	/**
	 * 回款结清时间
	 */
	private Date paymentTime;
	/**
	 * 备注
	 */
	private String remark;
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
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	

	
	
	
}
