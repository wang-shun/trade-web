package com.centaline.trans.cases.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.centaline.trans.cases.repository.ReturnVisitRegistrationMapper;
import com.centaline.trans.cases.service.ToTradeChangedCaseService;
import com.centaline.trans.cases.vo.CaseReturnVisitRegistrationVO;
@Service
public class ToTradeChangedCaseServiceImpl implements ToTradeChangedCaseService {

	@Resource 
	ReturnVisitRegistrationMapper returnVisitRegistrationMapper;
	
	@Override
	public List<CaseReturnVisitRegistrationVO> queryReturnVisitRegistrations(Long historyId) {
		return returnVisitRegistrationMapper.queryReturnVisitRegistrations(historyId);
	}

	@Override
	public int addReturnVisit(CaseReturnVisitRegistrationVO caseReturnVisitRegistrationVO) {
		return returnVisitRegistrationMapper.insertReturnVisitRegistration(caseReturnVisitRegistrationVO);
	}

}
