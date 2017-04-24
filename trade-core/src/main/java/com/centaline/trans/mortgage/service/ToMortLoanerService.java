package com.centaline.trans.mortgage.service;

import java.util.List;

import com.centaline.trans.mortgage.entity.ToMortLoaner;

public interface ToMortLoanerService {

	/**
	 * 查询接单贷款信息
	 * 
	 * @param caseCode
	 * @param loanerStatus
	 * @return
	 */
	ToMortLoaner findToMortLoanerByCaseCodeAndLoanerStatus(String caseCode,
			String loanerStatus);

	/**
	 * 信贷员驳回、银行驳回信息查询
	 * 
	 * @param caseCode
	 * @return
	 */
	ToMortLoaner findToMortLoanerByCaseCode(String caseCode);

	/**
	 * 插入派单贷款信息
	 * 
	 * @param ToMortLoaner
	 * @return
	 */
	void insertByToMortLoaner(ToMortLoaner toMortLoaner);

	/**
	 * 更新派单贷款信息
	 * 
	 * @param ToMortLoaner
	 * @return
	 */
	void updateByPrimaryKeySelective(ToMortLoaner toMortLoaner);

	/**
	 * 根据按揭信息id获取按揭接收记录信息
	 * 
	 * @param id
	 *            主键信息id
	 * @return 按揭接收记录信息
	 */
	ToMortLoaner getToMortLoanerById(Long id);

	List<ToMortLoaner> findToMortLoanerByCaseCodeAndIsMainBank(String caseCode,
			String isMainLoanBankProcess);

	ToMortLoaner findToMortLoaner(ToMortLoaner toMortLoaner);

}
