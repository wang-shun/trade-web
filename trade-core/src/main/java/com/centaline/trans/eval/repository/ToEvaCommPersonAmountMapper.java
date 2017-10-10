package com.centaline.trans.eval.repository;

import com.centaline.trans.eval.entity.ToEvaCommPersonAmount;

public interface ToEvaCommPersonAmountMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaCommPersonAmount record);

    int insertSelective(ToEvaCommPersonAmount record);

    ToEvaCommPersonAmount selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaCommPersonAmount record);

    int updateByPrimaryKey(ToEvaCommPersonAmount record);
}