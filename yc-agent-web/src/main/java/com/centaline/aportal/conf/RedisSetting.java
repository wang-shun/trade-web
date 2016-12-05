package com.centaline.aportal.conf;

import java.util.ArrayList;
import java.util.List;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix = "redisSetting")
public class RedisSetting {
	private String master;
	private org.springframework.data.redis.connection.RedisNode MASTER;
	private List<RedisNode> sentinels;
	private List<org.springframework.data.redis.connection.RedisNode>SENTINELS;
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public org.springframework.data.redis.connection.RedisNode getMasterSpringBean(){
		if(MASTER!=null)return MASTER;
		else if(master!=null){
			MASTER = new org.springframework.data.redis.connection.RedisNode(null,1);
			MASTER.setName(master);
		}
		return MASTER;
	}
	
	public List<RedisNode> getSentinels(){
		return this.sentinels;
	}
	
	public List<org.springframework.data.redis.connection.RedisNode> getSpringSentinels() {
		if(SENTINELS!=null){
			return SENTINELS;
		}else if(sentinels!=null){
			SENTINELS=new ArrayList<>();
			for (RedisNode redisNode : sentinels) {
				SENTINELS.add(new org.springframework.data.redis.connection.RedisNode(redisNode.getHost(), redisNode.getPort()));
			}
		}
		return SENTINELS;
	}
	public void setSentinels(List<RedisNode> sentinels) {
		this.sentinels = sentinels;
	}
	
}

