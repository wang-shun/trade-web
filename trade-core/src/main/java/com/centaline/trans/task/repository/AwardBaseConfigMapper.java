package com.centaline.trans.task.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.AwardBaseConfig;

@MyBatisRepository
public interface AwardBaseConfigMapper {
	public List<AwardBaseConfig> getConsultantConfig();
	public List<AwardBaseConfig> getConfig(AwardBaseConfig config);
}
