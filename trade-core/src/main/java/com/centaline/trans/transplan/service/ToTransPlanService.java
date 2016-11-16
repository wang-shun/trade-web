package com.centaline.trans.transplan.service;

import java.util.List;

import com.centaline.trans.task.entity.ToTransPlanOrToPropertyInfo;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.vo.TransPlanVO;

public interface ToTransPlanService {

	public boolean saveToTransPlan(TransPlanVO transPlanVO);
	
	public TransPlanVO findTransPlanByCaseCode(String caseCode);
	
	public ToTransPlan findTransPlan(ToTransPlan toTransPlan);
	
	public Boolean updateTransPlan(ToTransPlan toTransPlan);

    ToTransPlan selectByPrimaryKey(Long pkid);
	/**
	 * 获取到待办事项
	 * @return
	 */
	public List<ToTransPlanOrToPropertyInfo> getToTransPlanByUserId(String leadingProcessId);

    List<ToTransPlan> queryPlansByCaseCode(String caseCode);

    int updateByPrimaryKeySelective(ToTransPlan record);
    int deleteTransPlansByCaseCode(String caseCode);
    int insertSelective(ToTransPlan record);

	public List<ToTransPlanOrToPropertyInfo> getToTransPlanByDictOrUserId(
			List<String> dictCodeList, String id);
}
