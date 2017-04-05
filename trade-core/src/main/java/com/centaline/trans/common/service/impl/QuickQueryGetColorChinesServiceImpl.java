package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;

public class QuickQueryGetColorChinesServiceImpl implements CustomDictService{
	
	@Override
	@Cacheable(value="QuickQueryGetColorChinesServiceImpl",key="#root.targetClass+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			Object yellow= key.get("yellow");
			Object red= key.get("red");
			String realName1 = "";
			if(null != yellow && yellow.equals(1)){
				realName1 = "黄灯";
			}
			if(null != red && red.equals(1)){
				realName1 = "红灯";
			}
			key.put("val", realName1);
		}
		return keys;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
}
