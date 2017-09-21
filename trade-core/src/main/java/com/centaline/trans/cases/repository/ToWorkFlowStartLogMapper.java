package com.centaline.trans.cases.repository;

import com.centaline.trans.cases.entity.ToWorkFlowStartLog;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface ToWorkFlowStartLogMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToWorkFlowStartLog record);

    int insertSelective(ToWorkFlowStartLog record);

    ToWorkFlowStartLog selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToWorkFlowStartLog record);

    int updateByPrimaryKey(ToWorkFlowStartLog record);
}