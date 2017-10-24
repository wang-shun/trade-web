package com.centaline.trans.api.vo;

import java.math.BigDecimal;

/**
 * CCAI反馈评估返利所需信息
 * @author yinchao
 * @date 2017/10/11
 */
public class CcaiEvalRebateVo {
	//交易系统评估编号/案件编号
	private String code;
	// 评估值
	private BigDecimal assessPrice;
	// 评估公司名称 需要与CCAI的评估公司名称一致
	private String assessCompany;
	// 评估费收据  多个用,分割
	private String assessReceip;
	// 评估公司成本
	private BigDecimal assessCompanyPrice;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public BigDecimal getAssessCompanyPrice() {
		return assessCompanyPrice;
	}

	public void setAssessCompanyPrice(BigDecimal assessCompanyPrice) {
		this.assessCompanyPrice = assessCompanyPrice;
	}
}
