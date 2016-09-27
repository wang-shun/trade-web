package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvCashFlow;
@MyBatisRepository
public interface ToSpvCashFlowMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvCashFlow record);

    int insertSelective(ToSpvCashFlow record);

    ToSpvCashFlow selectByPrimaryKey(Long pkid);
    
    List<ToSpvCashFlow> selectByCashFlowApplyId(String cashFlowApplyId);

    int updateByPrimaryKeySelective(ToSpvCashFlow record);

    int updateByPrimaryKey(ToSpvCashFlow record);
}