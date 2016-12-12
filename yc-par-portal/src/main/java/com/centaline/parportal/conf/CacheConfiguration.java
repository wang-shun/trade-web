package com.centaline.parportal.conf;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ComponentScan(basePackages="com.aist.common.redisson")
public class CacheConfiguration {

	/**jedis连接池*/
	@Bean
	@ConfigurationProperties(prefix="redisPoolConfig")
	public JedisPoolConfig poolConfig(){
		JedisPoolConfig jConfig=new JedisPoolConfig();
	
		return jConfig;
	}
	@Bean
	public MapPropertySource mapPropertySource(@Value("${redis.cluster.nodes}") String nodes,@Value("${redis.cluster.max-redirects}")String maxRedirects){
		Map<String, Object>source=new HashMap<>();
		source.put("spring.redis.cluster.nodes",nodes);
		source.put("spring.redis.cluster.max-redirects",maxRedirects);
		MapPropertySource mapProperty=new MapPropertySource("redisClusterConfig", source);
		return mapProperty;
	}
	@Bean
	public RedisClusterConfiguration redisClusterConfiguration(MapPropertySource mapPropertySource){
		RedisClusterConfiguration rCluster=new RedisClusterConfiguration(mapPropertySource);
		return rCluster;
	}
	@Bean
	public JedisConnectionFactory jedisConnFactory(RedisClusterConfiguration redisClusterConfiguration,JedisPoolConfig jedisPoolConfig){
		JedisConnectionFactory factory=new JedisConnectionFactory(redisClusterConfiguration,jedisPoolConfig);
		factory.setUsePool(true);
		return factory;
	}   
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory jedisConnFactory){
		RedisTemplate<Object, Object> template=new RedisTemplate<>();
		template.setConnectionFactory(jedisConnFactory);
		template.setDefaultSerializer(new JdkSerializationRedisSerializer());
		return template;
	} 
	@Bean
	public RedisCacheManager redisCacheManager(RedisTemplate<Object, Object>redisTemplate){
		RedisCacheManager cacheManager=new RedisCacheManager(redisTemplate);
		cacheManager.setDefaultExpiration(300L);
		cacheManager.setUsePrefix(false);
		return cacheManager;
	}
	
	
}
