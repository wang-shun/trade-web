package com.centaline.trans.engine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.engine.entity.ToOutTimeTask;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.repository.ToWorkFlowMapper;
import com.centaline.trans.engine.service.ToWorkFlowService;

@Service
public class ToWorkFlowServiceImpl implements ToWorkFlowService {

	@Autowired
	private ToWorkFlowMapper toWorkFlowMapper;


	@Override
	public int insertSelective(ToWorkFlow record) {
		 toWorkFlowMapper.insertSelective(record);
		 return 1;
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
	public ToWorkFlow queryWorkFlowByInstCode(String instCode) {
		return toWorkFlowMapper.queryWorkFlowByInstCode(instCode);
	}

	@Override
	public ToWorkFlow queryActiveToWorkFlowByCaseCodeBusKey(ToWorkFlow record) {
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

	@Override
	public int updateWorkFlowByInstCode(ToWorkFlow record) {
		return toWorkFlowMapper.updateWorkFlowByInstCode(record);
	}

	@Override
	public List<String> queryAllInstCodesByCaseCode(String caseCode) {
		return toWorkFlowMapper.queryAllInstCodesByCaseCode(caseCode);
	}

	@Override
	public ToWorkFlow queryToWorkFlowByCaseCodeAndStatus(ToWorkFlow record) {
		return toWorkFlowMapper.queryToWorkFlowByCaseCodeAndStatus(record);
	}
	@Override
	public void deleteWorkFlowByProperty(ToWorkFlow record) {
		  toWorkFlowMapper.deleteWorkFlowByProperty(record);
	}
	@Override
	public void deleteWorkFlowByInstCode(String instCode) {
		 toWorkFlowMapper.deleteWorkFlowByInstCode(instCode);
	}
	@Override
	public List<ToWorkFlow> getMortToWorkFlowByCaseCode(ToWorkFlow record) {
		return toWorkFlowMapper.getMortToWorkFlowByCaseCode(record);
	}
	
}
