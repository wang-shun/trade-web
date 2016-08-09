package com.centaline.trans.remote.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.remote.service.AutoPlanService;
import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.entity.TsTaskPlanSet;
import com.centaline.trans.task.service.TaskPlanSetService;
import com.centaline.trans.task.service.ToTransPlanService;
@Service("autoPlanService")
public class AutoPlanServiceImpl implements AutoPlanService {
	@Autowired
	private TaskPlanSetService taskPlanSetService;
	
	private ToTransPlanService toTransPlanService;
	@Override
	public void autoGenerateTaskPlan(String taskDfkey, String caseCode) {
		TsTaskPlanSet planSet = taskPlanSetService.getAutoTsTaskPlanSetByPartCode(taskDfkey);
		if (planSet == null)
			return;
		ToTransPlan plan = new ToTransPlan();
		plan.setCaseCode(caseCode);
		plan.setPartCode(taskDfkey);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, planSet.getPlanDays());
		plan.setEstPartTime(cal.getTime());
		toTransPlanService.updateTransPlan(plan);
		
	}

}
