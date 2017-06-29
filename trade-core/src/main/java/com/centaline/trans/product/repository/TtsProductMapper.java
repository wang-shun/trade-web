package com.centaline.trans.product.repository;

import com.centaline.trans.product.entity.TtsProduct;

public interface TtsProductMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TtsProduct record);

    int insertSelective(TtsProduct record);

    TtsProduct selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TtsProduct record);

    int updateByPrimaryKey(TtsProduct record);
}