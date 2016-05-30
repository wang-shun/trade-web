package com.centaline.trans.task.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.entity.ToTransPlanOrToPropertyInfo;

@MyBatisRepository
public interface ToTransPlanMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToTransPlan record);

    int insertSelective(ToTransPlan record);

    ToTransPlan selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToTransPlan record);

    int updateByPrimaryKey(ToTransPlan record);
    
    int findTransPlanByCount(ToTransPlan record);
    
    List<ToTransPlan> findTransPlanByCaseCode(String caseCode);
    
    ToTransPlan findTransPlan(ToTransPlan record);
    
    int updateTransPlanSelective(ToTransPlan record);

    /**
     * 获取到 待办事项
     * @return
     */
	List<ToTransPlanOrToPropertyInfo> getToTransPlanByUserId(String leadingProcessId);
    int deleteTransPlansByCaseCode(String caseCode);

	List<ToTransPlanOrToPropertyInfo> getToTransPlanByDictOrUserId(
			List<String> dictCodeList, String id);
}