package com.centaline.trans.spv.repository;

import com.centaline.trans.spv.entity.ToSpvCloseApply;

public interface ToSpvCloseApplyMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvCloseApply record);

    int insertSelective(ToSpvCloseApply record);

    ToSpvCloseApply selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvCloseApply record);

    int updateByPrimaryKey(ToSpvCloseApply record);
}