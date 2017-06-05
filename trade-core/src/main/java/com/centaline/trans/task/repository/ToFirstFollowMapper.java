package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToFirstFollow;

@MyBatisRepository
public interface ToFirstFollowMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToFirstFollow record);

    int insertSelective(ToFirstFollow record);

    ToFirstFollow selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToFirstFollow record);

    int updateByPrimaryKey(ToFirstFollow record);
    
    
    ToFirstFollow selectByCaseCode(String caseCode);
    
    int isExistCasecode(String caseCode);
    
    
    int updateByCaseCode(ToFirstFollow ff);
    
}