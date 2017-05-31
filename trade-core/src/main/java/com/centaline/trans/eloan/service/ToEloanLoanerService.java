package com.centaline.trans.eloan.service;

import com.centaline.trans.eloan.entity.ToEloanLoaner;

public interface ToEloanLoanerService
{

    /**
     * 根据E+金融编号获取E+案件接收信息
     * 
     * @param eLoanCode
     *            金融编号
     * @return E+案件接收信息
     */
    public ToEloanLoaner getToEloanLoanerByELoanCode(String eLoanCode);
}
