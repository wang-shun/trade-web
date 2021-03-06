package com.centaline.trans.cases.service;

import java.util.List;

import com.centaline.trans.cases.vo.CaseReturnVisitRegistrationVO;

/**
 * 
 * @author zhoujp7
 *
 */
public interface ToTradeChangedCaseService {
	
	/**
	 * 查限回访跟进信息
	 * @return
	 */
	List<CaseReturnVisitRegistrationVO> queryReturnVisitRegistrations(Long historyId);
	
	/**
	 * 新增回访跟进内容
	 * @param caseReturnVisitRegistrationVO
	 * @return
	 */
	int addReturnVisit(CaseReturnVisitRegistrationVO caseReturnVisitRegistrationVO);
	
}
