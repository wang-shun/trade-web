package com.centaline.trans.eloan.repository;

import com.centaline.trans.eloan.entity.ToEloanLoaner;

public interface ToEloanLoanerMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEloanLoaner record);

    int insertSelective(ToEloanLoaner record);

    ToEloanLoaner selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEloanLoaner record);

    int updateByPrimaryKey(ToEloanLoaner record);
}