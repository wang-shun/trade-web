package com.centaline.trans.common.service;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.entity.TsOrgRelation;
import com.centaline.trans.team.vo.UserOrgRelationVO;

public interface TsOrgRelationService {
	
	int findTsOrgRelationByOrgRelation(TsOrgRelation orgRelation);
	
	int addTsOrgRelation(TsOrgRelation orgRelation);
	
	int updateTsOrgRelation(TsOrgRelation orgRelation);
	
	int deleteOrgRelationByKey(TsOrgRelation orgRelation);
	
	List<UserOrgRelationVO> getUserOrgRelationByOrgId(String orgId);
}
