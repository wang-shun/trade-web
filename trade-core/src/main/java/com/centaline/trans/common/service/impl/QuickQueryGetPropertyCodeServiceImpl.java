package com.centaline.trans.common.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;


/**
 * 查询案件渠道
 * @author hejf10
 *
 */
public class QuickQueryGetPropertyCodeServiceImpl implements CustomDictService{
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
   
	private  String  code;  
	
    private static String sql = " select PROPERTY_CODE from sctrans.T_TO_PROPERTY_INFO  where CASE_CODE=:caseCode";
    
      
    @Override
	@Cacheable(value="QuickQueryGetPropertyCodeServiceImpl",key="#root.target+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {			   
		
		for (Map<String, Object> key : keys) {			
			Object caseCode= key.values().iterator().next();
			Map paramMap = new HashMap();
			paramMap.put("caseCode", caseCode);
			String realName1 = "";
			if(null != caseCode && !StringUtils.isBlank((String)caseCode) ){
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
