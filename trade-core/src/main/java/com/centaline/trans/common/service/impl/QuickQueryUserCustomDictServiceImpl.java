package com.centaline.trans.common.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;

public class QuickQueryUserCustomDictServiceImpl implements CustomDictService{
	
	@Autowired
	private UamUserOrgService uamUserOrgService;

	private String prop;
	
	@Override
	@Cacheable(value="QuickQueryUserCustomDictServiceImpl",key="#root.target.getProp()+'/'+#key")
	public String getValue(String key) {
		User u = uamUserOrgService.getUserById(key);
		String value = null;
		try {
			value = BeanUtils.getProperty(u, prop);
		} catch (Exception e) {
			return null;
		} 
		return value;
	}
	
	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}
}
