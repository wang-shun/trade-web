package com.centaline.trans.knowledge.service;

import com.centaline.trans.knowledge.entity.KnowledgeLike;

public interface KnowledgeLikeService {
	int insertSelective(KnowledgeLike record);

	int updateLike(KnowledgeLike record);

	KnowledgeLike selectByKnowCodeAndUser(String ncode, String userId);
}
