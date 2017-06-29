package com.centaline.trans.product.repository;

import com.centaline.trans.product.entity.TtsProductMaterial;

public interface TtsProductMaterialMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TtsProductMaterial record);

    int insertSelective(TtsProductMaterial record);

    TtsProductMaterial selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TtsProductMaterial record);

    int updateByPrimaryKey(TtsProductMaterial record);
}