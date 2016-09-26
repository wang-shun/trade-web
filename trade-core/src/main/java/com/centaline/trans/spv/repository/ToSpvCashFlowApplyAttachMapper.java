package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvCashFlowApplyAttach;
@MyBatisRepository
public interface ToSpvCashFlowApplyAttachMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvCashFlowApplyAttach record);

    int insertSelective(ToSpvCashFlowApplyAttach record);

    ToSpvCashFlowApplyAttach selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvCashFlowApplyAttach record);

    int updateByPrimaryKey(ToSpvCashFlowApplyAttach record);

	List<ToSpvCashFlowApplyAttach> selectByCashFlowApplyId(Long cashFlowApplyId);
}