package com.centaline.trans.spv.repository;

import com.centaline.trans.spv.entity.ToSpvAduit;

public interface ToSpvAduitMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvAduit record);

    int insertSelective(ToSpvAduit record);

    ToSpvAduit selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvAduit record);

    int updateByPrimaryKey(ToSpvAduit record);
}