package com.centaline.api.ccai.service;

import com.centaline.api.ccai.vo.EvalRebeatImport;
import com.centaline.api.ccai.vo.EvalRebeatReportImport;
import com.centaline.api.common.vo.CcaiServiceResult;

/**
 * CCAI评估相关接口处理
 * @author yinchao
 * @date 2017/10/18
 */
public interface CcaiEvalService {
	/**
	 * 导入评估返利信息
	 * @param info
	 */
	CcaiServiceResult importEvalRebate(EvalRebeatImport info);

	/**
	 * 驳回修改评估返利信息
	 * @param info
	 */
	CcaiServiceResult updateEvalRebate(EvalRebeatImport info);

	/**
	 * 导入最终生成的评估返利报告
	 * @param info
	 */
	CcaiServiceResult importEvalRebateReport(EvalRebeatReportImport info);
}
