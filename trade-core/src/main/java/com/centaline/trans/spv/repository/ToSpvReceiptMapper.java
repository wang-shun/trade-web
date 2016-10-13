package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvReceipt;
@MyBatisRepository
public interface ToSpvReceiptMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvReceipt record);

    int insertSelective(ToSpvReceipt record);

    ToSpvReceipt selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvReceipt record);

    int updateByPrimaryKey(ToSpvReceipt record);

	List<ToSpvReceipt> selectByCashFlowId(String cashFlowId);
}