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
	public int updateToSatisfaction(ToSatisfaction toSatisfaction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertToSatisfaction(ToSatisfaction toSatisfaction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ToSatisfaction> queryToSatisfactionList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ToSatisfaction queryToSatisfactionById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
