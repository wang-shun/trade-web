package com.centaline.trans.ransom.entity;

import java.util.Date;

/**
 * 赎楼申请VO
 * @author wbcaiyx
 *
 */
public class ToRansomApplyVo {
	
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
	 * 申请人/主贷人
	 */
	private String applyUser;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 申请机构code
	 */
	private String applyOrgCode;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 信贷员
	 */
	private String loanOfficer;

	/**
	 * 更新人员
	 */
	private String updateUser;
	
	private String createUser;

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

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoanOfficer() {
		return loanOfficer;
	}

	public void setLoanOfficer(String loanOfficer) {
		this.loanOfficer = loanOfficer;
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

	
	
	

	
	
}
