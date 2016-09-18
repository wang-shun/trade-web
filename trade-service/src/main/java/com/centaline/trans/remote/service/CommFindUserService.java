package com.centaline.trans.remote.service;

public interface CommFindUserService {
	String findUserByCase(String jobCode, String caseCode);

	String findUserBySrv(String jobCode, String caseCode, String taskDfKey);
}
