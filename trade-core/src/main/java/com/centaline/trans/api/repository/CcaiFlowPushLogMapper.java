package com.centaline.trans.api.repository;

import com.centaline.trans.api.entity.CcaiFlowPushLog;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface CcaiFlowPushLogMapper {
    /**
     * 新增 与CCAI交互日志
     * @param record
     * @return
     */
    int insertSelective(CcaiFlowPushLog record);

    /**
     * 按照条件 统计与CCAI交互的次数
     * 用于判断是否需要与CCAI进行交互
     * @param record
     * @return
     */
    long countLog(CcaiFlowPushLog record);

}