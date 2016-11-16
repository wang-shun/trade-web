package com.centaline.trans.transplan.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.transplan.entity.TsTaskPlanSet;
@MyBatisRepository
public interface TaskPlanSetMapper {
	TsTaskPlanSet getAutoTsTaskPlanSetByPartCode(String partCode);
	TsTaskPlanSet getTsTaskPlanSetByPartCode(String code);
}
