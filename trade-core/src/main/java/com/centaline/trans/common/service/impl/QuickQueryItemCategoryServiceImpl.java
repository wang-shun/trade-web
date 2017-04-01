package com.centaline.trans.common.service.impl;

import com.aist.common.quickQuery.service.CustomDictService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;


public class QuickQueryItemCategoryServiceImpl implements CustomDictService {
	
	
	
	@Override
	@Cacheable(value="QuickQueryItemCategoryServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){			
			String val = "";
			Object ItemCategory = key.get("ITEM_CATEGORY");		
			if(ItemCategory!=null){				
				if("carded".equals(ItemCategory.toString())){
					val="身份证";
				}else if("bankCard".equals(ItemCategory.toString())){
					val="银行卡";
				}else if("propertyCard".equals(ItemCategory.toString())){
					val="产权证";
				}else if("otherCard".equals(ItemCategory.toString())){
					val="它证";
				}
				
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
