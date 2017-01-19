package com.centaline.parportal.conf;

import java.util.Properties;

import javax.sql.DataSource;

import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.centaline.trans.common.MyBatisRepository;

@Configuration
@Aspect
@MapperScan(basePackages = "com.centaline.trans.**.repository", annotationClass = MyBatisRepository.class)
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
@ConditionalOnBean(value = DeployContextConfiguration.class)
public class DataAccessConfiguration {
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier(value = "dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "txProperties")
    public Properties txProperties() {
        Properties properties = new Properties();
        return properties;
    }

    @Bean
    public TransactionInterceptor txAdvice(PlatformTransactionManager transactionManager,
                                           Properties txProperties) {
        return new TransactionInterceptor(transactionManager, txProperties);
    }

    @Bean
    public Advisor txAdviceAdvisor(TransactionInterceptor txAdvice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.centaline.trans..service..*+.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier(value = "dataSourceProxy") TransactionAwareDataSourceProxy dataSourceProxy) {
        return new JdbcTemplate(dataSourceProxy);
    }

    @Bean
    public QuickQueryJdbcTemplate quickQueryJdbcTemplate(@Qualifier(value = "readOnlyDataSourceProxy") TransactionAwareDataSourceProxy readOnlyDataSourceProxy) {
        return new QuickQueryJdbcTemplate(readOnlyDataSourceProxy);
    }

}
