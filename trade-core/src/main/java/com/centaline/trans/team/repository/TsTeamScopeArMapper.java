package com.centaline.trans.team.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.team.entity.TsTeamScopeAr;

@MyBatisRepository
public interface TsTeamScopeArMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsTeamScopeAr record);

    int insertSelective(TsTeamScopeAr record);

    TsTeamScopeAr selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsTeamScopeAr record);

    int updateByPrimaryKey(TsTeamScopeAr record);
    
    List<TsTeamScopeAr> getTsTeamScopeArListByProperty(TsTeamScopeAr record);
    
    int delTsTeamScopeArByProperty(TsTeamScopeAr record);
}