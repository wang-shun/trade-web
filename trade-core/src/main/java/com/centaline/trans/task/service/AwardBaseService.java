package com.centaline.trans.task.service;

import java.util.Date;

import com.centaline.trans.task.entity.ToHouseTransfer;

public interface AwardBaseService {
	void doAwardCalculate(ToHouseTransfer toHouseTransfer,String processInstanceId);
	
	void setAwradCaseCloseDate(String caseCode,Date closeDate);
	
}
