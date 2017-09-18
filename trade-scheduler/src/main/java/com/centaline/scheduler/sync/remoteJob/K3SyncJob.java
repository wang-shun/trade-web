package com.centaline.scheduler.sync.remoteJob;

import com.aist.scheduler.execution.remote.Job;
import com.aist.scheduler.execution.remote.vo.JobExecutionContext;
import com.centaline.scheduler.sync.enums.CityType;
import com.centaline.scheduler.sync.service.SyncHrService;
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

	@Autowired
	@Qualifier("bJSyncService")
	SyncHrService bJSyncService;
	@Autowired
	@Qualifier("tJSyncService")
	SyncHrService tJSyncService;

	@Override
	public void execute(JobExecutionContext context) {
		//注意顺序 后面不同城市 先在该处 进行添加 后面再进行调整
		System.out.println("-----------execu K3Sync Job-----start-----");
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
		System.out.println("-----------execu K3Sync Job-----end -----");
	}
}
