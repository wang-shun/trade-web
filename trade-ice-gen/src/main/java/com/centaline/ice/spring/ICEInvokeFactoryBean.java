package com.centaline.ice.spring;

import org.springframework.beans.factory.FactoryBean;

public class ICEInvokeFactoryBean implements FactoryBean<Object>{

	private String iceProxy;
	
	public String getIceProxy() {
		return iceProxy;
	}

	public void setIceProxy(String iceProxy) {
		this.iceProxy = iceProxy;
	}

	public void setIceBeanFactory(ICEBeanFactory iceBeanFactory) {
		this.iceBeanFactory = iceBeanFactory;
	}

	private Object serviceProxy ;
	
	private ICEBeanFactory iceBeanFactory;
	
	@Override
	public Object getObject() throws Exception {
		if(null == iceProxy || "".equals(iceProxy.trim())){
			throw new RuntimeException("iceProxy 不能为空！");
		}
		this.serviceProxy = iceBeanFactory.createIceProxy(getObjectType());
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
