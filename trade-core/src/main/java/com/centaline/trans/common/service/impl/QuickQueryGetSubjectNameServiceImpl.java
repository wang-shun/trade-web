/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

/**
 * 
 * @author 
 * @date
 */
public class QuickQueryGetSubjectNameServiceImpl implements CustomDictService{
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
   
	private  String  code;  
	
	private static String sql = "SELECT SUBJECT_NAME FROM sctrans.T_TS_COM_SUBJECT WHERE SUBJECT_CODE =:subjectCode";
	

    
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			Object subjectCode= key.values().iterator().next();
			Map paramMap = new HashMap();
			paramMap.put("subjectCode", subjectCode);
			String realName1 = "";
			if(null != subjectCode && !StringUtils.isBlank((String)subjectCode)){
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
