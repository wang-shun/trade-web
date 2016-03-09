package com.centaline.trans.api.repository;

import com.centaline.trans.api.entity.TsApiLog;
import com.centaline.trans.common.MyBatisRepository;


@MyBatisRepository
public interface TsApiLogMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsApiLog record);

    int insertSelective(TsApiLog record);

    TsApiLog selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsApiLog record);

    int updateByPrimaryKey(TsApiLog record);
}