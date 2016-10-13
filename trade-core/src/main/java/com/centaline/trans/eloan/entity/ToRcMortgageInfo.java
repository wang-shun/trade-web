package com.centaline.trans.eloan.entity;

import java.util.Date;

public class ToRcMortgageInfo {
	private Long pkid;

	private Long riskControlId;

	private String mortgageCategory;

	private String mortgageCode;

	private String mortgageName;

	private String createBy;

	private Date createTime;

	private Date updateTime;

	private String updateBy;

	private String riskType;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public Long getRiskControlId() {
		return riskControlId;
	}

	public void setRiskControlId(Long riskControlId) {
		this.riskControlId = riskControlId;
	}

	public String getMortgageCategory() {
		return mortgageCategory;
	}

	public void setMortgageCategory(String mortgageCategory) {
		this.mortgageCategory = mortgageCategory == null ? null : mortgageCategory.trim();
	}

	public String getMortgageCode() {
		return mortgageCode;
	}

	public void setMortgageCode(String mortgageCode) {
		this.mortgageCode = mortgageCode == null ? null : mortgageCode.trim();
	}

	public String getMortgageName() {
		return mortgageName;
	}

	public void setMortgageName(String mortgageName) {
		this.mortgageName = mortgageName == null ? null : mortgageName.trim();
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

}