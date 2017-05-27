package com.centaline.trans.product.repository;

import com.centaline.trans.product.entity.TtsProdLoanPayment;

public interface TtsProdLoanPaymentMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TtsProdLoanPayment record);

    int insertSelective(TtsProdLoanPayment record);

    TtsProdLoanPayment selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TtsProdLoanPayment record);

    int updateByPrimaryKey(TtsProdLoanPayment record);
}