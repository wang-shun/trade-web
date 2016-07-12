package com.centaline.trans.common.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.ToOutTimeTask;
import com.centaline.trans.common.entity.ToWorkFlow;

@MyBatisRepository
public interface ToWorkFlowMapper {
	int deleteByPrimaryKey(Long pkid);

	int insertSelective(ToWorkFlow record);

	ToWorkFlow selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(ToWorkFlow record);

	int updateByPrimaryKey(ToWorkFlow record);

	List<String> queryCaseCodesByInstCode(String instCode);

	List<String> queryInstCodesByCaseCode(String caseCode);

	ToWorkFlow queryToWorkFlowByCaseCodeBusKey(ToWorkFlow record);

	List<ToOutTimeTask> queryOutTimeTaskList();

	ToWorkFlow queryWorkFlowByInstCode(String instCode);
	
	ToWorkFlow queryActiveToWorkFlowByCaseCodeBusKey(ToWorkFlow record);
	List<ToWorkFlow>queryActiveToWorkFlowByCaseCode(ToWorkFlow record);
	void inActiveForm(String caseCode) ;
}