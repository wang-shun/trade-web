package com.centaline.trans.common.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;

public class QuickQueryProsUserCustomDictServiceImpl implements CustomDictService{
	
	@Autowired
	private UamUserOrgService uamUserOrgService;

	private String prop;
	private Boolean flag;
	
	@Override
	@Cacheable(value="QuickQueryProsUserCustomDictServiceImpl",key="#root.target.getProp()+'/'+#key")
	public String getValue(String key) {
		
		if(StringUtils.isEmpty(key))return null;
		String[] strs = key.split("/");
		StringBuffer reStrs = new StringBuffer();
		for(String s:strs){
			if(StringUtils.isEmpty(s))continue;
			User u = uamUserOrgService.getUserById(s);
			String value = null;
			try {
				value = BeanUtils.getProperty(u, prop);
				if(flag){
					if(reStrs.indexOf(value)== -1){
						reStrs.append(value);
						reStrs.append("/");
					}
				}else {
					reStrs.append(value);
					reStrs.append("/");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} 
		} 
		if(StringUtils.isEmpty(reStrs))return null;
		reStrs.deleteCharAt(reStrs.length()-1); 
		return reStrs.toString();
		
	}
	
	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}
