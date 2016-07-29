package com.centaline.ice.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

public class ICEInvokeFactoryBean implements FactoryBean<Object>{

	private String iceProxy;
	
	public String getIceProxy() {
		return iceProxy;
	}

	public void setIceProxy(String iceProxy) {
		this.iceProxy = iceProxy;
	}

	private Object serviceProxy ;
	
	@Autowired
	private ICEUtil iceUtil;
	
	@Override
	public Object getObject() throws Exception {
		if(StringUtils.isBlank(iceProxy)){
			throw new RuntimeException("iceProxy 不能为空！");
		}
		this.serviceProxy = iceUtil.createIceProxy(getObjectType());
		return this.serviceProxy;
	}

	@Override
	public Class<?> getObjectType() {
		try{
			Class cls = Class.forName(iceProxy);
			return cls;
		}catch(Exception e){
			throw new RuntimeException("iceProxy 找不到类！");
		}
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
}
