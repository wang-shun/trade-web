package com.centaline.trans.api.service;

import com.centaline.trans.api.vo.ApiCommissionAssign;



/**
 * @author xiefei1
 * @since 2017年10月25日 下午9:13:19 
 * @description 根据ccaiCode获取案件的调佣对象和调佣金额的详细信息
 */
public interface CommissionAssignApiService {
	/**
	 * 
	 * @since:2017年10月25日 下午9:39:28
	 * @description:获取要调佣的成交报告的分成信息
	 * @author:xiefei1
	 * @param ccaiCode
	 * @return
	 */
	ApiCommissionAssign getApiCommissionAssign(String ccaiCode);

}
