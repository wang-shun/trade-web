package com.centaline.api.conf;

import com.aist.common.cache.CustomKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@ComponentScan
@EnableCaching(proxyTargetClass = true)
public class CacheConfiguration  extends CachingConfigurerSupport {

	@Autowired
    @Qualifier("redisCacheManager")
    private CacheManager cacheManager;


    @Override
    public CacheManager cacheManager() {
        return cacheManager;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return customKeyGenerator();
    }

    @Bean(name = "customKeyGenerator")
    public KeyGenerator customKeyGenerator()
    {
        return new CustomKeyGenerator();
    }
}
