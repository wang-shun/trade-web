package com.centaline.trans.cases.repository;

import com.centaline.trans.cases.entity.ToCaseMerge;
import com.centaline.trans.common.MyBatisRepository;
@MyBatisRepository
public interface ToCaseMergeMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToCaseMerge record);

    int insertSelective(ToCaseMerge record);

    ToCaseMerge selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToCaseMerge record);

    int updateByPrimaryKey(ToCaseMerge record);
}