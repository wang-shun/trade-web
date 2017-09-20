package com.centaline.scheduler.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 该类中配置了交易系统的
 * 权限管理平台 和 交易系统 2个数据库
 * 用动态切换的形式 来进行切换和使用
 * @see DataSourceContextHolder
 * @see MultipleDataSource
 */
@Configuration
public class DeployContextConfiguration {
	
    private final Logger logger = LoggerFactory.getLogger(DeployContextConfiguration.class);
    
    //////////////数据库连接配置////////////////
    /** 权限管理平台 数据库连接配置 *******************************************/
    @Bean(name="scpfDataSourceSettings")
    @ConfigurationProperties(prefix = "dataSource.scpf")
    public DataSourceSettings scpfDataSourceSettings(){
    	return new DataSourceSettings();
    }
    @Bean(name="scpfDataSourceProxy")
    public TransactionAwareDataSourceProxy scpfDataSourceProxy(@Qualifier(value="scpfDataSource")DataSource dataSource){
    	return new TransactionAwareDataSourceProxy(dataSource);
    }
    @Primary
    @Bean(name="scpfDataSource")
    public DataSource scpfDataSource(@Qualifier(value="scpfDataSourceSettings")DataSourceSettings dataSourceSettings){
        DruidDataSource ds = new DruidDataSource();
        try {
            ds.setDriverClassName(dataSourceSettings.getDriverClassName());
            ds.setUrl(dataSourceSettings.getUrl());
            ds.setUsername(dataSourceSettings.getUsername());
            ds.setPassword(dataSourceSettings.getPassword());
            ds.setFilters(dataSourceSettings.getFilters());
        } catch (SQLException e) {
            logger.error("Error creating scpf data source",e);
        }
        return ds;
    }

    /** 交易系统 数据库连接配置 *******************************************/
    @Bean(name="sctransDataSourceSettings")
    @ConfigurationProperties(prefix = "dataSource.sctrans")
    public DataSourceSettings sctransDataSourceSettings(){
        return new DataSourceSettings();
    }
    @Bean(name="sctransDataSourceProxy")
    public TransactionAwareDataSourceProxy sctransDataSourceProxy(@Qualifier(value="sctransDataSource")DataSource dataSource){
        return new TransactionAwareDataSourceProxy(dataSource);
    }
    @Bean(name="sctransDataSource")
    public DataSource sctransDataSource(@Qualifier(value="sctransDataSourceSettings")DataSourceSettings dataSourceSettings){
        DruidDataSource ds = new DruidDataSource();
        try {
            ds.setDriverClassName(dataSourceSettings.getDriverClassName());
            ds.setUrl(dataSourceSettings.getUrl());
            ds.setUsername(dataSourceSettings.getUsername());
            ds.setPassword(dataSourceSettings.getPassword());
            ds.setFilters(dataSourceSettings.getFilters());
            ds.setMaxActive(dataSourceSettings.getMaxActive());
            ds.setInitialSize(dataSourceSettings.getInitialSize());
        } catch (SQLException e) {
            logger.error("Error creating sctrans data source",e);
        }
        return ds;
    }

}
