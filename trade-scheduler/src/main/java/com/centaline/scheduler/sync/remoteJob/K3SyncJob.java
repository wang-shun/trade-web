package com.centaline.scheduler.sync.remoteJob;

import com.aist.scheduler.execution.remote.Job;
import com.aist.scheduler.execution.remote.vo.JobExecutionContext;
import com.centaline.scheduler.sync.enums.CityType;
import com.centaline.scheduler.sync.service.SyncHrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 用于定时触发
 * 各个城市同步K3人事数据的到交易系统Service
 *
 * @author yinchao
 * @date 2017/9/18
 */
@Component  //让spring加入bean管理中 否则手动配置
public class K3SyncJob implements Job{
	private Logger logger = LoggerFactory.getLogger(K3SyncJob.class);
	@Autowired
	@Qualifier("bJSyncService")
	SyncHrService bJSyncService;
	@Autowired
	@Qualifier("tJSyncService")
	SyncHrService tJSyncService;

	@Override
	public void execute(JobExecutionContext context) {
		//注意顺序 后面不同城市 先在该处 进行添加 后面再进行调整
		logger.info("-----------execu K3Sync Job-----start-----");
		//北京
		bJSyncService.toSyncOrgUnit(CityType.BEIJING,"AUTO");
		bJSyncService.toSyncEmployee(CityType.BEIJING,"AUTO");
		bJSyncService.toSyncPosition(CityType.BEIJING,"AUTO");
		bJSyncService.toSyncEmpOrgPos(CityType.BEIJING,"AUTO");
		//天津
		tJSyncService.toSyncOrgUnit(CityType.TIANJIN,"AUTO");
		tJSyncService.toSyncEmployee(CityType.TIANJIN,"AUTO");
		tJSyncService.toSyncPosition(CityType.TIANJIN,"AUTO");
		tJSyncService.toSyncEmpOrgPos(CityType.TIANJIN,"AUTO");
		//执行存储过程 同步到正式表中 service用任何城市的都可以 只要没有重写即可
		bJSyncService.doProcExec("{call sync.proc_sync_dept(?,?)}");
		bJSyncService.doProcExec("{call sync.proc_sync_emp(?,?)}");
		bJSyncService.doProcExec("{call sync.proc_sync_position(?,?)}");
		bJSyncService.doProcExec("{call sync.proc_sync_eop(?,?)}");
		logger.info("-----------execu K3Sync Job-----end -----");
	}
}
