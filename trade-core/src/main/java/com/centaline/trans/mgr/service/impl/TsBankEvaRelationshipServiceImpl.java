package com.centaline.trans.mgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.mgr.entity.TsBankEvaRelationship;
import com.centaline.trans.mgr.repository.TsBankEvaRelationshipMapper;
import com.centaline.trans.mgr.service.TsBankEvaRelationshipService;

@Service
public class TsBankEvaRelationshipServiceImpl implements
		TsBankEvaRelationshipService {

	@Autowired
	private TsBankEvaRelationshipMapper tsBankEvaRelationshipMapper;
	
	@Override
	public void saveTsBankEvaRelationship(
			TsBankEvaRelationship tsBankEvaRelationship) {
		tsBankEvaRelationshipMapper.insert(tsBankEvaRelationship);
	}

	@Override
	public List<TsBankEvaRelationship> findByBankCode(String bankCode){
		return tsBankEvaRelationshipMapper.findByBankCode(bankCode);
	}
}
