package com.centaline.trans.remote.service;

public interface TaskDelegateService {
	/**
	 * 根据授权人查询被授权
	 * 
	 * @param owner
	 * @return
	 */
	String getTaskAgent(String owner);
}
