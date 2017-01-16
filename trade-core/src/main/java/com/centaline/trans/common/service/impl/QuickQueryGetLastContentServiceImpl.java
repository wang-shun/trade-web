package com.centaline.trans.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

public class QuickQueryGetLastContentServiceImpl implements CustomDictService{
	
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;

	private static String CASE_SQL = "SELECT  TOP 1 CONTENT,NOT_APPROVE,OPERATOR  FROM  SCTRANS.T_TO_APPROVE_RECORD AR3 WHERE  AR3.PART_CODE='GuohuApprove'  AND AR3.CASE_CODE = ? ORDER BY  AR3.OPERATOR_TIME  DESC";

    
    @Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {			   
		
		for (Map<String, Object> key : keys) {			
			Object caseCode = key.get("CASE_CODE");
			if (caseCode != null) {				
				//List<Map<String, Object>> list = jdbcTemplate.queryForList(CASE_SQL.toString(), String.class,caseCode.toString());
			}
		}
		
		return keys;
	}

	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
}
