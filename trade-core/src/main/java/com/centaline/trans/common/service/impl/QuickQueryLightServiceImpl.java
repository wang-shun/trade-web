package com.centaline.trans.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.CustomDictService;
import com.centaline.trans.common.enums.LampEnum;

@Service
public class QuickQueryLightServiceImpl implements CustomDictService{
	

	public List<Map<String,Object>> findDicts(List<Map<String,Object>> keys){
		if(keys == null) {
			return null;
		}
		for (Map<String, Object> map : keys) {
			Object todoTime = map.get("todoTime");
			if(null == todoTime || !(todoTime instanceof Date)) {
				map.put(DICTVALCOL, LampEnum.GREEN.toString());
				continue;
			}
			Date date = (Date)todoTime;
			long diff = new Date().getTime() - date.getTime();//这样得到的差值是微秒级别  
		    long days = diff / (1000 * 60 * 60 * 24);  
			
		    String val = "";
		    
		    if(days < Long.valueOf(LampEnum.GREEN.getCode())) {
		    	val = LampEnum.GREEN.toString();
		    }else if (days < Long.valueOf(LampEnum.YELLOW.getCode())) {
		    	val = LampEnum.YELLOW.toString();
			}else {
				val = LampEnum.RED.toString();
			}
			
		    map.put(DICTVALCOL, val);
		}
    	return keys;
    }
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
	public Boolean isCacheable(){
		return false;
	}
}
