package com.centaline.trans.common.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;

public class QuickQueryParentOrgCustomDictServiceImpl implements CustomDictService {
	@Autowired
	private UamUserOrgService uamUserOrgService;

	private String prop;
	
	private String type;
	
	@Override
	@Cacheable(value="QuickQueryParentOrgCustomDictServiceImpl",key="#root.target.getProp()+'/'+#root.target.getType()+'/'+#key")
	public String getValue(String key) {
		Org u = uamUserOrgService.getParentOrgByDepHierarchy(key, type);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
