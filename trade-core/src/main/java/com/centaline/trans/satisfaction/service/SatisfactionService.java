package com.centaline.trans.satisfaction.service;

import java.util.List;

import com.centaline.trans.satisfaction.entity.ToSatisfaction;

public interface SatisfactionService {

	int update(ToSatisfaction toSatisfaction);
	
	int updateSelective(ToSatisfaction toSatisfaction);
	
	int insert(ToSatisfaction toSatisfaction);
	
	List<ToSatisfaction> queryToSatisfactionList();
	
	ToSatisfaction queryToSatisfactionById(Long id);
	
	ToSatisfaction queryToSatisfactionByCaseCode(String caseCode);
	
	//签约完成操作
	void handleAfterSign(String caseCode, String signer);
	
	//过户审批通过操作
	void handleAfterGuohuApprove(String caseCode, String guohuer);
}
