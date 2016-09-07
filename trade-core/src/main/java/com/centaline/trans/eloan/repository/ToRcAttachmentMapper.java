package com.centaline.trans.eloan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.ToRcAttachment;
@MyBatisRepository
public interface ToRcAttachmentMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToRcAttachment record);

    int insertSelective(ToRcAttachment record);

    ToRcAttachment selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToRcAttachment record);

    int updateByPrimaryKey(ToRcAttachment record);
}