package com.centaline.trans.common.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.service.TgGuestInfoService;



public class QuickQueryGetCustPhoneByCodeServiceImpl implements CustomDictService{
	
	@Autowired(required = true)
	TgGuestInfoService tgGuestInfoService;
	   
    
	@Override
	@Cacheable(value="QuickQueryGetCustPhoneByCodeServiceImpl",key="#root.target+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {			   
	
		for (Map<String, Object> key : keys) {			
			Object custCode = key.get("CUST_CODE");
			if (custCode != null) {
				TgGuestInfo  TgGuestInfo = tgGuestInfoService.findTgGuestInfoById(Long.parseLong(custCode.toString()));		
				if(TgGuestInfo != null){
					key.put("val", TgGuestInfo.getGuestPhone()==null ? "": TgGuestInfo.getGuestPhone());
				}				
			}
		}
		return keys;
	}	
  
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
}
