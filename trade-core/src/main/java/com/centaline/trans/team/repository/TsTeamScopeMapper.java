package com.centaline.trans.team.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.team.entity.TsTeamScope;
import com.centaline.trans.team.vo.TsTeamScopePropertyVO;

@MyBatisRepository
public interface TsTeamScopeMapper {
    int deleteByPrimaryKey(Long pkid);
    
    int deleteTeamScopeByProperty(TsTeamScope teamScope);

    int insert(TsTeamScope record);

    int insertSelective(TsTeamScope record);

    TsTeamScope selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsTeamScope record);

    int updateByPrimaryKey(TsTeamScope record);
    
    List<TsTeamScope> findTeamScope(TsTeamScope record);

    
    /**
     * 功能：根据店组查询 誉萃的orgid
     * @author zhangxb16
     */
    List<TsTeamScope> selectByYuagentTeamCode(String yuBankCode);
    
    /**
     * 查询合作组
     * @param record
     * @return
     */
    List<TsTeamScope> selectCooperativeOrganization(TsTeamScope record);
    
    
    // 根据orgCode 到 T_TS_TEAM_SCOPE 表中去查询合作组的 orgCode 
    List<TsTeamScope> selectByOrgCode(String orgCode);
    
    List<TsTeamScopePropertyVO> getTeamScopeListByProperty(TsTeamScope tsTeamScope);
    
}