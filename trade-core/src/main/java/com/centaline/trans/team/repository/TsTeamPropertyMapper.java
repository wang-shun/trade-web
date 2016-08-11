package com.centaline.trans.team.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.vo.CaseInfoVO;

@MyBatisRepository
public interface TsTeamPropertyMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsTeamProperty record);

    int insertSelective(TsTeamProperty record);

    TsTeamProperty selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsTeamProperty record);

    int updateByPrimaryKey(TsTeamProperty record);
    
    TsTeamProperty findTeamPropertyByTeamCode(String yuTeamCode);
    
    TsTeamProperty findTeamPropertyCooperation(TsTeamProperty record);

    /**
     * 功能：交易顾问查询
     * @param yuTeamCode
     * @author zhangxb16
     */
    TsTeamProperty selectTeamPropertyByTeamCode(String yuTeamCode);
    
    TsTeamProperty selectTeamPropertyByNoTeamCode(String yuTeamCode);
    
    List<TsTeamProperty> getTsTeamPropertyListByProperty(TsTeamProperty teamProperty);
    
    List<CaseInfoVO> recoveryTeamScope();
    
    List<TsTeamProperty> findTeamPropertyCooperations(TsTeamProperty record);
}