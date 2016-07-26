package com.centaline.trans.team.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.team.entity.TsTeamScopeTarget;

@MyBatisRepository
public interface TsTeamScopeTargetMapper {
    int insert(TsTeamScopeTarget record);

    int insertSelective(TsTeamScopeTarget record);
    
    List<TsTeamScopeTarget> getTeamScopeTargetListByProperty(TsTeamScopeTarget record);
}