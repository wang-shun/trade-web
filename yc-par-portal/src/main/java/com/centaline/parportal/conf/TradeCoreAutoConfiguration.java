package com.centaline.parportal.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration()
@ComponentScan(basePackages = { "com.centaline.sctrans" })
//@ImportResource(value = { "classpath*:com/aist/common/**/META-INF/beans.xml" })
public class TradeCoreAutoConfiguration {

}
