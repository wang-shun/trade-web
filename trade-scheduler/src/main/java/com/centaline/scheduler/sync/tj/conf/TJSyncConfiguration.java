package com.centaline.scheduler.sync.tj.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
@Configuration()
public class TJSyncConfiguration {
	
	@Bean(name = "TJHrDataSource")
	public DataSource TJHrDataSource(){
		DruidDataSource dds = new DruidDataSource();
		dds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dds.setUrl("jdbc:sqlserver://10.5.250.14:1433;databaseName=AIS20070614182958");
		dds.setUsername("kquser");
		dds.setPassword("KQUserfx");
		dds.setMaxActive(2);//最大设置为2 因为只有同步的时候需要使用
		dds.setInitialSize(1);//初始化生成1个连接资源即可
		dds.setDbType("MS-SQL");
		return dds;
	}
	@Bean(name = "TJHrTemplate")
	public JdbcTemplate BJHrTemplate(){
		JdbcTemplate template = new JdbcTemplate(TJHrDataSource());
		return template;
	}
	
}
