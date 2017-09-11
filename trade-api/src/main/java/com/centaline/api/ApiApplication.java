package com.centaline.api;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 启动类
 * 继承 SpringBootServletInitializer 并重写 configure 方法
 * 否则最终package 的war包，无法在tomcat中进行部署
 * @author yinchao
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class,
                                     JpaRepositoriesAutoConfiguration.class })
public class ApiApplication extends SpringBootServletInitializer
{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //指向原有@SpringBootApplication的启动类
		return application.sources(ApiApplication.class);
    }

    public static void main(String[] args) {
    	SpringApplicationBuilder builder = new SpringApplicationBuilder(ApiApplication.class);
		builder.bannerMode(Mode.OFF);//关闭Banner 或者 修改resources banner.txt中的内容进行替换
		builder.run(args);

    }
}
