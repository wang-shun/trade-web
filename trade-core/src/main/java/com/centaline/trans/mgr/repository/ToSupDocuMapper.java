package com.centaline.trans.mgr.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mgr.entity.ToSupDocu;

@MyBatisRepository
public interface ToSupDocuMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSupDocu record);

    int insertSelective(ToSupDocu record);

    ToSupDocu selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSupDocu record);

    int updateByPrimaryKeyWithBLOBs(ToSupDocu record);

    int updateByPrimaryKey(ToSupDocu record);
    
    ToSupDocu findByCaseCode(String caseCode);
}