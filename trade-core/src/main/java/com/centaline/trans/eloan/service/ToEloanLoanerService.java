package com.centaline.trans.eloan.service;

import com.centaline.trans.eloan.entity.ToEloanLoaner;

public interface ToEloanLoanerService
{

    /**
     * 根据主键id获取对应的E+接收案件信息表
     * 
     * @param pkid
     *            主键id
     * @return E+接收案件信息
     */
    public ToEloanLoaner getToEloanLoanerById(Long pkid);
}
