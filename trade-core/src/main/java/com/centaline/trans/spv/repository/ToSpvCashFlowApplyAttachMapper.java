package com.centaline.trans.spv.repository;

import com.centaline.trans.spv.entity.ToSpvCashFlowApplyAttach;

public interface ToSpvCashFlowApplyAttachMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvCashFlowApplyAttach record);

    int insertSelective(ToSpvCashFlowApplyAttach record);

    ToSpvCashFlowApplyAttach selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvCashFlowApplyAttach record);

    int updateByPrimaryKey(ToSpvCashFlowApplyAttach record);
}