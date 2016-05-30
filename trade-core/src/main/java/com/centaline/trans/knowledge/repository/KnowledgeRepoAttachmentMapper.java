package com.centaline.trans.knowledge.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.knowledge.entity.KnowledgeRepoAttachment;

/**
 * 知识库附件表 Mapper
 * @author yumin1
 *
 */
@MyBatisRepository
public interface KnowledgeRepoAttachmentMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(KnowledgeRepoAttachment record);

    int insertSelective(KnowledgeRepoAttachment record);

    KnowledgeRepoAttachment selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(KnowledgeRepoAttachment record);

    int updateByPrimaryKey(KnowledgeRepoAttachment record);
    
    /**
     * 根据知识编码查询附件表
     * @param knowledgeCode  知识编码
     * @return 查询的集合
     * @author yumin1
     */
    List<KnowledgeRepoAttachment> selectByKnowledgeCode(String knowledgeCode);
    
    /**
     * 根据知识编码删除附件表
     * @param knowledgeCode 知识编码
     * @return  受影响的行数
     * @author yumin1
     */
    int deleteByKnowledgeCode(String knowledgeCode);
} 