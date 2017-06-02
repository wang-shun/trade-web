package com.centaline.trans.team.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.entity.TsTeamScope;
import com.centaline.trans.team.repository.TsTeamPropertyMapper;
import com.centaline.trans.team.repository.TsTeamScopeMapper;
import com.centaline.trans.team.service.TsTeamScopeService;
import com.centaline.trans.team.vo.TsTeamScopePropertyVO;
import com.centaline.trans.team.vo.TsTeamScopeVO;

@Service
public class TsTeamScopeServiceImpl implements TsTeamScopeService {

	@Autowired
	private TsTeamScopeMapper tsTeamScopeMapper;
	
	@Autowired
	private TsTeamPropertyMapper tsTeamPropertyMapper;
	
	@Override
	public List<TsTeamScope> findTeamScope(TsTeamScope tsTeamScope) {
		return tsTeamScopeMapper.findTeamScope(tsTeamScope);
	}

	@Override
	public List<TsTeamScope> selectCooperativeOrganization(
			TsTeamScope tsTeamScope) {
		return tsTeamScopeMapper.selectCooperativeOrganization(tsTeamScope);
	}

	@Override
	public void saveTsTeamScope(TsTeamScope tsTeamScope) {
		if(tsTeamScope.getPkid() != null){
			tsTeamScopeMapper.updateByPrimaryKeySelective(tsTeamScope);

		}else{
			tsTeamScopeMapper.insertSelective(tsTeamScope);

		}
	}

	@Override
	public TsTeamScope findById(Long id) {
		return tsTeamScopeMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteTsTeamScope(Long id) {
		tsTeamScopeMapper.deleteByPrimaryKey(id);
		
	}
	
	// 根据orgCode 到 T_TS_TEAM_SCOPE 表中去查询合作组的 orgCode 
	@Override
	public List<TsTeamScope> selectByOrgCode(String orgCode) {
		List<TsTeamScope> teamScopeList=tsTeamScopeMapper.selectByOrgCode(orgCode);
		return teamScopeList;
	}

	@Override
	public int saveTsTeamScopeVo(TsTeamScopeVO tsTeamScopeVO) {
		if(StringUtils.isBlank(tsTeamScopeVO.getYuAgentTeamCode()) && StringUtils.isBlank(tsTeamScopeVO.getBusiArIds())) {
			return 0;
		}
		int i = dealAgentTeamScope(tsTeamScopeVO);
		return i;
	}
	
	// 处理单个TeamCode
	private int dealAgentTeamScope(TsTeamScopeVO tsTeamScopeVO) {
		int i = 0;
		// 删除关联的业务组
		TsTeamScope teamScope = new TsTeamScope();
		teamScope.setYuAgentTeamCode(tsTeamScopeVO.getYuAgentTeamCode());
		tsTeamScopeMapper.deleteTeamScopeByProperty(teamScope);
		
		List<String> teamScopeList = tsTeamScopeVO.getYuTeamCode();
		for(String teamScopeCode : teamScopeList) {
			TsTeamScope TsTeamScope1 = new TsTeamScope();
			TsTeamScope1.setYuAgentTeamCode(tsTeamScopeVO.getYuAgentTeamCode());
			TsTeamScope1.setYuAgentTeamName(tsTeamScopeVO.getYuAgentTeamName());
			TsTeamScope1.setYuTeamCode(teamScopeCode);
			
			String yuTeamName = "";
			TsTeamProperty tsTeamProperty = new TsTeamProperty(); 
			tsTeamProperty.setYuTeamCode(teamScopeCode);
			if(tsTeamPropertyMapper.getTsTeamPropertyListByProperty(tsTeamProperty)!=null) {
				yuTeamName = tsTeamPropertyMapper.getTsTeamPropertyListByProperty(tsTeamProperty).get(0).getYuTeamName();
			}
			TsTeamScope1.setYuTeamName(yuTeamName);
			
			i+=tsTeamScopeMapper.insert(TsTeamScope1);
		}
		return i;
	}
	
	// 批量处理区域的TeamCode
	/*private int dealAreaTeamScope(TsTeamScopeVO tsTeamScopeVO) {
		int i = 0;
		// 删除关联的业务组
		TsTeamScope teamScope = new TsTeamScope();
		teamScope.setYuAgentTeamCode(tsTeamScopeVO.getYuAgentTeamCode());
		tsTeamScopeMapper.deleteTeamScopeByProperty(teamScope);
		
		List<String> teamScopeList = tsTeamScopeVO.getYuTeamCode();
		for(String teamScopeCode : teamScopeList) {
			TsTeamScope TsTeamScope1 = new TsTeamScope();
			TsTeamScope1.setYuAgentTeamCode(tsTeamScopeVO.getYuAgentTeamCode());
			TsTeamScope1.setYuAgentTeamName(tsTeamScopeVO.getYuAgentTeamName());
			TsTeamScope1.setYuTeamCode(teamScopeCode);
			
			String yuTeamName = "";
			TsTeamProperty tsTeamProperty = new TsTeamProperty(); 
			tsTeamProperty.setYuTeamCode(teamScopeCode);
			if(tsTeamPropertyMapper.getTsTeamPropertyListByProperty(tsTeamProperty)!=null) {
				yuTeamName = tsTeamPropertyMapper.getTsTeamPropertyListByProperty(tsTeamProperty).get(0).getYuTeamName();
			}
			TsTeamScope1.setYuTeamName(yuTeamName);
			
			i+=tsTeamScopeMapper.insert(TsTeamScope1);
		}
		return i;
	}*/

	@Override
	public List<TsTeamScopePropertyVO> getTeamScopeListByProperty(TsTeamScope tsTeamScope) {
		return tsTeamScopeMapper.getTeamScopeListByProperty(tsTeamScope);
	}




}
