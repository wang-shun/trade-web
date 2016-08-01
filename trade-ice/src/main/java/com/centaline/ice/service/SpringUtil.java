package com.centaline.ice.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.centaline.ice.gen.baseData.UserBaseDataPrx;

public class SpringUtil {
	
	private volatile static ApplicationContext applicationContext = null;
	
	public static void startSpringContex(){
		if(applicationContext != null){
			return ;
		}
		synchronized (SpringUtil.class) {
			if(applicationContext == null){
				String[] locations = {"classpath:spring/applicationContext.xml",
		                "classpath*:com/aist/uam/**/META-INF/beans.xml",
		                "classpath*:com/aist/common/**/META-INF/beans.xml",
		                "classpath*:com/centaline/trans/**/META-INF/core-beans.xml",
		                "classpath*:com/centaline/trans/**/META-INF/web-beans.xml",
		                "classpath*:com/aist/message/**/META-INF/beans.xml"};
				applicationContext = new ClassPathXmlApplicationContext(locations);
			}
			
			if(applicationContext instanceof ConfigurableApplicationContext){
				((ConfigurableApplicationContext)applicationContext).registerShutdownHook();
			}
		}
		
	}
	
	/**
     * 获取对象
     *
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws org.springframework.beans.BeansException
     *
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clz
     * @return
     * @throws org.springframework.beans.BeansException
     *
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        Assert.notNull(clz);
        T result = (T) applicationContext.getBean(clz);
        return result;
    }
    
    public static <T> T getBean(String name, Class<T> type){
    	Assert.hasText(name);
		Assert.notNull(type);
    	return applicationContext.getBean(name, type);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name) {
    	Assert.hasText(name);
        return applicationContext.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name
     * @return boolean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
    	Assert.hasText(name);
        return applicationContext.isSingleton(name);
    }

    /**
     * @param name
     * @return Class 注册对象的类型
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
    	Assert.hasText(name);
        return applicationContext.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name
     * @return
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
    	Assert.hasText(name);
        return applicationContext.getAliases(name);
    }

	public static void main(String[] args) {
		startSpringContex();
		
		UserBaseDataPrx u = applicationContext.getBean(UserBaseDataPrx.class);
		String val = u.getDictValue("10052", "10052");
		System.out.println(val);
		
	}

}
