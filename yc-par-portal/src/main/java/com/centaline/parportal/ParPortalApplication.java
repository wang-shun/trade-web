package com.centaline.parportal;

import java.util.Arrays;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

import org.springframework.web.filter.DelegatingFilterProxy;




@SpringBootApplication
public class ParPortalApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ParPortalApplication.class);
		application.run(args);
		
	}

	
}
