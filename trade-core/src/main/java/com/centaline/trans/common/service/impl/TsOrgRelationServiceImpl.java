package com.centaline.trans.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.entity.TsOrgRelation;
import com.centaline.trans.common.repository.TsOrgRelationMapper;
import com.centaline.trans.common.service.TsOrgRelationService;

@Service
public class TsOrgRelationServiceImpl implements TsOrgRelationService{
	@Autowired
	TsOrgRelationMapper tsOrgRelationMapper; 
	@Override
	public int findTsOrgRelationByOrgRelation(TsOrgRelation orgRelation) {
		// TODO Auto-generated method stub
		return tsOrgRelationMapper.findTsOrgRelationByOrgRelation(orgRelation);
	}
	@Override
	public int addTsOrgRelation(TsOrgRelation orgRelation) {
		// TODO Auto-generated method stub
		return tsOrgRelationMapper.insertOrgRelation(orgRelation);
	}
	@Override
	public int updateTsOrgRelation(TsOrgRelation orgRelation) {
		// TODO Auto-generated method stub
		return tsOrgRelationMapper.updateOrgRelation(orgRelation);
	}
	@Override
	public int deleteOrgRelationByKey(TsOrgRelation orgRelation) {
		// TODO Auto-generated method stub
		return tsOrgRelationMapper.deleteOrgRelation(orgRelation);
	}

}
