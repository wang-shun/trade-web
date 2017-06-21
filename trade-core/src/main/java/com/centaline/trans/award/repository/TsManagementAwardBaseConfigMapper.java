package com.centaline.trans.award.repository;

import com.centaline.trans.award.entity.TsConsultantAwardBaseConfig;
import com.centaline.trans.award.entity.TsManagementAwardBaseConfig;
import com.centaline.trans.common.MyBatisRepository;


@MyBatisRepository
public interface TsManagementAwardBaseConfigMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsManagementAwardBaseConfig record);

    int insertSelective(TsManagementAwardBaseConfig record);

    TsManagementAwardBaseConfig selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsManagementAwardBaseConfig record);

    int updateByPrimaryKey(TsManagementAwardBaseConfig record);

	int updateBaseConfigByPrimaryKey(TsConsultantAwardBaseConfig tsConsultantAwardBaseConfig);
	
	TsManagementAwardBaseConfig findAwardManagerInfoByUserIdAndOrgId(TsManagementAwardBaseConfig record);
	
}