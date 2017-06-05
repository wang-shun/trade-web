package com.centaline.trans.attachment.repository;

import com.centaline.trans.attachment.entity.TsAttachmentReadLog;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface TsAttachmentReadLogMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsAttachmentReadLog record);

    int insertSelective(TsAttachmentReadLog record);

    TsAttachmentReadLog selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsAttachmentReadLog record);

    int updateByPrimaryKey(TsAttachmentReadLog record);
}