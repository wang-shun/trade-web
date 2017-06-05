package com.centaline.trans.engine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.engine.entity.ToOutTimeTask;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.repository.ToWorkFlowMapper;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.mortgage.entity.ToMortLoaner;

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
	public ToWorkFlow queryActiveToWorkFlowByCaseCodeBusKeyBizCode(ToWorkFlow record) {
		
		if(null == record){
			throw new BusinessException("查询信贷员派单有效流程信息参数异常");
		}
		ToWorkFlow toWorkFlow= new ToWorkFlow();
		
		try{
			toWorkFlow  = toWorkFlowMapper.queryActiveToWorkFlowByCaseCodeBusKeyBizCode(record);

		}catch (BusinessException e) {
			throw new BusinessException("查询信贷员派单有效流程信息异常");
		}	
		return toWorkFlow;
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


	@Override
	public ToWorkFlow queryActiveToWorkFlowByBizCodeBusKey(ToWorkFlow record) {
		return toWorkFlowMapper.queryActiveToWorkFlowByBizCodeBusKey(record);
	}
	
	@Override
	public ToWorkFlow queryToWorkFlowByBizCodeBusKey(ToWorkFlow record) {
		return toWorkFlowMapper.queryToWorkFlowByBizCodeBusKey(record);
	}

	@Override
	public ToWorkFlow queryToWorkFlowByCaseCodeAndBusinessKey(ToWorkFlow record) {
		return toWorkFlowMapper.queryToWorkFlowByCaseCodeAndBusinessKey(record);
	}	@Override
	public List<ToWorkFlow> queryToWorkFlowByCaseCodeBusKeys(ToWorkFlow record) {
		return toWorkFlowMapper.queryToWorkFlowByCaseCodeBusKeys(record);
	}

	@Override
	public ToWorkFlow queryToWorkFlowByCaseCodeBusKey(ToWorkFlow record) {
		return toWorkFlowMapper.queryToWorkFlowByCaseCodeBusKey(record);
	}	
}
