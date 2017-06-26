package com.centaline.trans.product.repository;

import com.centaline.trans.product.entity.TtsProductCategory;

public interface TtsProductCategoryMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TtsProductCategory record);

    int insertSelective(TtsProductCategory record);

    TtsProductCategory selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TtsProductCategory record);

    int updateByPrimaryKey(TtsProductCategory record);
}