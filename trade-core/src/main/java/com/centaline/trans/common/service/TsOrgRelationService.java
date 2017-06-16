package com.centaline.trans.common.service;

import com.centaline.trans.common.entity.TsOrgRelation;

public interface TsOrgRelationService {
	
	int findTsOrgRelationByOrgRelation(TsOrgRelation orgRelation);
	
	int addTsOrgRelation(TsOrgRelation orgRelation);
	
	int updateTsOrgRelation(TsOrgRelation orgRelation);
	
	int deleteOrgRelationByKey(TsOrgRelation orgRelation);
}
