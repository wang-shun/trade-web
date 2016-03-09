package com.centaline.trans.cases.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToLoanAgent;
import com.centaline.trans.cases.repository.ToLoanAgentMapper;
import com.centaline.trans.cases.service.ToLoanAgentService;

@Service
public class ToLoanAgentServiceImpl implements ToLoanAgentService {

	@Autowired
	ToLoanAgentMapper toLoanAgentMapper;
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toLoanAgentMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(ToLoanAgent record) {
		// TODO Auto-generated method stub
		return toLoanAgentMapper.insert(record);
	}

	@Override
	public int insertSelective(ToLoanAgent record) {
		// TODO Auto-generated method stub
		return toLoanAgentMapper.insertSelective(record);
	}

	@Override
	public ToLoanAgent selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toLoanAgentMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(ToLoanAgent record) {
		// TODO Auto-generated method stub
		return toLoanAgentMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ToLoanAgent record) {
		// TODO Auto-generated method stub
		return toLoanAgentMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ToLoanAgent> selectByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toLoanAgentMapper.selectByCaseCode(caseCode);
	}

}
