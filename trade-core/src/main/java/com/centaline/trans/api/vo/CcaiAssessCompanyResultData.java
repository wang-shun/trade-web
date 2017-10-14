package com.centaline.trans.api.vo;

import java.util.List;

/**
 * CCAI评估公司信息
 * @author yinchao
 * @date 2017/10/11
 */
public class CcaiAssessCompanyResultData extends ApiResultData {
	private List<AssessCompanyVo> assessCompanyList;

	public List<AssessCompanyVo> getAssessCompanyList() {
		return assessCompanyList;
	}

	public void setAssessCompanyList(List<AssessCompanyVo> assessCompanyList) {
		this.assessCompanyList = assessCompanyList;
	}

}
