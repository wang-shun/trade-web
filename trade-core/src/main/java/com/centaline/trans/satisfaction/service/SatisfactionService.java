package com.centaline.trans.satisfaction.service;

import java.util.Date;
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
	void handleAfterSign(String caseCode, String signer, Date signTime, Date guohuTime, String type);
	
	//过户申请完成操作
	void handleAfterGuohu(String caseCode, String guohuer, Date guohuTime);

	int insertSelective(ToSatisfaction toSatisfaction);

	void dispatch(String caseCodes, String userId);

	void signPass(ToSatisfaction toSatisfaction, String taskId, String instCode);

	void signReject(ToSatisfaction toSatisfaction, String taskId, String instCode);

	void signFollow(ToSatisfaction toSatisfaction, String taskId, String instCode);

	void guohuPass(ToSatisfaction toSatisfaction, String taskId, String instCode);

	void guohuReject(ToSatisfaction toSatisfaction, String taskId, String instCode);

	void guohuFollow(ToSatisfaction toSatisfaction, String taskId, String instCode);

	void initSatisList();

}
