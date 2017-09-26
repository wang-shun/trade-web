package com.centaline.trans.common.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.MqOpertationLog;
@MyBatisRepository
public interface MqOpertationLogMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(MqOpertationLog record);

    int insertSelective(MqOpertationLog record);

    MqOpertationLog selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(MqOpertationLog record);

    int updateByPrimaryKey(MqOpertationLog record);
}