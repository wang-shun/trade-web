package com.centaline.trans.spv.repository;

import com.centaline.trans.spv.entity.ToSpvCashFlowApply;

public interface ToSpvCashFlowApplyMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvCashFlowApply record);

    int insertSelective(ToSpvCashFlowApply record);

    ToSpvCashFlowApply selectByPrimaryKey(Long pkid);
    
    ToSpvCashFlowApply selectByCashFlowApplyCode(String cashFlowApplyCode);

    int updateByPrimaryKeySelective(ToSpvCashFlowApply record);

    int updateByPrimaryKey(ToSpvCashFlowApply record);
}