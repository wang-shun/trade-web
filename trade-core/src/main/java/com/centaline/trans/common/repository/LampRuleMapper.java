package com.centaline.trans.common.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.LampRule;

@MyBatisRepository
public interface LampRuleMapper {
    int deleteByPrimaryKey(Long pkId);

    int insert(LampRule record);

    int insertSelective(LampRule record);

    LampRule selectByPrimaryKey(Long pkId);

    int updateByPrimaryKeySelective(LampRule record);

    int updateByPrimaryKey(LampRule record);
    
    int deleteLampRuleByProperty(LampRule lampRule);
}