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

	List<ToMortLoaner> findToMortLoaner(ToMortLoaner toMortLoaner);
	/**
	 * 根据安全编号和主次银行获取有效的派单信息 (ACCEPTING  ，AUDITING ，COMPLETED) 
	 * @param caseCode
	 * @param isMainLoanBankProcess
	 * @return
	 */
	ToMortLoaner findActiveToMortLoaner(String caseCode,String isMainLoanBankProcess);
	/**
	 * 根据安全编号和主次银行获取派单信息 (主要为了贷款上显示派单状态) 
	 * @param caseCode
	 * @param isMainLoanBankProcess
	 * @return
	 */
	ToMortLoaner findLastToMortLoaner(String caseCode, String isMainLoanBankProcess);

}
