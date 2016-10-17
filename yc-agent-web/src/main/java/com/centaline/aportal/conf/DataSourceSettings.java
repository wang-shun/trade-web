package com.centaline.aportal.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by linjiarong on 2016/10/12.
 */
@Component
@ConfigurationProperties(prefix = "dataSource")
public class DataSourceSettings {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int maxActive;
    private int initialSize;
    private String filters;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActivie() {
        return maxActive;
    }

    public void setMaxActivie(int maxActivie) {
        this.maxActive = maxActivie;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }
}
