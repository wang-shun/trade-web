package com.centaline.trans.income.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.income.entity.TsOverriding;

@MyBatisRepository
public interface TsOverridingMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsOverriding record);

    int insertSelective(TsOverriding record);

    TsOverriding selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsOverriding record);

    int updateByPrimaryKey(TsOverriding record);
}