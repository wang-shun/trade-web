package com.centaline.trans.product.repository;

import com.centaline.trans.product.entity.TtsProdSpv;

public interface TtsProdSpvMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TtsProdSpv record);

    int insertSelective(TtsProdSpv record);

    TtsProdSpv selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TtsProdSpv record);

    int updateByPrimaryKey(TtsProdSpv record);
}