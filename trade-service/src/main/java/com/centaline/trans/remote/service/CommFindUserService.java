package com.centaline.trans.remote.service;

public interface CommFindUserService {
	/**
	 * 流程通用找人逻辑()
	 * @param jobCode 
	 * @param caseCode
	 * @return 
	 */
	String findUserByCase(String jobCode, String caseCode);
	/**
	 * 流程通用找人逻辑
	 * @param jobCode 
	 * @param caseCode
	 * @param taskDfKey
	 * @return 
	 */
	String findUserBySrv(String jobCode, String caseCode, String taskDfKey);
}
