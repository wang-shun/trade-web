package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;



public class QuickQueryCustomValueColorServiceImpl implements CustomDictService{
	
	@Override
	@Cacheable(value="QuickQueryCustomValueColorServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){
			String val = "";
			Object color = key.get("color");
			if(color!=null){
				val = "1".equals(color.toString())?"黄灯":"0".equals(color.toString())?"红灯":"红黄灯";
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
