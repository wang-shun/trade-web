package com.centaline.trans.transplan.service;

import java.util.List;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.task.entity.ToTransPlanOrToPropertyInfo;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.entity.TsTaskPlanSet;
import com.centaline.trans.transplan.entity.TsTransPlanHistory;
import com.centaline.trans.transplan.entity.TtsReturnVisitRegistration;
import com.centaline.trans.transplan.entity.TtsTransPlanHistoryBatch;
import com.centaline.trans.transplan.vo.TransPlanVO;
import com.centaline.trans.transplan.vo.TsTransPlanHistoryVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 交易计划操作service类
 * @author zhoujp7
 *
 */
public interface TransplanServiceFacade {

	/**
	 * 流程重启或重置操作
	 */
	public void processRestartOrResetOperate(String caseCode,String changeReason);
	
	/**
	 * 新增交易计划变更历史批次表
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
	/**
	 * 插入交易计划信息
	 * @param record
	 * @return
	 */
	int insertSelective(ToTransPlan record);
	/**
	 * 根据caseCode查询交易计划信息
	 * @param caseCode
	 * @return
	 */
	List<ToTransPlan> queryPlansByCaseCode(String caseCode);
	/**
	 * 根据pkid查询交易计划信息
	 * @param pkid
	 * @return
	 */
	ToTransPlan selectByPrimaryKey(Long pkid);
	/**
	 * 交易计划更新
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(ToTransPlan record);
	public Boolean updateTransPlan(ToTransPlan toTransPlan);
	public ToTransPlan findTransPlan(ToTransPlan toTransPlan);
	/**
	 * 删除交易计划
	 * @param caseCode
	 * @return
	 */
	int deleteTransPlansByCaseCode(String caseCode);
	public TransPlanVO findTransPlanByCaseCode(String caseCode);
	/**
	 * 获取到待办事项
	 * @return
	 */
	public List<ToTransPlanOrToPropertyInfo> getToTransPlanByUserId(String leadingProcessId);
	public boolean saveToTransPlan(TransPlanVO transPlanVO);
	
	/**
	 * 插入交易计划变更历史信息
	 * @param record
	 * @return
	 */
    int insertTsTransPlanHistorySelective(TsTransPlanHistory record);
    List<TransPlanVO> getTransPlanVOList(TransPlanVO transPlanVO);
    /**TsTaskPlanSet*/
    /**
     * 根据环节编码查询交易计划延迟天数
     * @param tsakDfkey
     * @return
     */
	public TsTaskPlanSet getAutoTsTaskPlanSetByPartCode(String tsakDfkey);
	int getTsTaskPlanSetCountByProperty(TsTaskPlanSet tsTaskPlanSet);
	int addTsTaskPlanSet(TsTaskPlanSet tsTaskPlanSet);
	int updateByPrimaryKeySelective(TsTaskPlanSet tsTaskPlanSet);
	int deleteByPrimaryKey(Long pkid);

	/**
	 * 获取交易计划变更历史记录的审核状态 by wbzhouht
	 */
	TsTransPlanHistory findTransPlanHistoryByCaseCode(TsTransPlanHistory tsTransPlanHistory);

	/**
	 * 通过交易历史的环节编码和案件编号查询交易计划的PKID
	 */
	ToTransPlan findTransPlanPKIDBycasecodeAndPartCode(TsTransPlanHistory tsTransPlanHistory);

	/**
	 * 修改交易计划变更历史记录状态
	 */
	int updateTransPlanHistoryByPKID(TsTransPlanHistory tsTransPlanHistory);

	/**
	 * 交易计划提交
	 * @param request
	 * @param transPlanVO
	 * @return
	 */
	Boolean submitTransPlan(HttpServletRequest request,
							TransPlanVO transPlanVO)throws Exception;

	/**
	 * 交易计划变更审核
	 * @return
	 */
	AjaxResponse<?> submitTranPlanAppver(String[]pkids, String[] newDates, String[] partCodes, Boolean audit, TransPlanVO transPlanVO);
}
