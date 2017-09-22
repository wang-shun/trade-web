package com.centaline.api;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * api service测试父类 减少注解的重复添加
 * @author yinchao
 * @date 2017/9/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml",
		"classpath*:com/aist/common/**/META-INF/beans.xml",
		"classpath*:com/aist/uam/**/META-INF/httpInvoke-beans.xml",
		"classpath*:com/aist/uam/**/META-INF/beans.xml",
		"classpath*:com/aist/uam/auth/META-INF/shiro-beans.xml",
		"classpath*:com/centaline/trans/**/META-INF/core-beans.xml",
		"classpath*:com/centaline/trans/**/META-INF/web-beans.xml",
		"classpath*:com/aist/message/**/META-INF/beans.xml"})
@ActiveProfiles({"development","jedis"})
public abstract class AbstractServiceTest {
}
