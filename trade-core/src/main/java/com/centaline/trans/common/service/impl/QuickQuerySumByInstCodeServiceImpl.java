package com.centaline.trans.common.service.impl;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;



public class QuickQuerySumByInstCodeServiceImpl implements CustomDictService{
	@Autowired
	private JdbcTemplate jdbcTemplate;  

    private static String sql = "SELECT TOP 1 AT.START_TIME_ FROM sctrans.ACT_HI_TASKINST AT WHERE AT.TASK_DEF_KEY_='Guohu'  AND AT.PROC_INST_ID_= ?  ORDER BY AT.START_TIME_";

	       
	@Override
	@Cacheable(value="QuickQuerySumByInstCodeServiceImpl",key="#root.target+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {			   
	
		for (Map<String, Object> key : keys) {			
			Object instCode = key.get("INST_CODE");
			if (instCode != null) {
				List<String> managerList = jdbcTemplate.queryForList(sql, String.class, instCode.toString());
				key.put("val", StringUtils.join(managerList, ""));
			}
		}		
		return keys;
	}	
  
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
}
