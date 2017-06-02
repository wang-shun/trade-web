package com.centaline.trans.common.service.impl;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;



public class QuickQueryGetGbdepartmentServiceImpl implements CustomDictService{
	@Autowired
	private JdbcTemplate jdbcTemplate;
   
	private  String  code;  
	
    private static String sqlDepartment = "SELECT  ORG2.ORG_NAME FROM SCTRANS.SYS_ORG ORG1	LEFT JOIN SCTRANS.SYS_ORG ORG2 	ON ORG2.ID = ORG1.PARENT_ID WHERE ORG1.PARENT_ID IN('FF5BC56E0E4B45289DAA5721A494C7C5','8a8493d453141f3301532c9568011af9','d5878adf8b0c4032aeae895c701ed693','de81a98a8ec341caa8e9bc77c46cd370','54b3233fdf994d9987e7c8febc223588','960089538bc54a9a9139ef52ceb45b33','6114AB949B4445828D4383977C4FAC71','6a84979158b942b78a8a5921cc30b8c3') "
    									+ "AND ORG1.ID = ?";
    
      
    @Override
	@Cacheable(value="QuickQueryGetGbdepartmentServiceImpl",key="#root.target+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {			   
		
		for (Map<String, Object> key : keys) {			
			Object loanAgentTeam = key.get("LOAN_AGENT_TEAM");
			if (loanAgentTeam != null) {				
				List<String> departmentNameList = jdbcTemplate.queryForList(sqlDepartment, String.class, loanAgentTeam.toString());					
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


}
