package com.centaline.trans.task.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToApproveRecord;

@MyBatisRepository
public interface ToApproveRecordMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToApproveRecord record);

    int insertSelective(ToApproveRecord record);

    ToApproveRecord selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToApproveRecord record);

    int updateByPrimaryKeyWithBLOBs(ToApproveRecord record);

    int updateByPrimaryKey(ToApproveRecord record);
    
    ToApproveRecord findApproveRecordByRecord(ToApproveRecord record);
    
    List<String> findApproveRecordByList(ToApproveRecord record);
}