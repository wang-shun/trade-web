package com.centaline.scheduler.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Properties;

/**
 * Spring mvc 的配置类
 * 用于配置 url job的路径映射
 * @author yinchao
 * @date 2017/9/18
 */
@Configuration
public class WebApplicationConfig extends WebMvcConfigurerAdapter{

	@Autowired
	@Qualifier("/remote/K3SyncJob")
	HttpInvokerServiceExporter k3SyncJob;

	@Bean
	public SimpleUrlHandlerMapping simpleUrlHandlerMapping(){
		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		mapping.setOrder(1);
		Properties urlProperties = new Properties();
		urlProperties.put("/remote/K3SyncJob",k3SyncJob);
		mapping.setMappings(urlProperties);
		return mapping;
	}
}
