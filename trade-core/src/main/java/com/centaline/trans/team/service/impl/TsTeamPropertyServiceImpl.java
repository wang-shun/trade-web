package com.centaline.trans.team.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.repository.TsTeamPropertyMapper;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.team.vo.CaseInfoVO;

@Service
public class TsTeamPropertyServiceImpl implements TsTeamPropertyService {

	
	@Autowired
	private TsTeamPropertyMapper tsTeamPropertyMapper;
	
	@Override
	public TsTeamProperty findTeamPropertyByTeamCode(String yuTeamCode) {
		return tsTeamPropertyMapper.findTeamPropertyByTeamCode(yuTeamCode);
	}

	@Override
	public TsTeamProperty findTeamPropertyCooperation(TsTeamProperty record) {
		return tsTeamPropertyMapper.findTeamPropertyCooperation(record);
	}

	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return tsTeamPropertyMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(TsTeamProperty record) {
		// TODO Auto-generated method stub
		return tsTeamPropertyMapper.insert(record);
	}

	@Override
	public int insertSelective(TsTeamProperty record) {
		// TODO Auto-generated method stub
		return tsTeamPropertyMapper.insertSelective(record);
	}

	@Override
	public TsTeamProperty selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return tsTeamPropertyMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(TsTeamProperty record) {
		// TODO Auto-generated method stub
		return tsTeamPropertyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TsTeamProperty record) {
		// TODO Auto-generated method stub
		return tsTeamPropertyMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<TsTeamProperty> getTsTeamPropertyListByProperty(TsTeamProperty teamProperty) {
		// TODO Auto-generated method stub
		return tsTeamPropertyMapper.getTsTeamPropertyListByProperty(teamProperty);
	}

	@Override
	public List<CaseInfoVO> recoveryTeamScope() {
       return  tsTeamPropertyMapper.recoveryTeamScope();		
	}

	@Override
	public List<TsTeamProperty> findTeamPropertyCooperations(TsTeamProperty record) {
		return tsTeamPropertyMapper.findTeamPropertyCooperations(record);
	}
	

}
