package com.centaline.parportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

//@ComponentScan(basePackages = { "com.centaline.parportal", "com.aist" })
@SpringBootApplication

@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class,
                                     JpaRepositoriesAutoConfiguration.class })
public class ParPortalApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ParPortalApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ParPortalApplication.class);
        application.run(args);

    }

}
