package com.centaline.trans.mgr.service;

import java.util.List;

import com.centaline.trans.mgr.entity.TsBankEvaRelationship;

public interface TsBankEvaRelationshipService {

	void saveTsBankEvaRelationship(TsBankEvaRelationship tsBankEvaRelationship);

	List<TsBankEvaRelationship> findByBankCode(String bankCode);
}
