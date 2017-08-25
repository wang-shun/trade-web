package com.centaline.api.conf;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Created by linjiarong on 2016/10/11.
 *
 */
@Configuration
public class DeployContextConfiguration {
	
    private final Logger logger = LoggerFactory.getLogger(DeployContextConfiguration.class);
    
    //////////////数据库连接配置////////////////
    
    @Bean(name="dataSourceSettings")
    @ConfigurationProperties(prefix = "dataSource")
    public DataSourceSettings dataSourceSettings(){
    	return new DataSourceSettings();
    }
    @ConfigurationProperties(prefix = "readOnlyDataSource")
    @Bean(name="readOnlyDataSourceSettings")
    public DataSourceSettings readOnlyDataSourceSettings(){
    	return new DataSourceSettings();
    }
    @Bean(name="readOnlyDataSourceProxy")
    public TransactionAwareDataSourceProxy readOnlyDataSourceProxy(@Qualifier(value="readOnlyDataSource")DataSource readOnlyDataSource){
    	return new TransactionAwareDataSourceProxy(readOnlyDataSource);
    }
    @Bean(name="dataSourceProxy")
    public TransactionAwareDataSourceProxy dataSourceProxy(@Qualifier(value="dataSource")DataSource dataSource){
    	return new TransactionAwareDataSourceProxy(dataSource);
    }
    @Primary
    @Bean(name="dataSource")
    public DataSource dataSource(@Qualifier(value="dataSourceSettings")DataSourceSettings dataSourceSettings){
        DruidDataSource ds = new DruidDataSource();
        
        try {
            ds.setDriverClassName(dataSourceSettings.getDriverClassName());
            ds.setUrl(dataSourceSettings.getUrl());
            ds.setUsername(dataSourceSettings.getUsername());
            ds.setPassword(dataSourceSettings.getPassword());
            ds.setFilters(dataSourceSettings.getFilters());
        } catch (SQLException e) {
            logger.error("Error creating data source",e);
        }
        return ds;
    }
    @Bean(name="readOnlyDataSource")
    public DataSource readOnlyDataSource(@Qualifier(value="readOnlyDataSourceSettings")DataSourceSettings readOnlyDataSourceSettings){
        DruidDataSource ds = new DruidDataSource();
        try {
            ds.setDriverClassName(readOnlyDataSourceSettings.getDriverClassName());
            ds.setUrl(readOnlyDataSourceSettings.getUrl());
            ds.setUsername(readOnlyDataSourceSettings.getUsername());
            ds.setPassword(readOnlyDataSourceSettings.getPassword());
            ds.setFilters(readOnlyDataSourceSettings.getFilters());
        } catch (SQLException e) {
            logger.error("Error creating data source",e);
        }
        return ds;
    }
}
