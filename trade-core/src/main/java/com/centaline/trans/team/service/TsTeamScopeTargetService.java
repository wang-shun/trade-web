package com.centaline.trans.team.service;

import java.util.List;

import com.centaline.trans.team.entity.TsTeamScopeTarget;

public interface TsTeamScopeTargetService {
	
	List<TsTeamScopeTarget> getTeamScopeTargetListByProperty(TsTeamScopeTarget record);

	/**
	 * 添加誉萃，grp 对应关系
	 * @param salesId
	 * @param yuTeamCode
	 * @return
	 */
	String addCaseMapping(String salesOrgId,String yuTeamCode);
	
	/**
	 * 检查是否存在映射
	 * @param salesOrgId
	 * @return
	 */
	Boolean checkCaseMapping(String salesOrgId);
}
