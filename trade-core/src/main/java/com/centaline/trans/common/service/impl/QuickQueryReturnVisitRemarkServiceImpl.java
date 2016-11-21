package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import com.aist.common.quickQuery.service.CustomDictService;



public class QuickQueryReturnVisitRemarkServiceImpl implements CustomDictService{
	
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> key:keys){
			String val = "未回访";
			Object visitRemark = key.get("LAST_VISIT_REMARK");
			if(visitRemark!=null){
				val = "1".equals(visitRemark.toString())?"正常":"0".equals(visitRemark.toString())?"异常":"2".equals(visitRemark.toString())?"下次处理":"未回访";
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
