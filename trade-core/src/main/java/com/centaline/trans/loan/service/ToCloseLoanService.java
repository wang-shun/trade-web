package com.centaline.trans.loan.service;

import com.centaline.trans.loan.entity.ToCloseLoan;

public interface ToCloseLoanService {

	public Boolean saveToCloseLoan(ToCloseLoan toCloseLoan);
	
	public ToCloseLoan qureyToCloseLoan(String caseCode);
}
