package com.centaline.trans.income.repository;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.income.entity.TsAwardDispatch;

@MyBatisRepository
public interface TsAwardDispatchMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsAwardDispatch record);

    int insertSelective(TsAwardDispatch record);

    TsAwardDispatch selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsAwardDispatch record);

    int updateByPrimaryKey(TsAwardDispatch record);
    
    TsAwardDispatch findTsAwardDispatchByParticipant(TsAwardDispatch tsAwardDispatch);
}