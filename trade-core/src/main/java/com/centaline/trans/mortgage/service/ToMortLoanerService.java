package com.centaline.trans.mortgage.service;


import com.centaline.trans.mortgage.entity.ToMortLoaner;



public interface ToMortLoanerService {    
    
    /**
     * 查询接单贷款信息
     * @param caseCode
     * @param loanerStatus
     * @return
     */
	ToMortLoaner findToMortLoanerByCaseCodeAndLoanerStatus(String caseCode,String loanerStatus);


    /**
     * 插入贷款信息
     * @param ToMortLoaner 
     * @return
     */
	void insertByToMortLoaner(ToMortLoaner toMortLoaner);


}
