package com.centaline.trans.common.service.impl;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;



public class QuickQueryGetManagerByOrgIdAndJobCodeServiceImpl implements CustomDictService{
	@Autowired
	private JdbcTemplate jdbcTemplate;
   
	private  String  jobCode;
    
    private static String sql = "select REAL_NAME FROM sctrans.V_USER_ORG_JOB_ACTIVE WHERE ORG_ID = ? AND JOB_CODE = ?";
	   
    
	@Override
	@Cacheable(value="QuickQueryGetManagerByOrgIdAndJobCodeServiceImpl",key="#root.target.getJobCode()+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {			   
	
		for (Map<String, Object> key : keys) {
			
			Object orgId = key.get("ORG_ID");
			if (orgId != null) {
				List<String> managerList = jdbcTemplate.queryForList(sql, String.class, orgId.toString(), jobCode);
				key.put("val", StringUtils.join(managerList, ""));
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
