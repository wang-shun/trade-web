/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.centaline.parportal.mobile.interceptor.MobileSecurityInterceptor;

/**
 * 
 * @author sstonehu
 * @version $Id: WebApplicationConfig.java, v 0.1 2016年12月12日 上午4:46:52 sstonehu Exp $
 */
@Configuration
public class WebApplicationConfig extends WebMvcConfigurerAdapter {

    @Bean
    public HandlerInterceptor getMyInterceptor() {
        return new MobileSecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/mobile/**/**");
        super.addInterceptors(registry);
    }

}
