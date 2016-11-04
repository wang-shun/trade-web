package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;



public class QuickQueryReturnVisitRemarkServiceImpl implements CustomDictService{
	
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){
			String val = "未处理";
			Object visitRemark = key.get("VISIT_REMARK");
			if(visitRemark!=null){
				val = "1".equals(visitRemark.toString())?"正常":"0".equals(visitRemark.toString())?"异常":"";
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
