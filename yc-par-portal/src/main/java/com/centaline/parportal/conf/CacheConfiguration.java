package com.centaline.parportal.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="com.aist.common.conf.Redisson")
public class CacheConfiguration {

	/**jedis连接池*//*
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
	}*/
	
	
}
