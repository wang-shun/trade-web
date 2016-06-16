package com.centaline.ice.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aist.uam.basedata.remote.UamBasedataService;

public class SpringTest {

	public static void main(String[] args) {
		String[] locations = {"classpath:spring/applicationContext.xml",
				
                "classpath*:com/aist/uam/**/META-INF/beans.xml",
                "classpath*:com/aist/common/**/META-INF/beans.xml",
                "classpath*:com/centaline/trans/**/META-INF/core-beans.xml",
                "classpath*:com/centaline/trans/**/META-INF/web-beans.xml",
                "classpath*:com/aist/uam/auth/META-INF/shiro-beans.xml",
                "classpath*:com/aist/message/**/META-INF/beans.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(locations);
		UamBasedataService  bean = (UamBasedataService) ctx.getBean("uamBasedataServiceClient");
		System.out.println(bean.getDictValue("10052", "满二"));
		System.out.println("adsadd");
		
//		classpath*:com/aist/common/**/META-INF/beans.xml,
//		classpath*:com/aist/uam/**/META-INF/beans.xml,
//        classpath*:com/aist/uam/auth/META-INF/shiro-beans.xml,
//        classpath*:com/centaline/trans/**/META-INF/core-beans.xml,
//        classpath*:com/centaline/trans/**/META-INF/web-beans.xml,
//        classpath*:com/aist/message/**/META-INF/beans.xml
	}

}
