package com.centaline.trans.transplan.service;

import java.util.List;

import com.centaline.trans.transplan.entity.TtsReturnVisitRegistration;
import com.centaline.trans.transplan.entity.TtsTransPlanHistoryBatch;
import com.centaline.trans.transplan.vo.TsTransPlanHistoryVO;

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
	public int insertTtsTransPlanHistoryBatch(TtsTransPlanHistoryBatch ttsTransPlanHistoryBatch);
	
	/**
	 * 查限回访跟进信息
	 * @return
	 */
	List<TtsReturnVisitRegistration> queryReturnVisitRegistrations(long batchId);
	
	/**
	 * 新增回访跟进内容
	 * @param caseReturnVisitRegistrationVO
	 * @return
	 */
	int addReturnVisit(TtsReturnVisitRegistration ttsReturnVisitRegistration);

	/**
	 * 根据交易历史批次表id查询该批次交易历史表信息
	 * @param batchId
	 * @return
	 */
	public List<TsTransPlanHistoryVO> queryTtsTransPlanHistorys(TsTransPlanHistoryVO tsTransPlanHistoryVO);
}
