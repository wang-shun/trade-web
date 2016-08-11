package com.centaline.trans.eloan.service;

import java.util.List;
import java.util.Map;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.eloan.entity.ToEloanCase;

public interface ToEloanCaseService {
	
	ToEloanCase getToEloanCaseByPkId(Long pkid);
	
    void saveEloanApply(SessionUser user,ToEloanCase tEloanCase);
    
    int updateEloanApply(SessionUser user, ToEloanCase tEloanCase);
    
    void saveEloanSign(String taskId,ToEloanCase tEloanCase);
    
    List<ToEloanCase> getToEloanCaseListByProperty(ToEloanCase tEloanCase);
    
    //void eloanProcessConfirm(String taskId, String approved,ToEloanCase toEloanCase);
    
    void eloanProcessConfirm(String taskId,Map<String,Object> map,ToEloanCase toEloanCase,boolean isUpdate);
}
