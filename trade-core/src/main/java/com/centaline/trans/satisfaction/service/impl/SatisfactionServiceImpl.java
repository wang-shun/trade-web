package com.centaline.trans.satisfaction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.satisfaction.entity.ToSatisfaction;
import com.centaline.trans.satisfaction.repository.ToSatisfactionMapper;
import com.centaline.trans.satisfaction.service.SatisfactionService;

@Service
public class SatisfactionServiceImpl implements SatisfactionService {
	
	@Autowired
	private ToSatisfactionMapper toSatisfactionMapper;

	@Override
	public int update(ToSatisfaction toSatisfaction) {
		return toSatisfactionMapper.updateByPrimaryKey(toSatisfaction);
	}
	
	@Override
	public int updateSelective(ToSatisfaction toSatisfaction) {
		return toSatisfactionMapper.updateByPrimaryKeySelective(toSatisfaction);
	}

	@Override
	public int insert(ToSatisfaction toSatisfaction) {
		return toSatisfactionMapper.insert(toSatisfaction);
	}

	@Override
	public List<ToSatisfaction> queryToSatisfactionList() {
		return toSatisfactionMapper.selectAll();
	}

	@Override
	public ToSatisfaction queryToSatisfactionById(Long id) {
		return toSatisfactionMapper.selectByPrimaryKey(id);
	}

}
