package com.centaline.trans.engine.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.ToOutTimeTask;
import com.centaline.trans.engine.entity.ToWorkFlow;

@MyBatisRepository
public interface ToWorkFlowMapper {
	int deleteByPrimaryKey(Long pkid);

	int insertSelective(ToWorkFlow record);
	
	int insertSpvCashflowInProcessSelective(ToWorkFlow record);

	ToWorkFlow selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(ToWorkFlow record);

	int updateByPrimaryKey(ToWorkFlow record);

	List<String> queryCaseCodesByInstCode(String instCode);

	List<String> queryInstCodesByCaseCode(String caseCode);
	
	List<String> queryAllInstCodesByCaseCode(String caseCode);

	ToWorkFlow queryToWorkFlowByCaseCodeBusKey(ToWorkFlow record);
	
	ToWorkFlow queryToWorkFlowByCaseCodeAndStatus(ToWorkFlow record);

	List<ToOutTimeTask> queryOutTimeTaskList();

	ToWorkFlow queryWorkFlowByInstCode(String instCode);
	
	ToWorkFlow queryActiveToWorkFlowByCaseCodeBusKey(ToWorkFlow record);
	List<ToWorkFlow> queryActiveToWorkFlowByCaseCode(ToWorkFlow record);
	List<ToWorkFlow> getMortToWorkFlowByCaseCode(ToWorkFlow record);
	void inActiveForm(String caseCode) ;
	
	int deleteWorkFlowByProperty(ToWorkFlow record);
	
	int updateWorkFlowByInstCode(ToWorkFlow record);
	
	void deleteWorkFlowByInstCode(String instCode);
	
}