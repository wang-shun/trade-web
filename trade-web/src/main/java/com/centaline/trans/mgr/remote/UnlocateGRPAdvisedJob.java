package com.centaline.trans.mgr.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aist.scheduler.execution.remote.Job;
import com.aist.scheduler.execution.remote.vo.JobExecutionContext;
import com.centaline.trans.mgr.service.UnlocateGRPService;

@Component
public class UnlocateGRPAdvisedJob implements Job {

	@Autowired
	private UnlocateGRPService unlocateGRPService;
	@Override
	public void execute(JobExecutionContext arg0) {
		unlocateGRPService.execute();
	}

}
