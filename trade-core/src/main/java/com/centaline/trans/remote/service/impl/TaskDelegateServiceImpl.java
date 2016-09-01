package com.centaline.trans.remote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.remote.service.TaskDelegateService;
import com.centaline.trans.task.service.TsTaskDelegateService;

@Service(value = "taskDelegateService")
public class TaskDelegateServiceImpl implements TaskDelegateService {
	@Autowired
	private TsTaskDelegateService tsTaskDelegateService;

	@Override
	public String getTaskAgent(String owner) {
		return tsTaskDelegateService.getTaskAgent(owner);
	}

}
