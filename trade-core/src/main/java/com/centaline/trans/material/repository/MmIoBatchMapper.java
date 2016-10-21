package com.centaline.trans.material.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.material.entity.MmIoBatch;
@MyBatisRepository
public interface MmIoBatchMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(MmIoBatch record);

    int insertSelective(MmIoBatch record);

    MmIoBatch selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(MmIoBatch record);

    int updateByPrimaryKey(MmIoBatch record);
}