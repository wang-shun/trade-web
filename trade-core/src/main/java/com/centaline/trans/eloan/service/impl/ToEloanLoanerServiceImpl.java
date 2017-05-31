package com.centaline.trans.eloan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.eloan.entity.ToEloanLoaner;
import com.centaline.trans.eloan.repository.ToEloanLoanerMapper;
import com.centaline.trans.eloan.service.ToEloanLoanerService;

@Service
public class ToEloanLoanerServiceImpl implements ToEloanLoanerService
{

    @Autowired
    private ToEloanLoanerMapper toEloanLoanerMapper;

    @Override
    public ToEloanLoaner getToEloanLoanerById(Long pkid)
    {
        return toEloanLoanerMapper.selectByPrimaryKey(pkid);
    }
}
