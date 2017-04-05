package com.centaline.trans.award.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aist.uam.userorg.remote.vo.User;
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

	void setCloseDateByCaseCode(AwardBaseEntity record);

	int deleteByCaseCode(String caseCode);

	int countAward(@Param("caseCode") String caseCode);

	int countManagerTeam(String userId);

	List<User> getUserByJobCodeAndOrgId(String jobCode, String orgId);
}