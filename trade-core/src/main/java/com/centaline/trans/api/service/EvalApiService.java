package com.centaline.trans.api.service;

import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.CcaiEvalRebateVo;
import com.centaline.trans.api.vo.FlowFeedBack;

/**
 * 评估模块交互的接口
 * @author yinchao
 * @date 2017/9/14
 */
public interface EvalApiService {
	/**
	 * 评估返利报告 反馈CCAI接口
	 * @param result 审批信息
	 * @param info 评估返利信息
	 * @return
	 */
	ApiResultData evalRebateFeedBack(FlowFeedBack result, CcaiEvalRebateVo info);
	//TODO 银行返利审批 同步到CCAI 财务进行审批
	//TODO 评估费结算/出账 待确认 同步到CCAI 还是在交易系统做
}
