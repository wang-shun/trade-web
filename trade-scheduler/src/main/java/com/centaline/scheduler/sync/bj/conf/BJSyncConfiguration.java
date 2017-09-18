package com.centaline.scheduler.sync.bj.conf;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;
/**
 * 同步配置类
 * 用于配置同步的源 数据源等信息
 * @author yinchao
 *
 */
@Configuration()
public class BJSyncConfiguration {
	
	@Bean(name = "BJHrDataSource")
	public DataSource BJHrDataSource(){
		DruidDataSource dds = new DruidDataSource();
		dds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dds.setUrl("jdbc:sqlserver://10.5.250.14:1433;databaseName=AIS20061025163636");
		dds.setUsername("CCAIUser");
		dds.setPassword("BJ.ccai");
		dds.setMaxActive(4);//最大设置为2 因为只有同步的时候需要使用
		dds.setInitialSize(1);//初始化生成1个连接资源即可
		dds.setDbType("MS-SQL");
		return dds;
	}
	@Bean(name = "BJHrTemplate")
	public JdbcTemplate BJHrTemplate(){
		JdbcTemplate template = new JdbcTemplate(BJHrDataSource());
		return template;
	}
	
}
