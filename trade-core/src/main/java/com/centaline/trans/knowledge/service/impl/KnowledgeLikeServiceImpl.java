package com.centaline.trans.knowledge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.knowledge.entity.KnowledgeLike;
import com.centaline.trans.knowledge.repository.KnowledgeLikeMapper;
import com.centaline.trans.knowledge.service.KnowledgeLikeService;

@Service
public class KnowledgeLikeServiceImpl implements KnowledgeLikeService {
	@Autowired
	private KnowledgeLikeMapper mapper;

	@Override
	public int insertSelective(KnowledgeLike record) {
		return mapper.insertSelective(record);
	}

	@Override
	public int updateLike(KnowledgeLike record) {
		return mapper.updateLike(record);
	}

	@Override
	public KnowledgeLike selectByKnowCodeAndUser(String nCode, String userId) {
		return mapper.selectByKnowCodeAndUser(nCode, userId);
	}

}
