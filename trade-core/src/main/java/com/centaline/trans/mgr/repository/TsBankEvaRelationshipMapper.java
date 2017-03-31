package com.centaline.trans.mgr.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mgr.entity.TsBankEvaRelationship;

@MyBatisRepository
public interface TsBankEvaRelationshipMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsBankEvaRelationship record);

    TsBankEvaRelationship selectByPrimaryKey(Long pkid);

    int update(TsBankEvaRelationship record);
    
    List<TsBankEvaRelationship> findByBankCode(String bankCode);

}