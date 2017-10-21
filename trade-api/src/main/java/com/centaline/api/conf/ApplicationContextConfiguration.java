package com.centaline.api.conf;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ApplicationContextConfiguration {

	@Bean
	@Profile("dev")
	public PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurerDev() {
		ClassPathResource resource = new ClassPathResource("application-dev.properties");
		PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setLocation(resource);
		propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		return propertyPlaceholderConfigurer;
	}

	@Bean
	@Profile("production")
	public PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurerPrd() {
		ClassPathResource resource = new ClassPathResource("application-prod.properties");
		PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setLocation(resource);
		propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		return propertyPlaceholderConfigurer;
	}

	@Bean
	public FilterRegistrationBean loggingFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new com.github.isrsal.logging.LoggingFilter());
		registration.addUrlPatterns("/api/*");//只记录api的接口信息 否则日志太多
		return registration;
	}
}
