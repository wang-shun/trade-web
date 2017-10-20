package com.centaline.trans.api.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CCAI 评估返利报告确认信息
 * @author yinchao
 * @date 2017/10/11
 */
public class CcaiPostAssessFlowInfo {
	// 成交报告编号
	private String reportNo;
	// 评估值
	private BigDecimal assessPrice;
	// 评估公司名称
	private String assessCompany;
	// 评估公司成本
	private BigDecimal assessCompanyPrice;
	// 评估费收据
	private String assessReceip;
	// CCAI流程ID
	private String applyId;
	// 审批人域账户
	private String userName;
	// 审批人姓名
	private String realName;
	// 审批结果 -1拒绝  1通过
	private int result;
	// 审批意见
	private String comment;
	// 审批时间
	private Date approveTime;

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public BigDecimal getAssessPrice() {
		return assessPrice;
	}

	public void setAssessPrice(BigDecimal assessPrice) {
		this.assessPrice = assessPrice;
	}

	public String getAssessCompany() {
		return assessCompany;
	}

	public void setAssessCompany(String assessCompany) {
		this.assessCompany = assessCompany;
	}

	public String getAssessReceip() {
		return assessReceip;
	}

	public void setAssessReceip(String assessReceip) {
		this.assessReceip = assessReceip;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public BigDecimal getAssessCompanyPrice() {
		return assessCompanyPrice;
	}

	public void setAssessCompanyPrice(BigDecimal assessCompanyPrice) {
		this.assessCompanyPrice = assessCompanyPrice;
	}
}
