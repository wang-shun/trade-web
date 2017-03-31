package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;

public class QuickQuerySrvPartServiceImpl implements CustomDictService {

	@Override
	@Cacheable(value="QuickQuerySrvPartServiceImpl",key="#root.targetClass + #root.methodName")
	public String getValue(String[] key) {

		if (null == key || key.length != 2) {
			return "";
		}

		String in = key[0];
		String total = key[1];
		
		in = in.indexOf(".") > 0 ? in.substring(0, in.indexOf(".")) : in;
		total = total.indexOf(".") > 0 ? total.substring(0, total.indexOf(".")) : total;
		
		String val = in + "/" + total;
		return val;
	}
	
	@Override
	@Cacheable(value="QuickQuerySrvPartServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){
			String val = "";
			if(null==key || key.size()!=2){
				key.put("val", val);
				continue;
			}
			try{
				String in = key.get("SRV_PART_IN").toString();
				String total = key.get("SRV_PART_TOTAL").toString();
				in = in.indexOf(".") > 0 ? in.substring(0, in.indexOf(".")) : in;
				total = total.indexOf(".") > 0 ? total.substring(0, total.indexOf(".")) : total;
				val = in + "/" + total;
				key.put("val", val);
			}catch(Exception e){
				key.put("val", val);
			}
			
		}
		
		return keys;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	


}
