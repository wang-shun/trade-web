package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

public class QuickQueryEvaFeeServiceImpl implements CustomDictService {
	
	
    @Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	@Cacheable(value="QuickQueryEvaFeeServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if (CollectionUtils.isNotEmpty(keys)) {
			 List<Map<String,Object>> totalList = new ArrayList<Map<String,Object>>();
			 List<String> caseCodeList1 = new ArrayList<String>();
			 int i = 0;
			 for(Map<String, Object> m:keys){
				 //得到所有的参数
				 if(m.get("CASE_CODE")==null) {
					 continue;
				 }
				 caseCodeList1.add(m.get("CASE_CODE").toString());
				 i++;
				 
				 if(i==1000) {
					 
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("caseCodes", caseCodeList1);
					 
					 String hql = "SELECT CASE_CODE,EVAL_FEE,CONVERT(varchar(100), RECORD_TIME, 23) AS RECORD_TIME FROM sctrans.T_TO_EVA_FEE_RECORD WHERE CASE_CODE IN (:caseCodes)";
					 
					 List<Map<String,Object>> result1 = jdbcTemplate.queryForList(hql, map);
					 
					 totalList.addAll(result1);
					 
					 caseCodeList1.clear();
					 
					 i = 0 ;
				 }
			 }
			 
			 if(CollectionUtils.isNotEmpty(caseCodeList1)) {
				 Map<String,Object> map = new HashMap<String,Object>();
				 map.put("caseCodes", caseCodeList1);
				 String hql = "SELECT CASE_CODE,EVAL_FEE,CONVERT(varchar(100), RECORD_TIME, 23) AS RECORD_TIME FROM sctrans.T_TO_EVA_FEE_RECORD WHERE CASE_CODE IN (:caseCodes)";
					 
				 List<Map<String,Object>> result1 = jdbcTemplate.queryForList(hql, map);
				 totalList.addAll(result1);
			 }
			 
			/* if(CollectionUtils.isNotEmpty(result)){
				for(Map<String,Object> m: result){
					m.put(CustomDictService.DICTVALCOL, m.get("EVAL_FEE"));
				}
			 }*/
				 				 
			 return totalList;
		}
		return null;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
}
