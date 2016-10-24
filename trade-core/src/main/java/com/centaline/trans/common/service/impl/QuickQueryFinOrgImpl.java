package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;

public class QuickQueryFinOrgImpl implements CustomDictService{
	
	@Autowired
	private TsFinOrgService finOrgService;

	private String prop;
	
	@Override
	@Cacheable(value="QuickQueryFinOrgImpl",key="#root.target.getProp()+'/'+#key")
	public String getValue(String key) {
		TsFinOrg finOrg = finOrgService.findBankByFinOrg(key);
		String value = null;
		try {
			value = BeanUtils.getProperty(finOrg, prop);
		} catch (Exception e) {
			return null;
		} 
		return value;
	}
	
	@Override
	@Cacheable(value="QuickQueryFinOrgImpl",key="#root.target.getProp()+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> keyer:keys){
			String val = "";
			try {
				Object key = keyer.values().iterator().next();
				if(key!=null){
					TsFinOrg finOrg = finOrgService.findBankByFinOrg(key.toString());
					val = BeanUtils.getProperty(finOrg, prop);
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
