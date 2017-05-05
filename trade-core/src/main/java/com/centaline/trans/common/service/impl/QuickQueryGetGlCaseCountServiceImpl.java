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
import com.centaline.trans.utils.CollectionUtils;
import com.centaline.trans.utils.CollectionUtils.Converter;

/**
 * 
 * @author hejf10
 * @date
 */
public class QuickQueryGetGlCaseCountServiceImpl implements CustomDictService{
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
   
	private  String  code;  
	
	/*private static String sql0 = "select   																				"+
			"		count(*)                           																	"+
			"	from sctrans.T_TO_CASE_INFO i                                                                           "+
			"	left join sctrans.T_TO_PROPERTY_INFO t on i.CASE_CODE=t.CASE_CODE                                       "+
			"	inner join sctrans.T_TO_CASE c on i.CASE_CODE=c.CASE_CODE                                               "+
			"	where                                                                                                   "+
			"		   c.CREATE_TIME >DATEADD(mm,-6,GETDATE())                                                       "+
			"		  and c.CREATE_TIME > cast(substring('2016-12-28 00:00:00.000',0,19) as datetime)                   "+
			"	    and c.CASE_ORIGIN != 'CTM'  and c.CASE_ORIGIN !='PROCESS'									        "+
			"		and t.PROPERTY_CODE=:propertyCode                                                              		";*/
	
	private static String sql0 = "SELECT  COUNT(1)"
			+ " FROM    sctrans.T_TO_PROPERTY_INFO t"
			+ " WHERE   EXISTS ( SELECT 1 FROM   sctrans.T_TO_PROPERTY_INFO tpi"
			+ " WHERE  tpi.PROPERTY_CODE = t.PROPERTY_CODE AND tpi.CASE_CODE =:caseCode )"
			+ " AND EXISTS ( SELECT 1 FROM   sctrans.T_TO_CASE c WHERE  t.CASE_CODE = c.CASE_CODE"
			+ " AND c.CREATE_TIME > DATEADD(mm, -6, GETDATE())"
			+ " AND c.CREATE_TIME > CAST(SUBSTRING('2016-12-28 00:00:00.000', 0, 19) AS DATETIME)"
			+ " AND c.CASE_ORIGIN != 'CTM' AND c.CASE_ORIGIN != 'PROCESS' )";

	/*private static String sql1 = "select   																				"+
			"		count(*)                           																	"+
			"	from sctrans.T_TO_CASE_INFO i                                                                           "+
			"	left join sctrans.T_TO_PROPERTY_INFO t on i.CASE_CODE=t.CASE_CODE                                       "+
			"	inner join sctrans.T_TO_CASE c on i.CASE_CODE=c.CASE_CODE                                               "+
			"	where                                                                                                   "+
			"		  c.CREATE_BY='ctm_proc'                                                                            "+
			"		  and c.CREATE_TIME >DATEADD(mm,-6,GETDATE())                                                       "+
			"		  and c.CREATE_TIME > cast(substring('2016-12-28 00:00:00.000',0,19) as datetime)                   "+
			"	    and c.CASE_ORIGIN != 'INPUT'  and c.CASE_ORIGIN !='PROCESS'									        "+
			"		and t.PROPERTY_CODE=:propertyCode                                                              				";*/
	private static String sql1 = "SELECT  COUNT(1)"
			+ " FROM    sctrans.T_TO_PROPERTY_INFO t"
			+ " WHERE   EXISTS ( SELECT 1 FROM   sctrans.T_TO_PROPERTY_INFO tpi"
			+ " WHERE  tpi.PROPERTY_CODE = t.PROPERTY_CODE AND tpi.CASE_CODE =:caseCode )"
			+ " AND EXISTS ( SELECT 1 FROM   sctrans.T_TO_CASE c WHERE  t.CASE_CODE = c.CASE_CODE"
			+ " AND c.CREATE_BY = 'ctm_proc' AND c.CREATE_TIME > DATEADD(mm, -6, GETDATE())"
			+ " AND c.CREATE_TIME > CAST(SUBSTRING('2016-12-28 00:00:00.000', 0, 19) AS DATETIME)"
			+ " AND c.CASE_ORIGIN != 'INPUT' AND c.CASE_ORIGIN != 'PROCESS' )";
	private static String sql2 = "SELECT PROPERTY_ADDR,PROPERTY_CODE,CASE_CODE FROM sctrans.T_TO_PROPERTY_INFO WHERE CASE_CODE in (:caseCode) ";
	

    
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		
		StringBuilder[] joins = CollectionUtils.join(keys, new String[]{"','"}, new String[]{"CASE_CODE"});
		// 设置条件参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql = sql2.replace(":caseCode","'"+joins[0].toString()+"'");
		List<Map<String, Object>> propertyInfoMapList = jdbcTemplate.queryForList(sql, paramMap);
		CollectionUtils.merge(propertyInfoMapList, keys, new String[]{"CASE_CODE"});
		CollectionUtils.convert(keys, converters);
		return keys;
	}
	private Map<String,Converter<String,Object>> converters = new HashMap<String,Converter<String,Object>>();
	public QuickQueryGetGlCaseCountServiceImpl() {
		//转换操作人员
		converters.put("glCount", new Converter<String,Object>(){//ASSESSOR 具体展示内容
			//new Converter<String,Object>(){} 查询结果集，具体转化操作
			public Object convert(Map<String,Object> to) {
				Object inputType = to.get("CASE_ORIGIN");
				Object caseCode  = to.get("CASE_CODE");
				String glCount = "";
				if(null!=caseCode && !StringUtils.isBlank((String)inputType)){
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("caseCode", caseCode);
					if("CTM".equals(inputType)){
						glCount = jdbcTemplate.queryForObject(sql0, paramMap, String.class);
					}else{
						glCount = jdbcTemplate.queryForObject(sql1, paramMap, String.class);
					}
				}
				return glCount;
			};
		});
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
