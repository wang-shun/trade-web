package com.centaline.trans.ransom.entity;

import java.util.Date;

/**
 * 赎楼领取产证VO
 * @author wbcaiyx
 *
 */
public class ToRansomPermitVo {
	
	/**
	 * 赎楼编号 
	 */
	private String ransomCode;
	/**
	 * 环节编号
	 */
	private String partCode;
	/**
	 * 领取产证时间
	 */
	private Date redeemTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 抵押类型
	 */
	private String diyaType;
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
	public Date getRedeemTime() {
		return redeemTime;
	}
	public void setRedeemTime(Date redeemTime) {
		this.redeemTime = redeemTime;
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

	
	
	
}