package com.centaline.trans.spv.repository;

import com.centaline.trans.spv.entity.toSpvCashFlowApply;

public interface toSpvCashFlowApplyMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(toSpvCashFlowApply record);

    int insertSelective(toSpvCashFlowApply record);

    toSpvCashFlowApply selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(toSpvCashFlowApply record);

    int updateByPrimaryKey(toSpvCashFlowApply record);
}