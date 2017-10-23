package com.centaline.api.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bobin on 2017/3/1.
 */
@Configuration
@ComponentScan
@Profile("jedis")
public class JedisConfiguration {

    @Value("${redis.cluster.nodes}")
    private String redisnodes;

    @Value("${redis.cluster.max-redirects}")
    private String maxredircts;

    @Value("${redis.maxIdle}")
    private int maxidle;

    @Value("${redis.maxTotal}")
    private int maxTotal;

    @Bean
    public RedisClusterConfiguration redisClusterConfiguration()
    {
        Map<String,Object> props=new HashMap<>();
        props.put("spring.redis.cluster.nodes",redisnodes);
        props.put("spring.redis.cluster.max-redirects",maxredircts);
        MapPropertySource mapPropertySource=new MapPropertySource("redisClusterConfig",props);
        return new RedisClusterConfiguration(mapPropertySource);
    }
    
    @Bean
    public JedisPoolConfig jedisPoolConfig()
    {
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxIdle(maxidle);
        poolConfig.setMaxTotal(maxTotal);
        return  poolConfig;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration,
                                                         JedisPoolConfig jedisPoolConfig)
    {
        return  new JedisConnectionFactory(redisClusterConfiguration,jedisPoolConfig);
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory)
    {
        RedisTemplate redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "shiroSessionRedisTemplate")
    public RedisTemplate shiroSessionRedisTemplate(JedisConnectionFactory jedisConnectionFactory)
    {
        RedisTemplate redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "redisTemplateInfo")
    public RedisTemplate redisTemplateInfo(JedisConnectionFactory jedisConnectionFactory)
    {
        RedisTemplate redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }
    @Bean(name = "quickQueryRedisTemplate")
    public RedisTemplate quickQueryRedisTemplate(JedisConnectionFactory jedisConnectionFactory)
    {
        RedisTemplate redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }


    @Bean(name = "redisCacheManager")
    public CacheManager redisCacheManager(@Qualifier("redisTemplate") RedisTemplate redisTemplate)
    {
        RedisCacheManager cacheManager=new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        cacheManager.setDefaultExpiration(600);
        return cacheManager;
    }
    @Bean(name = "shiroSessionRedisManager")
    public CacheManager shiroSessionRedisManager(@Qualifier("shiroSessionRedisTemplate") RedisTemplate redisTemplate)
    {
        RedisCacheManager cacheManager=new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        cacheManager.setDefaultExpiration(36000);
        return cacheManager;
    }

    @Bean(name = "redisLongCacheManager")
    public CacheManager redisLongCacheManager(@Qualifier("redisTemplateInfo") RedisTemplate redisTemplate)
    {
        RedisCacheManager cacheManager=new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        cacheManager.setDefaultExpiration(7200);
        return cacheManager;
    }




}
