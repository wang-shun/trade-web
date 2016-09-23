package com.centaline.trans.spv.repository;

import com.centaline.trans.spv.entity.toSpvCashFlowApplyAttach;

public interface toSpvCashFlowApplyAttachMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(toSpvCashFlowApplyAttach record);

    int insertSelective(toSpvCashFlowApplyAttach record);

    toSpvCashFlowApplyAttach selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(toSpvCashFlowApplyAttach record);

    int updateByPrimaryKey(toSpvCashFlowApplyAttach record);
}