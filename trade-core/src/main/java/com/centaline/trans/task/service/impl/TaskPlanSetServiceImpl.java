package com.centaline.trans.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.service.TaskPlanSetService;
import com.centaline.trans.transplan.entity.TsTaskPlanSet;
import com.centaline.trans.transplan.repository.TaskPlanSetMapper;
@Service
public class TaskPlanSetServiceImpl implements TaskPlanSetService{
	@Autowired
	private TaskPlanSetMapper taskPlanSetMapper;
	@Override
	public TsTaskPlanSet getAutoTsTaskPlanSetByPartCode(String partCode) {
		return taskPlanSetMapper.getAutoTsTaskPlanSetByPartCode(partCode);
	}

}
