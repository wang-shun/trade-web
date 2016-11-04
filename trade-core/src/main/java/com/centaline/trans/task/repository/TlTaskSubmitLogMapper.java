package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.TlTaskSubmitLog;
@MyBatisRepository
public interface TlTaskSubmitLogMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TlTaskSubmitLog record);

    int insertSelective(TlTaskSubmitLog record);

    TlTaskSubmitLog selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TlTaskSubmitLog record);

    int updateByPrimaryKey(TlTaskSubmitLog record);
}