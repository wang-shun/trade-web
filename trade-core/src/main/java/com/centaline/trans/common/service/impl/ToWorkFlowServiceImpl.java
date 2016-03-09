package com.centaline.trans.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.entity.ToOutTimeTask;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.repository.ToWorkFlowMapper;
import com.centaline.trans.common.service.ToWorkFlowService;

@Service
public class ToWorkFlowServiceImpl implements ToWorkFlowService {

	@Autowired
	ToWorkFlowMapper toWorkFlowMapper;

	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(ToWorkFlow record) {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.insert(record);
	}

	@Override
	public int insertSelective(ToWorkFlow record) {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.insertSelective(record);
	}

	@Override
	public ToWorkFlow selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(ToWorkFlow record) {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ToWorkFlow record) {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<String> queryCaseCodesByInstCode(String instCode) {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.queryCaseCodesByInstCode(instCode);
	}

	@Override
	public List<String> queryInstCodesByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.queryInstCodesByCaseCode(caseCode);
	}

	@Override
	public ToWorkFlow queryToWorkFlowByCaseCodeBusKey(ToWorkFlow record) {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.queryToWorkFlowByCaseCodeBusKey(record);
	}

	@Override
	public List<ToOutTimeTask> queryOutTimeTaskList() {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.queryOutTimeTaskList();
	}

	@Override
	public int deleteByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.deleteByCaseCode(caseCode);
	}

	@Override
	public int deleteByToWorkFlow(ToWorkFlow record) {
		return toWorkFlowMapper.deleteByToWorkFlow(record);
	}

	@Override
	public ToWorkFlow queryWorkFlowByInstCode(String instCode) {
		return toWorkFlowMapper.queryWorkFlowByInstCode(instCode);
	}

	@Override
	public ToWorkFlow queryActiveToWorkFlowByCaseCodeBusKey(ToWorkFlow record) {
		// TODO Auto-generated method stub
		return toWorkFlowMapper.queryActiveToWorkFlowByCaseCodeBusKey(record);
	}

	@Override
	public List<ToWorkFlow> queryActiveToWorkFlowByCaseCode(ToWorkFlow record) {
		return toWorkFlowMapper.queryActiveToWorkFlowByCaseCode(record);
	}

	@Override
	public void inActiveForm(String caseCode) {
		toWorkFlowMapper.inActiveForm(caseCode);
	}
}
