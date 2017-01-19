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
public class QuickQueryGetMegerCuntServiceImpl implements CustomDictService{
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
   
	private  String  code;  
	
	private static String sql = "select count(*) cu from sctrans.T_TO_CASE c left join sctrans.T_TO_PROPERTY_INFO p on c.CASE_CODE=p.CASE_CODE  where c.CREATE_TIME>DATEADD(mm,-6,GETDATE()) and c.CASE_ORIGIN != 'MERGE' and c.CASE_ORIGIN !='PROCESS'   and c.CASE_PROPERTY != '30003001' and c.STATUS != '30001007'  and p.PROPERTY_CODE = :propertyCode";
	

    
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			Object propertyCode= key.values().iterator().next();
			Map paramMap = new HashMap();
			paramMap.put("propertyCode", propertyCode);
			String realName1 = "";
			if(null != propertyCode && !StringUtils.isBlank((String)propertyCode)){
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
