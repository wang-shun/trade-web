package com.centaline.trans.wdcase.repository;

import com.centaline.trans.wdcase.entity.TpdCommSubs;

public interface TpdCommSubsMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TpdCommSubs record);

    int insertSelective(TpdCommSubs record);

    TpdCommSubs selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TpdCommSubs record);

    int updateByPrimaryKey(TpdCommSubs record);
}