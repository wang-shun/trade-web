package com.centaline.trans.ransom.entity;

import java.util.Date;

/**
 * 赎楼领取产证VO
 * @author wbcaiyx
 *
 */
public class ToRansomPermitVo {
	
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
	
}
