package com.centaline.trans.cases.repository;

import java.util.List;

import com.centaline.trans.cases.vo.CaseReturnVisitRegistrationVO;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface ReturnVisitRegistrationMapper {
	
	List<CaseReturnVisitRegistrationVO> queryReturnVisitRegistrations(Long historyId);
}
