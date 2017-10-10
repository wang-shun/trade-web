package com.centaline.trans.eval.repository;

import com.centaline.trans.eval.entity.ToEvaCommissionChange;

public interface ToEvaCommissionChangeMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaCommissionChange record);

    int insertSelective(ToEvaCommissionChange record);

    ToEvaCommissionChange selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaCommissionChange record);

    int updateByPrimaryKey(ToEvaCommissionChange record);
}