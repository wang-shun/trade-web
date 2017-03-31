package com.centaline.trans.common.service.impl;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;


/**
 * 查询案件渠道
 * @author hejf10
 *
 */
public class QuickQueryGetCaseOriginServiceImpl implements CustomDictService{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private  String  code;  
    private static String sql = " SELECT CASE_ORIGIN  FROM  sctrans.T_TO_CASE WHERE CASE_CODE=  ?";
      
    @Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {			   
		
		for (Map<String, Object> key : keys) {			
			Object loanAgentTeam = key.get("CASE_CODE");
			if (null != loanAgentTeam  && !StringUtils.isBlank((String)loanAgentTeam)) {				
				List<String> departmentNameList = jdbcTemplate.queryForList(sql, String.class, loanAgentTeam.toString());					
				key.put("val", StringUtils.join(departmentNameList, ""));	
			}
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
	public Boolean isCacheable(){
    	return false;
    }

}
