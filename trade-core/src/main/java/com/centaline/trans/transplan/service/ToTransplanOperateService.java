package com.centaline.trans.transplan.service;

import com.centaline.trans.transplan.entity.TtsTransPlanHistoryBatch;

/**
 * 交易计划操作service类
 * @author zhoujp7
 *
 */
public interface ToTransplanOperateService {

	/**
	 * 流程重启或重置操作
	 */
	public void processRestartOrResetOperate(String caseCode,String changeReason);
	
	/**
	 * 新增交易计划变量历史批次表
	 * @param ttsTransPlanHistoryBatch
	 * @return
	 */
	public long insertTtsTransPlanHistoryBatch(TtsTransPlanHistoryBatch ttsTransPlanHistoryBatch);
}
