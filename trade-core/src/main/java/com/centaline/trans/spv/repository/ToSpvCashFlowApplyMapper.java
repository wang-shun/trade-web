package com.centaline.trans.spv.repository;


import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvCashFlowApply;
@MyBatisRepository
public interface ToSpvCashFlowApplyMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvCashFlowApply record);

    int insertSelective(ToSpvCashFlowApply record);

    ToSpvCashFlowApply selectByPrimaryKey(Long pkid);
    
    ToSpvCashFlowApply selectByCashFlowApplyCode(String cashFlowApplyCode);

    int updateByPrimaryKeySelective(ToSpvCashFlowApply record);

    int updateByPrimaryKey(ToSpvCashFlowApply record);
    
    List<ToSpvCashFlowApply> selectBySpvCode(String spvCode);//根据spvCode查询ToSpvCashFlowApply
}