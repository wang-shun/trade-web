package com.centaline.trans.product.repository;

import com.centaline.trans.product.entity.TtsProdTag;

public interface TtsProdTagMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TtsProdTag record);

    int insertSelective(TtsProdTag record);

    TtsProdTag selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TtsProdTag record);

    int updateByPrimaryKey(TtsProdTag record);
}