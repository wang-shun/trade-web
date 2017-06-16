package com.centaline.trans.common.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.TsOrgRelation;

@MyBatisRepository
public interface TsOrgRelationMapper {
	
	int findTsOrgRelationByOrgRelation(TsOrgRelation orgRelation);
	
	int insertOrgRelation(TsOrgRelation orgRelation);
	
	int updateOrgRelation(TsOrgRelation orgRelation);
	
	int deleteOrgRelation(TsOrgRelation orgRelation);
}
