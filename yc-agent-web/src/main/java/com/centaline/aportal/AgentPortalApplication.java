package com.centaline.aportal;

import java.util.Arrays;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.Ordered;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.opensymphony.sitemesh.webapp.SiteMeshFilter;


@SpringBootApplication
@ComponentScan(basePackages = { "com.aist.uam", "com.centaline.aportal" }, excludeFilters = {
		@ComponentScan.Filter(type=FilterType.REGEX, pattern = "com.aist.uam.basedata.Repository.impl.*") })
public class AgentPortalApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AgentPortalApplication.class);
		application.run(args);
	}

	@Bean
	public FilterRegistrationBean singleSignOutFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new SingleSignOutFilter());
		filterRegistrationBean.setName("CAS Single Sign Out Filter");
		filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));

		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean shiroFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		DelegatingFilterProxy dfp = new DelegatingFilterProxy();
		dfp.setBeanName("shiroFilter");
		dfp.setTargetFilterLifecycle(true);
		filterRegistrationBean.setFilter(dfp);
		filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
		filterRegistrationBean.setName("shiroFilter");
		return filterRegistrationBean;
	}

	// SingleSignOutHttpSessionListener
	@Bean
	public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignoutRegistrationBean() {
		ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> registrationBean = new ServletListenerRegistrationBean<>();
		registrationBean.setListener(new SingleSignOutHttpSessionListener());
		return registrationBean;
	}
	
}
