package com.centaline.trans.material.repository;

import com.centaline.trans.material.entity.MmItemBatch;

public interface MmItemBatchMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(MmItemBatch record);

    int insertSelective(MmItemBatch record);

    MmItemBatch selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(MmItemBatch record);

    int updateByPrimaryKey(MmItemBatch record);
}