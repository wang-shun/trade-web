package com.centaline.trans.eloan.service;

import com.centaline.trans.eloan.entity.ToCloseLoan;

public interface ToCloseLoanService {

	public Boolean saveToCloseLoan(ToCloseLoan toCloseLoan);
	
	public ToCloseLoan qureyToCloseLoan(String caseCode);
	/**
	 * 添加贷款流失类型查询
	 * @param caseCode
	 * @return
	 */
	String getLoanLostTypeValue(String caseCode);
}
