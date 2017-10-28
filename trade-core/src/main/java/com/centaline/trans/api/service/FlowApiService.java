package com.centaline.trans.api.service;

import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.common.enums.CcaiTaskEnum;

/**
 * 流程审批相关接口
 * @author yinchao
 * @date 2017/9/14
 */
public interface FlowApiService {
	/**
	 * 交易主流程结果反馈
	 * @param caseCode 案件caseCode
	 * @param task 流程环节
	 * @param info 审批结果
	 * @return
	 */
	ApiResultData tradeFeedBackCcai(String caseCode, CcaiTaskEnum task, FlowFeedBack info);

	/**
	 * 通用与CCAI流程交互反馈
	 * @param applyId CCAI流程shiliID
	 * @param task 任务环节枚举
	 * @param info 审批信息
	 * @return
	 */
	ApiResultData feedBackCcai(String applyId,CcaiTaskEnum task,FlowFeedBack info);

}
