package com.centaline.trans.team.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.team.entity.TsTeamScopeGrp;

@MyBatisRepository
public interface TsTeamScopeGrpMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsTeamScopeGrp record);

    int insertSelective(TsTeamScopeGrp record);

    TsTeamScopeGrp selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsTeamScopeGrp record);

    int updateByPrimaryKey(TsTeamScopeGrp record);
    
    List<TsTeamScopeGrp> getTsTeamScopeGrpListByProperty(TsTeamScopeGrp record);
}