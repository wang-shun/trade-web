package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;

public class QuickQueryMKpiCustomDictServiceImpl implements CustomDictService{

	@Override
	public String getValue(String key) {
		if("0".equals(key)) {
			return "否";
		} else if("1".equals(key)){
			return "是";
		} else {
			return null;
		}
	}
	
	@Override
	@Cacheable(value="QuickQueryMKpiCustomDictServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){
			String val = "";
			Object status = key.get("status");
			if(status!=null){
				val = "1".equals(status.toString())?"是":"否";
			}
			key.put("val", val);
		}
		
		return keys;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
	
	
}
