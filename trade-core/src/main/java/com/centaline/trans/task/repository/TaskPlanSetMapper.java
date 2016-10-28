package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.TsTaskPlanSet;
@MyBatisRepository
public interface TaskPlanSetMapper {
	TsTaskPlanSet getAutoTsTaskPlanSetByPartCode(String partCode);
}
