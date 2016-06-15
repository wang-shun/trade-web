package com.centaline.trans.mortgage.service;

import com.centaline.trans.mortgage.entity.ToMortgage;

public interface ToMortgageService {

	/**
	 * 保存按揭贷款信息
	 * @param toMortgage
	 */
	ToMortgage saveToMortgage(ToMortgage toMortgage);
	
	/**
	 * 修改按揭贷款信息
	 * @param toMortgage
	 */
	void updateToMortgage(ToMortgage toMortgage);
	
	/**
	 * 根据案件编号查询最终贷款信息
	 * @param caseCode
	 * @return
	 */
	ToMortgage findToMortgageByCaseCode(String caseCode);
	
	/**
	 * 根据案件编号查询最终贷款信息
	 * @param caseCode
	 * @return
	 */
	ToMortgage findToMortgageByCaseCode2(String caseCode);
	
	/**
	 * 根据主键查询按揭贷款信息
	 * @param id
	 * @return
	 */
	ToMortgage findToMortgageById(Long id);

	/**
	 * 保存按揭贷款及补件信息
	 * @param toMortgage
	 */
	void saveToMortgageAndSupDocu(ToMortgage toMortgage);
	
	/**
	 * 根据案件编号查询按揭贷款信息
	 * @param toMortgage
	 * @return
	 */
	ToMortgage findToMortgageByCaseCode(ToMortgage toMortgage);
	
	ToMortgage findToMortgageByMortTypeAndCaseCode(String caseCode,String mortType);
	
	ToMortgage findToSelfLoanMortgage(String caseCode);

	ToMortgage findToMortgageByCondition(ToMortgage toMortgage);
	
	void inActiveMortageByCaseCode(String caseCode);

	ToMortgage findToMortgageByCaseCodeWithAll(ToMortgage toMortgage);

}
