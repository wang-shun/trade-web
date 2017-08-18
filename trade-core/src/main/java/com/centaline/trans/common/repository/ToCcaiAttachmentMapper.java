package com.centaline.trans.common.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.ToCcaiAttachment;
@MyBatisRepository
public interface ToCcaiAttachmentMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToCcaiAttachment record);

    int insertSelective(ToCcaiAttachment record);

    ToCcaiAttachment selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToCcaiAttachment record);

    int updateByPrimaryKey(ToCcaiAttachment record);
    /**
     * 根据CCAI CODE 获取已导入的附件列表
     * @param ccaiCode
     * @return
     */
    List<ToCcaiAttachment> findAttachmentsByCcaiCode(String ccaiCode);
    
}