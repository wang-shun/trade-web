package com.centaline.trans.cases.repository;

import com.centaline.trans.cases.entity.ToChangeRecord;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface ToChangeRecordMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToChangeRecord record);

    int insertSelective(ToChangeRecord record);

    ToChangeRecord selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToChangeRecord record);

    int updateByPrimaryKey(ToChangeRecord record);
}