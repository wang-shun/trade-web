package com.centaline.trans.award.service;

import com.centaline.trans.award.entity.TsConsultantAwardBaseConfig;
import com.centaline.trans.award.entity.TsManagementAwardBaseConfig;

public interface TsManagementAwardBaseConfigService {

	int insertSelective(TsManagementAwardBaseConfig tsManagementAwardBaseConfig);

	int deleteByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(TsManagementAwardBaseConfig tsManagementAwardBaseConfig);

	int updateBaseConfigByPrimaryKey(TsConsultantAwardBaseConfig tsConsultantAwardBaseConfig);

}
