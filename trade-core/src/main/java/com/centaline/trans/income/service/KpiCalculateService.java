package com.centaline.trans.income.service;

public interface KpiCalculateService {

	
	/**
	 * 功能：根据不同的角色去计算 kpi
	 * 作者：zhangxb16
	 * @param roleCode [角色名称]
	 * @param score [满意度评分]
	 * @param phoneRate [电话准确率]
	 * @param financialCount [金融产品]
	 * @param signedCount [组别月签约单数]
	 * @param outflowRate [流失率]
	 */
	public double calculateKpi(String roleCode, double score, double phoneRate, int financialCount, 
			int signedCount, double outflowRate); 
	
}
