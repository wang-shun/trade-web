package com.centaline.trans.satisfaction.service;

import java.util.List;

import com.centaline.trans.satisfaction.entity.ToSatisfaction;

public interface SatisfactionService {

	int update(ToSatisfaction toSatisfaction);
	
	int updateSelective(ToSatisfaction toSatisfaction);
	
	int insert(ToSatisfaction toSatisfaction);
	
	List<ToSatisfaction> queryToSatisfactionList();
	
	ToSatisfaction queryToSatisfactionById(Long id);

}
