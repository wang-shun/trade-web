package com.centaline.trans.task.service;

import java.util.List;
import java.util.Map;

import com.centaline.trans.task.entity.ToApproveRecord;

public interface LoanlostApproveService {

	public Boolean saveLoanlostApprove(ToApproveRecord toApproveRecord);
	
	public List<String> findApproveRecordByList(ToApproveRecord toApproveRecord);
	
	public Map<String, Object> queryCaseInfo(String caseCode, String partCode,String instCode);

	public ToApproveRecord findLastApproveRecord(ToApproveRecord toApproveRecord);
}
