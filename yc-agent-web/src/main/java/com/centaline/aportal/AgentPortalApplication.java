package com.centaline.aportal;

import java.util.Arrays;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.DelegatingFilterProxy;

@ComponentScan(basePackages = { "com.centaline.aportal.*", "com.centaline.trans.*" })
@SpringBootApplication
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class ,JpaRepositoriesAutoConfiguration.class})
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
