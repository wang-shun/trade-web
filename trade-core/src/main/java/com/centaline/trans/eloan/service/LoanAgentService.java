package com.centaline.trans.eloan.service;

import java.util.List;

import com.centaline.trans.eloan.entity.LoanAgent;
import com.centaline.trans.eloan.entity.LoanStatusChange;

public interface LoanAgentService {
	LoanAgent view(Long pkid);

	void add(LoanAgent loanAgent);

	void update(LoanAgent loanAgent);
	
	void updateLoanAgent(LoanAgent loanAgent);

	/***
	 * 
	 *  @param loanAgent
	 */
	void updateStatusLoanAgent(LoanAgent loanAgent,LoanStatusChange loanStatusChange);

	void doDelete(LoanAgent loanAgent);
	
	int batchUpdateExportTime(String[] array);
	
	List<LoanAgent> selectByCaseCode(String caseCode);

}
