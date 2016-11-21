package com.centaline.trans.apilog.service;


/**
 * 用于记录接口调用日志
 * @author linjiarong
 *
 */
public interface ApiLogService {
	
	public void apiLog(String module, String apiUrl, String params, String result, String status, String errMsg);
	
}
