package com.centaline.trans.product.repository;

import com.centaline.trans.product.entity.TtsPrdcFlow;

public interface TtsPrdcFlowMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TtsPrdcFlow record);

    int insertSelective(TtsPrdcFlow record);

    TtsPrdcFlow selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TtsPrdcFlow record);

    int updateByPrimaryKey(TtsPrdcFlow record);
}