package com.centaline.trans.remote.service;

public interface CommFindUserService {
	/**
	 * ����ͨ�������߼�()
	 * @param jobCode 
	 * @param caseCode
	 * @return 
	 */
	String findUserByCase(String jobCode, String caseCode);
	/**
	 * ����ͨ�������߼�
	 * @param jobCode 
	 * @param caseCode
	 * @param taskDfKey
	 * @return 
	 */
	String findUserBySrv(String jobCode, String caseCode, String taskDfKey);
}
