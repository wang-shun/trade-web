package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvDeCond;

@MyBatisRepository
public interface ToSpvDeCondMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvDeCond record);

    int insertSelective(ToSpvDeCond record);

    ToSpvDeCond selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvDeCond record);

    int updateByPrimaryKeyWithBLOBs(ToSpvDeCond record);

    int updateByPrimaryKey(ToSpvDeCond record);
    
    List<ToSpvDeCond> findBySpvCode(String spvCode);
}