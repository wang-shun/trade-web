package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

public class QuickQueryEvaFeeServiceImpl implements CustomDictService {
	
	
    @Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if (CollectionUtils.isNotEmpty(keys)) {
			 List<String> caseCodeList = new ArrayList<String>();
			 for(Map<String, Object> m:keys){
				 //得到所有的参数
				 if(m.get("CASE_CODE")==null) {
					 continue;
				 }
				 caseCodeList.add(m.get("CASE_CODE").toString());
			 }
			 Map<String,Object> map = new HashMap<String,Object>();
			 map.put("caseCodes", caseCodeList);

			 String hql = "SELECT CASE_CODE,EVAL_FEE,RECORD_TIME FROM sctrans.T_TO_EVA_FEE_RECORD WHERE CASE_CODE IN (:caseCodes)";
				 
			 List<Map<String,Object>> result = jdbcTemplate.queryForList(hql, map);
			 
			/* if(CollectionUtils.isNotEmpty(result)){
				for(Map<String,Object> m: result){
					m.put(CustomDictService.DICTVALCOL, m.get("EVAL_FEE"));
				}
			 }*/
				 				 
			 return result;
		}
		return null;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
}
