package com.centaline.trans.award.repository;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.award.entity.AwardBaseEntity;
import com.centaline.trans.common.MyBatisRepository;
@MyBatisRepository
public interface AwardBaseEntityMapper {
	int deleteByPrimaryKey(Long pkid);

	int insert(AwardBaseEntity record);

	int insertSelective(AwardBaseEntity record);

	AwardBaseEntity selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(AwardBaseEntity record);

	int updateByPrimaryKey(AwardBaseEntity record);

	AwardBaseEntity selectByCaseCodeAndSrvCode(@Param("caseCode") String caseCode, @Param("srvCode") String srvCode);
	
	int deleteByCaseCode(String caseCode);
	
	int countManagerTeam(String userId);
}