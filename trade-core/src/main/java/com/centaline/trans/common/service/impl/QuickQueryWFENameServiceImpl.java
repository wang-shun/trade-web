package com.centaline.trans.common.service.impl;


import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 根据businessKey查询模版名
 * @author caoy
 *
 */
public class QuickQueryWFENameServiceImpl implements CustomDictService{
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
    private static String sql = "select WFE_NAME from sctrans.SYS_WFE_TEMPLATE where WFE_CODE=:code";
      
    @Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {			   
		
		for (Map<String, Object> key : keys) {
			if(key==null){
				continue;
			}
			Object business_key = key.get("BUSINESS_KEY");
			if (!StringUtils.isBlank((String)business_key)) {
				Map paramMap = new HashMap();
				paramMap.put("code", (String) business_key);
				List<Map<String, Object>>  list = jdbcTemplate.queryForList(sql,paramMap);
				if(list.size()!=0){
					key.put("val", list.get(0).get("WFE_NAME"));
				}else{
					key.put("val", null);
				}
			}
		}
		return keys;
	}	
  
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	public Boolean isCacheable(){
    	return false;
    }

}
