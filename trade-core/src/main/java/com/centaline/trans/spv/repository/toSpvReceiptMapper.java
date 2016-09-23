package com.centaline.trans.spv.repository;

import com.centaline.trans.spv.entity.toSpvReceipt;

public interface toSpvReceiptMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(toSpvReceipt record);

    int insertSelective(toSpvReceipt record);

    toSpvReceipt selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(toSpvReceipt record);

    int updateByPrimaryKey(toSpvReceipt record);
}