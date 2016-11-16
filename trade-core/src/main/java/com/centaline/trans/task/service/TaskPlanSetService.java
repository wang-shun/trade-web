package com.centaline.trans.task.service;

import com.centaline.trans.transplan.entity.TsTaskPlanSet;

public interface TaskPlanSetService {
	TsTaskPlanSet getAutoTsTaskPlanSetByPartCode(String partCode);
}
