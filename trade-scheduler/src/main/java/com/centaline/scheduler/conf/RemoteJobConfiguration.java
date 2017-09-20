package com.centaline.scheduler.conf;

import com.aist.scheduler.execution.remote.Job;
import com.centaline.scheduler.sync.remoteJob.K3SyncJob;
import com.centaline.scheduler.sysconfig.SequencesResetJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

/**
 * 提供远程调度Job的配置
 * 该类配置完成后，要把对应的Bean在
 * @see WebApplicationConfig 中进行映射
 * @author yinchao
 * @date 2017/9/18
 */
@Configuration
public class RemoteJobConfiguration {

	@Autowired
	K3SyncJob syncJob;

	@Autowired
	SequencesResetJob sequencesResetJob;

	@Bean("/remote/K3SyncJob")
	public HttpInvokerServiceExporter K3SyncJob(){
		HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
		exporter.setService(syncJob);
		exporter.setServiceInterface(Job.class);
		return exporter;
	}

	@Bean("/remote/SequencesResetJob")
	public HttpInvokerServiceExporter SequencesResetJob(){
		HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
		exporter.setService(sequencesResetJob);
		exporter.setServiceInterface(Job.class);
		return exporter;
	}

}
