package com.centaline.trans.task.service;

import com.centaline.trans.task.entity.TsTaskPlanSet;

public interface TaskPlanSetService {
	TsTaskPlanSet getAutoTsTaskPlanSetByPartCode(String partCode);
}
