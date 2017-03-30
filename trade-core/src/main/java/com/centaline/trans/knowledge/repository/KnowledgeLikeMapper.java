package com.centaline.trans.knowledge.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.knowledge.entity.KnowledgeLike;

@MyBatisRepository
public interface KnowledgeLikeMapper {
	int insertSelective(KnowledgeLike record);

	int updateLike(KnowledgeLike record);

	KnowledgeLike selectByKnowCodeAndUser(String nCode, String userId);
}
