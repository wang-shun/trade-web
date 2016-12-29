package com.centaline.trans.team.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.team.entity.TsTeamScopeTarget;

@MyBatisRepository
public interface TsTeamScopeTargetMapper {
    int insert(TsTeamScopeTarget record);

    int insertSelective(TsTeamScopeTarget record);
    
    List<TsTeamScopeTarget> getTeamScopeTargetListByProperty(TsTeamScopeTarget record);
    
    /**
     * 更新mapping
     * @return
     */
    Integer updateGrpMap();
    
    TsTeamScopeTarget getTeamScopeTargetInfo(Map<String, Object> param);   
}