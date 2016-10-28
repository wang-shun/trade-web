package com.centaline.trans.spv.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvDe;

@MyBatisRepository
public interface ToSpvDeMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvDe record);

    int insertSelective(ToSpvDe record);

    ToSpvDe selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvDe record);

    int updateByPrimaryKey(ToSpvDe record);
    
    ToSpvDe selectBySpvCode(String spvCode);
}