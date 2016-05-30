package com.centaline.trans.task.service;

import java.util.List;

import com.centaline.trans.task.entity.ToApproveRecord;

public interface ToApproveRecordService {

	public ToApproveRecord queryToApproveRecord(ToApproveRecord toApproveRecord);
	
	public void saveToApproveRecord(ToApproveRecord toApproveRecord);
	
	List<String> findOperatorList(ToApproveRecord toApproveRecord);
	
	void insertToApproveRecord(ToApproveRecord toApproveRecord);
}
