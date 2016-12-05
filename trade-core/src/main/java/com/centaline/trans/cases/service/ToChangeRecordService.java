package com.centaline.trans.cases.service;

import com.centaline.trans.cases.entity.ToChangeRecord;

public interface ToChangeRecordService {
	
	/***
	 * 插入变更记录
	 */
	int saveToChangeRecord(ToChangeRecord toChangeRecord);
}
