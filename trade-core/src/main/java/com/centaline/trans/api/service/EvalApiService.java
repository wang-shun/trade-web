package com.centaline.trans.api.service;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.api.vo.*;
import com.centaline.trans.bankRebate.vo.ToBankRebateInfoVO;

import java.util.List;

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

	/**
	 * 获取CCAI评估公司信息
	 * @return
	 */
	CcaiAssessCompanyResultData getAllAssessCompany();

	/**
	 * 银行返利审批 同步到CCAI 财务进行审批
	 * @param info 银行返利申请信息
	 * @param userId 申请人ID
	 * @return
	 */
	ApiResultData evalBankRebateSync(ToBankRebateInfoVO info, String userId);
	/**
	 * 获取CCAI中 返利银行列表
	 * @return 返利银行名称 CCAI已名称作为唯一标识
	 */
	CcaiBankResultData getAllBankName();

	//TODO 评估费结算/出账 待确认 同步到CCAI 还是在交易系统做
}
