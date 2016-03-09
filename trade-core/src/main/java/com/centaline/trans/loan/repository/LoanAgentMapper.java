package com.centaline.trans.loan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.loan.entity.LoanAgent;

@MyBatisRepository
public interface LoanAgentMapper {
	LoanAgent selectByPkid(Long pkid);

	int deleteByPkid(Long pkid);

	int insertSelective(LoanAgent loanAgent);

	int updateByPrimaryKeySelective(LoanAgent loanAgent);

	List<LoanAgent> listByPkIdAndCaseCodeSrvCode(Long pkid, String caseCode,
			String srvCode);
	
	//int batchUpdateExportTime(String[] array);
}
