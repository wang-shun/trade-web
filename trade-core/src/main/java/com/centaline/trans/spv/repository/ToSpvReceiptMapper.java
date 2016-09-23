package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.spv.entity.ToSpvReceipt;

public interface ToSpvReceiptMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvReceipt record);

    int insertSelective(ToSpvReceipt record);

    ToSpvReceipt selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvReceipt record);

    int updateByPrimaryKey(ToSpvReceipt record);

	List<ToSpvReceipt> selectByCashFlowId(Long cashFlowId);
}