package com.centaline.trans.log.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.log.entity.TsAttachmentReadLog;

@MyBatisRepository
public interface TsAttachmentReadLogMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsAttachmentReadLog record);

    int insertSelective(TsAttachmentReadLog record);

    TsAttachmentReadLog selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsAttachmentReadLog record);

    int updateByPrimaryKey(TsAttachmentReadLog record);
}