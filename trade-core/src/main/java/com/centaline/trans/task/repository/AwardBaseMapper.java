package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.AwardBase;
@MyBatisRepository
public interface AwardBaseMapper {
	 void insert(AwardBase ab);
	 int deleteByCaseCode(String caseCode);
	 Integer getDirectorOrgSize(String userId,String jobCode);
	 Integer getGeneralManagerOrgSize(String userId,String jobCode);
}
