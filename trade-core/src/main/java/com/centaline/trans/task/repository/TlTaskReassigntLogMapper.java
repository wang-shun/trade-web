package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.TlTaskReassigntLog;
@MyBatisRepository
public interface TlTaskReassigntLogMapper {
    int deleteByPrimaryKey(Integer pkid);

    int insert(TlTaskReassigntLog record);

    int insertSelective(TlTaskReassigntLog record);

    TlTaskReassigntLog selectByPrimaryKey(Integer pkid);

    int updateByPrimaryKeySelective(TlTaskReassigntLog record);

    int updateByPrimaryKey(TlTaskReassigntLog record);
}