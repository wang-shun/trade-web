package com.centaline.trans.product.repository;

import com.centaline.trans.product.entity.TtsProdLoan;

public interface TtsProdLoanMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TtsProdLoan record);

    int insertSelective(TtsProdLoan record);

    TtsProdLoan selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TtsProdLoan record);

    int updateByPrimaryKey(TtsProdLoan record);
}