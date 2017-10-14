package com.centaline.trans.api.vo;

import java.math.BigDecimal;

/**
 * CCAI 评估公司信息
 * @author yinchao
 * @date 2017/10/11
 */
public class AssessCompanyVo {
	//评估公司名称
	private String AssessCompanyName;
	// 评估公司成本 成本为0 需要权证手动输入
	private BigDecimal AssessCompanyPrice;

	public String getAssessCompanyName() {
		return AssessCompanyName;
	}

	public void setAssessCompanyName(String assessCompanyName) {
		AssessCompanyName = assessCompanyName;
	}

	public BigDecimal getAssessCompanyPrice() {
		return AssessCompanyPrice;
	}

	public void setAssessCompanyPrice(BigDecimal assessCompanyPrice) {
		AssessCompanyPrice = assessCompanyPrice;
	}

	@Override
	public String toString() {
		return "AssessCompanyVo{" +
				"AssessCompanyName='" + AssessCompanyName + '\'' +
				", AssessCompanyPrice=" + AssessCompanyPrice +
				'}';
	}
}
