package com.centaline.trans.common.service;

import java.util.List;

import com.centaline.trans.common.entity.ToOutTimeTask;
import com.centaline.trans.engine.entity.ToWorkFlow;

public interface ToWorkFlowService {

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
	List<ToWorkFlow>queryActiveToWorkFlowByCaseCode(ToWorkFlow record);
	/**
	 * 无效表单数据
	 * @param caseCode
	 * @return
	 */
	void inActiveForm(String caseCode);
	
	int updateWorkFlowByInstCode(ToWorkFlow record);
}
