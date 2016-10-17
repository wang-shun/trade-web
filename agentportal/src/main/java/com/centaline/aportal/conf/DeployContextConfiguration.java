package com.centaline.aportal.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by linjiarong on 2016/10/11.
 *
 */
@Configuration
public class DeployContextConfiguration {

    private final Logger logger = LoggerFactory.getLogger(DeployContextConfiguration.class);

    @Autowired
    private DataSourceSettings dataSourceSettings;

    @Bean
    public DataSource getDataSource(){
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
}
