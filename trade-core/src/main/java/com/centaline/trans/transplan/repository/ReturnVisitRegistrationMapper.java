package com.centaline.trans.transplan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.transplan.vo.CaseReturnVisitRegistrationVO;

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
