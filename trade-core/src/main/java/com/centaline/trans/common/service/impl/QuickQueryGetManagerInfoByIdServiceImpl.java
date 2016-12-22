package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;

public class QuickQueryGetManagerInfoByIdServiceImpl implements CustomDictService{
	
	
	@Autowired
	private UamUserOrgService uamUserOrgService;	
	
	@Override
	@Cacheable(value="QuickQueryGetManagerInfoByIdServiceImpl",key="#root.targetClass+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key : keys){		
			try {
				Object itemManager = key.values().iterator().next();			
				if(itemManager!=null){
					User user = uamUserOrgService.getUserById(itemManager.toString());
					if(user != null){					
						key.put("ITEM_MANAGER_REAL_NAME",  user.getRealName()==null?"":user.getRealName());
						key.put("ITEM_MANAGER_ORG_NAME",  user.getMobile()==null?"":user.getMobile());
						key.put("ITEM_MANAGER_MOBILE",  user.getOrgName()==null?"":user.getOrgName());
					}				
				}
				
			} catch (Exception e) {	
				key.put("ITEM_MANAGER_REAL_NAME", "");
				key.put("ITEM_MANAGER_ORG_NAME", "");
				key.put("ITEM_MANAGER_MOBILE", "");
			} 
			
		}
		
		return keys;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
}
