package com.centaline.trans.task.service;

import com.centaline.trans.task.entity.ToApproveRecord;

import java.util.List;

public interface ToApproveRecordService {
	
	public ToApproveRecord queryToApproveRecordForSpvApply(ToApproveRecord toApproveRecord);
	
	public ToApproveRecord queryToApproveRecord(ToApproveRecord toApproveRecord);
	
	public void saveToApproveRecord(ToApproveRecord toApproveRecord);
	
	List<String> findOperatorList(ToApproveRecord toApproveRecord);
	
	void insertToApproveRecord(ToApproveRecord toApproveRecord);

	/**
	 * 根据案件编号和approve_type删除审批记录
	 * @param caseCode
	 * @param approveType
	 * @author yinchao 2017-10-18
	 */
	void deleteByCaseCodeAndType(String caseCode,String approveType);
}
