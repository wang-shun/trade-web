package com.centaline.trans.mgr.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mgr.entity.TsAttendance;

@MyBatisRepository
public interface TsAttendanceMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsAttendance record);

    int insertSelective(TsAttendance record);

    TsAttendance selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsAttendance record);

    int updateByPrimaryKey(TsAttendance record);
}