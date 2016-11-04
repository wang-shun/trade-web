package com.centaline.trans.engine.service;

import java.util.Map;

/**
 * 流程通用找人逻辑接口
 * 
 * @author Jimmy
 * 
 */
public interface FindUserLogic {
	/**
	 * 流程找人逻辑
	 * 
	 * @param groupId
	 * @param caseowner
	 * @param serviceMap
	 *            ｛serviceCode为key,userId为value｝
	 * @param taskDefinitionKey
	 * @return
	 */
	String findWorkFlowUser(String groupId, String caseowner,
			Map<String, String> serviceMap, String taskDefinitionKey,
			String processInstanceId);
}
