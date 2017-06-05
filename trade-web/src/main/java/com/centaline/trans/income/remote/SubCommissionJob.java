package com.centaline.trans.income.remote;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.scheduler.execution.remote.Job;
import com.aist.scheduler.execution.remote.vo.JobExecutionContext;
import com.centaline.trans.income.service.ProfitService;

public class SubCommissionJob implements Job {
	@Autowired
	private ProfitService profitService;

	@Override
	public void execute(JobExecutionContext context) {
		profitService.doProcess();
	}

}
