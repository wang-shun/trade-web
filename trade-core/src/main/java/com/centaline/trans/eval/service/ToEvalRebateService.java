package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.task.entity.ToApproveRecord;

public interface ToEvalRebateService {
	/**
	 * 新增评估返利申请
	 * @param record
	 * @return
	 */
	int insertSelective(ToEvalRebate record);

	/**
	 * 更新评估返利报告信息
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(ToEvalRebate record);

	/**
	 * 根据案件编号 获取返利报告信息
	 * @param caseCode
	 * @return
	 */
	ToEvalRebate findToEvalRebateByCaseCode(String caseCode);

	/**
	 * 根据主键ID获取评估返利信息
	 * @param pkid
	 * @return
	 */
	ToEvalRebate selectByPrimaryKey(Long pkid);

	/**
	 * 内勤确认评估返利
	 * @param rebate 评估返利信息
	 * @param record 审批信息
	 * @param approve 是否通过
	 */
	void assistantApprove(ToEvalRebate rebate, ToApproveRecord record,boolean approve);

}
