package com.centaline.trans.eloan.service;

import java.util.List;
import java.util.Map;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.eloan.entity.ToEloanRel;

public interface ToEloanRelService {
	int saveEloanRelease(String taskId ,List<ToEloanRel> toEloanRelList,Map map,String isRelFinish);
	
	List<ToEloanRel> getEloanRelByEloanCode(String eloanCode);
	
	int saveEloanReleaseConfirm(String taskId,String approved,String eloanCode,SessionUser user,Map map,String processInstanceId);

	void updateEloanRelByEloanCodeForModify(List<ToEloanRel> eloanRelList);

	List<ToEloanRel> getEloanRelByEloanCodeAndConfirmStatus(String eloanCode);

}
