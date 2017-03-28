package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.UserOrgJob;

public class QuickQueryUOJByUserIdCustomerDictServiceImpl implements CustomDictService{
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	private String prop;
	

	@Override
	public String getValue(String key) {
		
		List<UserOrgJob> uojList = uamUserOrgService.getUserOrgJobByUserId(key);
		if("orgName".equals(prop)){
			List<String> orgNameList = new ArrayList<String>();
			for(UserOrgJob uoj : uojList){
				orgNameList.add(uoj.getOrgName());
			}
			return StringUtils.join(orgNameList.toArray(),"/");
		}
		return key;
	}
	
	@Override
	@Cacheable(value="QuickQueryUOJByUserIdCustomerDictServiceImpl",key="#root.target.getProp()+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> keyer:keys){
			String val = "";
			Object key = keyer.values().iterator().next();
			if(key!=null){
				List<UserOrgJob> uojList = uamUserOrgService.getUserOrgJobByUserId(key.toString());
				if("orgName".equals(prop)){
					List<String> orgNameList = new ArrayList<String>();
					for(UserOrgJob uoj : uojList){
						orgNameList.add(uoj.getOrgName());
					}
					val = StringUtils.join(orgNameList.toArray(),"/");
				}
			}
			
			keyer.put("val", val);
		}
		
		return keys;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}
	
	public String getProp() {
		return prop;
	}
	
	public static void main(String[] args){
		System.out.println(StringUtils.join(new String[]{"a"}, "/") );
	}
}
