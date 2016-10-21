package com.centaline.trans.material.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.material.entity.MmItemBatch;
@MyBatisRepository
public interface MmItemBatchMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(MmItemBatch record);

    int insertSelective(MmItemBatch record);

    MmItemBatch selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(MmItemBatch record);

    int updateByPrimaryKey(MmItemBatch record);
    
    List<MmItemBatch> queryMmItemBatchList(Long itemId);
}