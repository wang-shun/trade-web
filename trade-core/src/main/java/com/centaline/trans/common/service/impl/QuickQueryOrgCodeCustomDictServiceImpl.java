package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;

public class QuickQueryOrgCodeCustomDictServiceImpl implements CustomDictService{
	
	@Autowired
	private UamUserOrgService uamUserOrgService;

	private String prop;
	
	@Override
	@Cacheable(value="QuickQueryOrgCodeCustomDictServiceImpl",key="#root.target.getProp()+'/'+#key")
	public String getValue(String key) {
		Org u = uamUserOrgService.getOrgByCode(key);
		String value = null;
		try {
			value = BeanUtils.getProperty(u, prop);
		} catch (Exception e) {
			return null;
		} 
		return value;
	}
	
	@Override
	@Cacheable(value="QuickQueryOrgCodeCustomDictServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> keyer:keys){
			String val = "";
			Object key = keyer.values().iterator().next();
			try {
				if(key!=null){
					Org u = uamUserOrgService.getOrgByCode(key.toString());
					val = BeanUtils.getProperty(u, prop);
				}
				
			} catch (Exception e) {
			} 
			keyer.put("val", val);
		}
		
		return keys;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}
}
