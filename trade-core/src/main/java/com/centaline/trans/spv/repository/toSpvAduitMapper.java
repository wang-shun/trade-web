package com.centaline.trans.spv.repository;

import com.centaline.trans.spv.entity.toSpvAduit;

public interface toSpvAduitMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(toSpvAduit record);

    int insertSelective(toSpvAduit record);

    toSpvAduit selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(toSpvAduit record);

    int updateByPrimaryKey(toSpvAduit record);
}