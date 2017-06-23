package com.centaline.trans.common.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.TsOrgRelation;
import com.centaline.trans.team.vo.UserOrgRelationVO;

@MyBatisRepository
public interface TsOrgRelationMapper {
	
	int findTsOrgRelationByOrgRelation(TsOrgRelation orgRelation);
	
	int insertOrgRelation(TsOrgRelation orgRelation);
	
	int updateOrgRelation(TsOrgRelation orgRelation);
	
	int deleteOrgRelation(TsOrgRelation orgRelation);
	
	List<UserOrgRelationVO> queryUserOrgRelationByOrgId(String orgId);
}
