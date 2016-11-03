package com.centaline.trans.spv.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvProperty;

@MyBatisRepository
public interface ToSpvPropertyMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvProperty record);

    int insertSelective(ToSpvProperty record);

    ToSpvProperty selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvProperty record);

    int updateByPrimaryKey(ToSpvProperty record);
    
    ToSpvProperty selectBySpvCode(String spvCode);
}