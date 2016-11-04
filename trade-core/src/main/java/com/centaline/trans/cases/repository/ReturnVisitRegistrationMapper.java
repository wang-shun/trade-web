package com.centaline.trans.cases.repository;

import java.util.List;

import com.centaline.trans.cases.vo.CaseReturnVisitRegistrationVO;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface ReturnVisitRegistrationMapper {
	/**
	 * 跟进交易变更历史表id查询回访跟进信息
	 * @param historyId
	 * @return
	 */
	List<CaseReturnVisitRegistrationVO> queryReturnVisitRegistrations(Long historyId);
	/**
	 * 新增回访跟进信息
	 * @param caseReturnVisitRegistrationVO
	 * @return
	 */
	int insertReturnVisitRegistration(CaseReturnVisitRegistrationVO caseReturnVisitRegistrationVO);
}
