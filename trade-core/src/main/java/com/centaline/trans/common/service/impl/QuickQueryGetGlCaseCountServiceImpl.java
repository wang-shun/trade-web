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
 * @author hejf10
 * @date
 */
public class QuickQueryGetGlCaseCountServiceImpl implements CustomDictService{
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
   
	private  String  code;  
	
	private static String sql0 = "select   																				"+
			"		count(*)                           																	"+
			"	from sctrans.T_TO_CASE_INFO i                                                                           "+
			"	left join sctrans.T_TO_PROPERTY_INFO t on i.CASE_CODE=t.CASE_CODE                                       "+
			"	inner join sctrans.T_TO_CASE c on i.CASE_CODE=c.CASE_CODE                                               "+
			"	where                                                                                                   "+
			"		   c.CREATE_TIME >DATEADD(mm,-6,GETDATE())                                                       "+
			"		  and c.CREATE_TIME > cast(substring('2016-12-28 00:00:00.000',0,19) as datetime)                   "+
			"	    and c.CASE_ORIGIN != 'CTM'  and c.CASE_ORIGIN !='PROCESS'									        "+
			"		and t.PROPERTY_CODE=:propertyCode                                                              		";
	private static String sql1 = "select   																				"+
			"		count(*)                           																	"+
			"	from sctrans.T_TO_CASE_INFO i                                                                           "+
			"	left join sctrans.T_TO_PROPERTY_INFO t on i.CASE_CODE=t.CASE_CODE                                       "+
			"	inner join sctrans.T_TO_CASE c on i.CASE_CODE=c.CASE_CODE                                               "+
			"	where                                                                                                   "+
			"		  c.CREATE_BY='ctm_proc'                                                                            "+
			"		  and c.CREATE_TIME >DATEADD(mm,-6,GETDATE())                                                       "+
			"		  and c.CREATE_TIME > cast(substring('2016-12-28 00:00:00.000',0,19) as datetime)                   "+
			"	    and c.CASE_ORIGIN != 'INPUT'  and c.CASE_ORIGIN !='PROCESS'									        "+
			"		and t.PROPERTY_CODE=:propertyCode                                                              				";
	

    
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			Object propertyCode= key.get("PROPERTY_CODE");
			Object inputType= key.get("CASE_ORIGIN");
			
			Map paramMap = new HashMap();
			paramMap.put("propertyCode", propertyCode);
			paramMap.put("inputType", inputType);
			String realName1 = "";
			if(null != propertyCode && null != inputType && !StringUtils.isBlank((String)propertyCode) && !StringUtils.isBlank((String)inputType)){
				if(null != inputType && "CTM".equals(inputType)){
					realName1 = jdbcTemplate.queryForObject(sql0, paramMap, String.class);
				}else{
					realName1 = jdbcTemplate.queryForObject(sql1, paramMap, String.class);
				}
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
	public Boolean isCacheable(){
	    	return false;
	    }
	
}
