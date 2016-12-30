package com.centaline.parportal.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration()
@ComponentScan(basePackages = { "com.centaline.trans", "com.aist.message" })
@ImportResource(value = { "classpath*:com/aist/message/**/META-INF/beans.xml" })
public class TradeCoreAutoConfiguration {

}
