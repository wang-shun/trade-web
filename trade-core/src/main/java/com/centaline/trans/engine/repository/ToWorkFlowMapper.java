package com.centaline.trans.engine.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.engine.entity.ToOutTimeTask;
import com.centaline.trans.engine.entity.ToWorkFlow;

@MyBatisRepository
public interface ToWorkFlowMapper {
	

	int insertSelective(ToWorkFlow record);

	ToWorkFlow selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(ToWorkFlow record);

	int updateByPrimaryKey(ToWorkFlow record);

	List<String> queryCaseCodesByInstCode(String instCode);

	List<String> queryInstCodesByCaseCode(String caseCode);
	
	List<String> queryAllInstCodesByCaseCode(String caseCode);

	ToWorkFlow queryToWorkFlowByCaseCodeBusKey(ToWorkFlow record);
	
	List<ToWorkFlow> queryToWorkFlowByCaseCodeBusKeys(ToWorkFlow record);
	
	ToWorkFlow queryToWorkFlowByCaseCodeAndStatus(ToWorkFlow record);

	List<ToOutTimeTask> queryOutTimeTaskList();

	ToWorkFlow queryWorkFlowByInstCode(String instCode);
	
	ToWorkFlow queryActiveToWorkFlowByCaseCodeBusKey(ToWorkFlow record);
	
	ToWorkFlow queryToWorkFlowByCaseCodeAndBusinessKey(ToWorkFlow record);
	
	ToWorkFlow queryActiveToWorkFlowByBizCodeBusKey(ToWorkFlow record);
	
	ToWorkFlow queryToWorkFlowByBizCodeBusKey(ToWorkFlow record);
	
	ToWorkFlow queryActiveToWorkFlowByCaseCodeBusKeyBizCode(ToWorkFlow record);
	
	List<ToWorkFlow> queryActiveToWorkFlowByCaseCode(ToWorkFlow record);
	
	List<ToWorkFlow> getMortToWorkFlowByCaseCode(ToWorkFlow record);
	
	void inActiveForm(String caseCode) ;
	
	int deleteWorkFlowByProperty(ToWorkFlow record);
	
	int updateWorkFlowByInstCode(ToWorkFlow record);
	
	void deleteWorkFlowByInstCode(String instCode);
	
	/**
	 * 根据条件查询任务
	 * @param bizCode
	 * @param businessKey
	 * @return
	 */
	List<String> queryAllInstCodesByBizCode(@Param("bizCode")String bizCode,@Param("businessKey")String businessKey);
	
}
