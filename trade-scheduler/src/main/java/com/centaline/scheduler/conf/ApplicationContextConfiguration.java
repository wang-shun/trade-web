package com.centaline.scheduler.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ApplicationContextConfiguration {
	@Bean
	@Profile("dev")
	public PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurerDev() {
		ClassPathResource resource = new ClassPathResource("application-dev.yml");
		PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setLocation(resource);
		return propertyPlaceholderConfigurer;
	}

	@Bean
	@Profile("prod")
	public PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurerPrd() {
		ClassPathResource resource = new ClassPathResource("application-prod.yml");
		PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setLocation(resource);
		return propertyPlaceholderConfigurer;
	}
}
