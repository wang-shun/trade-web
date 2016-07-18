package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.CustomDictService;



public class QuickQueryCustomValueColorServiceImpl implements CustomDictService{
	
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){
			String val = "";
			Object color = key.get("color");
			if(color!=null){
				val = "1".equals(color.toString())?"红灯":"0".equals(color.toString())?"黄灯":"红黄灯";
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
