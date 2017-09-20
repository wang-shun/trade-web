package com.centaline.scheduler.conf;

import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@Aspect
@MapperScan(basePackages = "com.centaline.scheduler.**.service", annotationClass = Service.class)
@EnableTransactionManagement(proxyTargetClass = true)
@ConditionalOnBean(value = DeployContextConfiguration.class)
public class DataAccessConfiguration {
    @Bean("scpfJdbcTemplate")
    public JdbcTemplate scpfJdbcTemplate(@Qualifier(value = "scpfDataSourceProxy") TransactionAwareDataSourceProxy dataSourceProxy) {
        return new JdbcTemplate(dataSourceProxy);
    }
    @Bean("sctransJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier(value = "sctransDataSourceProxy") TransactionAwareDataSourceProxy dataSourceProxy) {
        return new JdbcTemplate(dataSourceProxy);
    }
}
