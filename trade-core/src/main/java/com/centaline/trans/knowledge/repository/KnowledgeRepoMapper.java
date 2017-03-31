package com.centaline.trans.knowledge.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.knowledge.entity.KnowledgeRepo;
/**
 * 知识库  Mapper 
 * @author yumin1
 *
 */
@MyBatisRepository
public interface KnowledgeRepoMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(KnowledgeRepo record);

    int insertSelective(KnowledgeRepo record);

    KnowledgeRepo selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(KnowledgeRepo record);

    int updateByPrimaryKeyWithBLOBs(KnowledgeRepo record);

    int updateByPrimaryKey(KnowledgeRepo record);

    /**
     * 更改是否置顶
     * @param knowledgeRepo
     * @return
     */
	int updateIsTop(KnowledgeRepo knowledgeRepo);

	/**
	 * 更改是否推荐
	 * @param knowledgeRepo
	 * @return
	 */
	int updateIsRecommand(KnowledgeRepo knowledgeRepo);
}