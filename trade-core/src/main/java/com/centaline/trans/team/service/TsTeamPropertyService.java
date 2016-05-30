package com.centaline.trans.team.service;

import java.util.List;

import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.vo.CaseInfoVO;

public interface TsTeamPropertyService {
	    int deleteByPrimaryKey(Long pkid);

	    int insert(TsTeamProperty record);

	    int insertSelective(TsTeamProperty record);

	    TsTeamProperty selectByPrimaryKey(Long pkid);

	    int updateByPrimaryKeySelective(TsTeamProperty record);

	    int updateByPrimaryKey(TsTeamProperty record);
	    
	public TsTeamProperty findTeamPropertyByTeamCode(String yuTeamCode);
	
    public TsTeamProperty findTeamPropertyCooperation(TsTeamProperty record);
    
       List<TsTeamProperty> getTsTeamPropertyListByProperty(TsTeamProperty teamProperty);
       
       List<CaseInfoVO> recoveryTeamScope();
}
