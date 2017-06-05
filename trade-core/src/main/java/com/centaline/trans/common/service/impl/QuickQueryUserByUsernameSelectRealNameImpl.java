package com.centaline.trans.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;


public class QuickQueryUserByUsernameSelectRealNameImpl implements CustomDictService{
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	private String prop;
	
	@Override
	@Cacheable(value="QuickQueryUserByUsernameSelectRealNameImpl",key="#root.target.getProp()+'/'+#key")
	public String getValue(String key) {
		User u = uamUserOrgService.getUserByUsername(key);
		String value = null;
		try {
			value = u.getRealName();
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
