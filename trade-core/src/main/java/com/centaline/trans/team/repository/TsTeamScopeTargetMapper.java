package com.centaline.trans.team.repository;

import java.util.List;

import com.centaline.trans.team.entity.TsTeamScopeTarget;

public interface TsTeamScopeTargetMapper {
    int insert(TsTeamScopeTarget record);

    int insertSelective(TsTeamScopeTarget record);
    
    List<TsTeamScopeTarget> getTeamScopeTargetListByProperty(TsTeamScopeTarget record);
}