package com.centaline.trans.common.service.impl;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;



public class QuickQueryGetProaddrByCaseCodeServiceImpl implements CustomDictService{
	@Autowired
	private JdbcTemplate jdbcTemplate;
   
	private  String  jobCode;
    
    private static String sql = "SELECT PROPERTY_ADDR FROM sctrans.T_TO_PROPERTY_INFO WHERE CASE_CODE = ?";
	   
    
	@Override
	@Cacheable(value="QuickQueryGetProaddrByCaseCodeServiceImpl",key="#root.target+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {			   
	
		for (Map<String, Object> key : keys) {
			
			Object caseCode = key.get("CASE_CODE");
			if (caseCode != null) {
				List<String> proAddrList = jdbcTemplate.queryForList(sql, String.class, caseCode.toString());
				key.put("val", StringUtils.join(proAddrList, ""));
			}
		}
		return keys;
	}	
  
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	
	
}
