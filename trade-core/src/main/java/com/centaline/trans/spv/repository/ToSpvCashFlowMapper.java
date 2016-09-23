package com.centaline.trans.spv.repository;

import com.centaline.trans.spv.entity.ToSpvCashFlow;

public interface ToSpvCashFlowMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvCashFlow record);

    int insertSelective(ToSpvCashFlow record);

    ToSpvCashFlow selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvCashFlow record);

    int updateByPrimaryKey(ToSpvCashFlow record);
}