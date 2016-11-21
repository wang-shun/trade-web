package com.centaline.trans.engine.service;

import java.util.List;

import com.centaline.trans.engine.entity.ToOutTimeTask;
import com.centaline.trans.engine.entity.ToWorkFlow;

public interface ToWorkFlowService {

	int insertSelective(ToWorkFlow record);

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
	
	ToWorkFlow queryActiveToWorkFlowByBizCodeBusKey(ToWorkFlow record);
	
	List<ToWorkFlow>queryActiveToWorkFlowByCaseCode(ToWorkFlow record);
	/**
	 * 无效表单数据
	 * @param caseCode
	 * @return
	 */
	void inActiveForm(String caseCode);
	
	int updateWorkFlowByInstCode(ToWorkFlow record);
	
	void deleteWorkFlowByProperty(ToWorkFlow record);
	
	void deleteWorkFlowByInstCode(String instCode);
	
	List<ToWorkFlow> getMortToWorkFlowByCaseCode(ToWorkFlow record);
}
