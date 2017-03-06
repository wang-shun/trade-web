package com.centaline.trans.cases.service;


import javax.servlet.http.HttpServletRequest;
import com.centaline.trans.cases.vo.CaseMergeVo;


public interface CaseMergeService {	
	
	void saveCaseInfo(HttpServletRequest request,CaseMergeVo caseMergeVo,String caseCode);

}
