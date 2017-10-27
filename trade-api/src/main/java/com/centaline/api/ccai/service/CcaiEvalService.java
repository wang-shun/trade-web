package com.centaline.api.ccai.service;

import com.centaline.api.ccai.vo.*;
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
	
	/**
	 * 导入评估退费信息
	 * @param info
	 */
	CcaiServiceResult importEvalRefund(EvalRefundImport info);
	
	/**
	 * 导入自办贷款审批信息或自办评估审批信息
	 * @param info
	 * @return
	 */
	CcaiServiceResult importSelfDo(SelfDoImport info);

	/**
	 * 驳回修改自办贷款审批/自办评估审批信息
	 * @param info
	 * @return
	 */
	CcaiServiceResult updateSelfDo(SelfDoImport info);

	/**
	 * 银行返利财务回馈
	 * @param feedBack
	 * @return
	 */
	CcaiServiceResult bankRebateFeedBack(BankRebeatFeedBack feedBack);

}
