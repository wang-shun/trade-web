package com.centaline.trans.transplan.repository;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToTransPlanOrToPropertyInfo;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.entity.TsTransPlanHistory;

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
    int deleteTransPlansByCaseCode(Map map);

	List<ToTransPlanOrToPropertyInfo> getToTransPlanByDictOrUserId(
			List<String> dictCodeList, String id);

    /**
     * 通过交易变更历史的环节编码和案件编号查询交易计划的PKID
     * @param tsTransPlanHistory
     * @return
     */
    ToTransPlan findTransPlanPKIDBycasecodeAndPartCode(TsTransPlanHistory tsTransPlanHistory);
}