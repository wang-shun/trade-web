package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToPrTransferLog;
@MyBatisRepository
public interface ToPrTransferLogMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToPrTransferLog record);

    int insertSelective(ToPrTransferLog record);

    ToPrTransferLog selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToPrTransferLog record);

    int updateByPrimaryKey(ToPrTransferLog record);
}