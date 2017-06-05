package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvAduit;
@MyBatisRepository
public interface ToSpvAduitMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvAduit record);

    int insertSelective(ToSpvAduit record);

    ToSpvAduit selectByPrimaryKey(Long pkid);
    
    List<ToSpvAduit> selectByCashFlowApplyId(String cashFlowApplyId);

    int updateByPrimaryKeySelective(ToSpvAduit record);

    int updateByPrimaryKey(ToSpvAduit record);
}