package com.centaline.trans.transplan.service;

import com.centaline.trans.transplan.entity.TsTaskPlanSet;

public interface TaskPlanSetService {
	TsTaskPlanSet getAutoTsTaskPlanSetByPartCode(String partCode);
}
