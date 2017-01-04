package com.centaline.aportal.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ApplicationContextConfiguration {
	@Autowired
    public Environment env;
	@Bean
	@Profile("dev")
	public PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurerDev() {
		ClassPathResource resource = new ClassPathResource("application-dev.properties");
		PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setLocation(resource);
		return propertyPlaceholderConfigurer;
	}
	@Bean
	@Profile("prod")
	public PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurerPrd() {
		ClassPathResource resource = new ClassPathResource("application-prod.properties");
		PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setLocation(resource);
		return propertyPlaceholderConfigurer;
	}
}
