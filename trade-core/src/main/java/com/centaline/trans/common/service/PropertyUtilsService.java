package com.centaline.trans.common.service;

import java.util.Map;

public interface PropertyUtilsService {
	/**
	 * 从系统参数配置中获取相应流程定义
	 * 默认组织ID为当前登录人的服务组织ID
	 * @param process 流程定义参数编码
	 * @return
	 */
	String getProcessDfId(String process);

	/**
	 * 从系统参数配置中获取相应流程定义
	 * 主要提供给后台自动调用时，获取流程定义使用
	 * @param process 流程定义参数编码
	 * @param orgId 所属组织的参数
	 * @return
	 */
	String getProcessDfId(String process,String orgId);

	Map<String, Object> getProcessDefVals(String process);
}
