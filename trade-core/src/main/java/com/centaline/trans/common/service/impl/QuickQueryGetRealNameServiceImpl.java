/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper.BatchQuery;

/**
 * 
 * @author hejf10
 * @date
 */
public class QuickQueryGetRealNameServiceImpl implements CustomDictService{
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
   
	private  String  code;  
	
	private static String sql = "SELECT REAL_NAME FROM SCTRANS.SYS_USER WHERE ID =:userId";
	

    
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			Object userId= key.values().iterator().next();
			Map paramMap = new HashMap();
			paramMap.put("userId", userId);
			String realName1 = "";
			if(null != userId && !StringUtils.isBlank((String)userId)){
				realName1 = jdbcTemplate.queryForObject(sql, paramMap, String.class);
			}
			key.put("val", realName1);
		}
		return keys;
	}
  
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	
}
