package com.centaline.trans.loan.service;

import com.centaline.trans.loan.entity.LoanAgent;
import com.centaline.trans.loan.entity.LoanStatusChange;

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

}
