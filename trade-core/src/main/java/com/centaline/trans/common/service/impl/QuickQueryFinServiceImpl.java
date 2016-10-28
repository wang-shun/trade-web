package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;

public class QuickQueryFinServiceImpl implements CustomDictService {

	@Override
	@Cacheable(value="QuickQueryFinServiceImpl",key="#root.targetClass + #root.methodName")
	public String getValue(String[] key) {

		if (null == key || key.length != 2) {
			return "";
		}

		String finOrder = key[0];
		String finOrderRoll = key[1];
		if(StringUtils.isBlank(finOrder) && StringUtils.isBlank(finOrderRoll)){
			return "";
		}
		String val = finOrder + "+" + finOrderRoll;
		return val;
	}
	
	
	@Override
	@Cacheable(value="QuickQueryFinServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){
			String val = "";
			if(null==key || key.size()!=2){
				key.put("val", val);
				continue;
			}
			String finOrder = key.get("FIN_ORDER").toString();
			String finOrderRoll = key.get("FIN_ORDER_ROLL").toString();
			if(StringUtils.isBlank(finOrder) && StringUtils.isBlank(finOrderRoll)){
				
			}else{
				val = finOrder + "+" + finOrderRoll;
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
