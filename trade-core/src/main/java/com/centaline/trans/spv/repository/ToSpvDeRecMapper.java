package com.centaline.trans.spv.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvDeRec;

@MyBatisRepository
public interface ToSpvDeRecMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvDeRec record);

    int insertSelective(ToSpvDeRec record);

    ToSpvDeRec selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvDeRec record);

    int updateByPrimaryKeyWithBLOBs(ToSpvDeRec record);

    int updateByPrimaryKey(ToSpvDeRec record);
    
    ToSpvDeRec findBySpvCodeAndCondId(ToSpvDeRec record);
    
    ToSpvDeRec findByProcessInstanceId(String processInstanceId);
}