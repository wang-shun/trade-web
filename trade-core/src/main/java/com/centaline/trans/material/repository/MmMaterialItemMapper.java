package com.centaline.trans.material.repository;

import com.centaline.trans.material.entity.MmMaterialItem;

public interface MmMaterialItemMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(MmMaterialItem record);

    int insertSelective(MmMaterialItem record);

    MmMaterialItem selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(MmMaterialItem record);

    int updateByPrimaryKey(MmMaterialItem record);
}