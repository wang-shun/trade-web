package com.centaline.trans.apilog.repository;

import com.centaline.trans.apilog.entity.TsApiLog;
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