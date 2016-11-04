package com.centaline.trans.remote.service;

public interface AutoPlanService {
	/**
	 *自动生成任务计划
	 * @param taskDfkey 流程定义
	 * @param caseCode  案件编号
	 */
	void autoGenerateTaskPlan(String taskDfkey, String caseCode);
}
