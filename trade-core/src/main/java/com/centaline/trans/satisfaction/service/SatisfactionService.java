package com.centaline.trans.satisfaction.service;

import java.util.List;

import com.centaline.trans.satisfaction.entity.ToSatisfaction;

public interface SatisfactionService {

	int updateToSatisfaction(ToSatisfaction toSatisfaction);
	
	int insertToSatisfaction(ToSatisfaction toSatisfaction);
	
	List<ToSatisfaction> queryToSatisfactionList();
	
	ToSatisfaction queryToSatisfactionById(int id);

}
