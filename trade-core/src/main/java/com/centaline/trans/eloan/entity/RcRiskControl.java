package com.centaline.trans.eloan.entity;

import java.util.Date;

public class RcRiskControl {
	private Long pkid;

	private String eloanCode;

	private String riskType;

	private String riskComment;

	private String createBy;

	private Date createTime;

	private Date updateTime;

	private String updateBy;

	private String caseCode;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getEloanCode() {
		return eloanCode;
	}

	public void setEloanCode(String eloanCode) {
		this.eloanCode = eloanCode == null ? null : eloanCode.trim();
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType == null ? null : riskType.trim();
	}

	public String getRiskComment() {
		return riskComment;
	}

	public void setRiskComment(String riskComment) {
		this.riskComment = riskComment == null ? null : riskComment.trim();
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

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

}