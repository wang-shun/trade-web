package com.centaline.trans.wdcase.repository;

import com.centaline.trans.wdcase.entity.TpdPayment;

public interface TpdPaymentMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TpdPayment record);

    int insertSelective(TpdPayment record);

    TpdPayment selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TpdPayment record);

    int updateByPrimaryKey(TpdPayment record);
}