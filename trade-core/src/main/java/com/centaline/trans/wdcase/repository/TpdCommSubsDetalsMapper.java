package com.centaline.trans.wdcase.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.wdcase.entity.TpdCommSubsDetals;
@MyBatisRepository
public interface TpdCommSubsDetalsMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TpdCommSubsDetals record);

    int insertSelective(TpdCommSubsDetals record);

    TpdCommSubsDetals selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TpdCommSubsDetals record);

    int updateByPrimaryKey(TpdCommSubsDetals record);
}