package com.centaline.trans.common.service.impl;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;



public class QuickQueryGetEloanProductServiceImpl implements CustomDictService{
	@Autowired
	private JdbcTemplate jdbcTemplate;
   
	private  String  code;  
	
    private static String sqlEloanProduct = "SELECT D.NAME 	FROM  SCTRANS.SYS_DICT D WHERE  D.TYPE='yu_serv_cat_code_tree' 	AND (D.TAG LIKE '%Eloan%' or D.TAG LIKE '%eplus%') 	AND D.CODE IN (SELECT EC.LOAN_SRV_CODE FROM SCTRANS.T_TO_ELOAN_CASE EC 	WHERE  EC.CASE_CODE = ?  AND EC.LOAN_SRV_CODE NOT IN ('30004005','30004015'))";
    
    private static String sqlEloanKa = "SELECT D.NAME 	FROM  SCTRANS.SYS_DICT D WHERE  D.TYPE='yu_serv_cat_code_tree' 	AND (D.TAG LIKE '%Eloan%' or D.TAG LIKE '%eplus%') 	AND D.CODE IN (SELECT EC.LOAN_SRV_CODE FROM SCTRANS.T_TO_ELOAN_CASE EC 	WHERE  EC.CASE_CODE = ?  AND EC.LOAN_SRV_CODE IN ('30004005','30004015'))";
    
    private static String sqlEloanKaAmountCount = "SELECT  EC.APPLY_AMOUNT  FROM SCTRANS.T_TO_ELOAN_CASE EC WHERE  EC.CASE_CODE = ? AND EC.LOAN_SRV_CODE IN ('30004005','30004015')";
    
    @Override
	@Cacheable(value="QuickQueryGetEloanProductServiceImpl",key="#root.target+'/'+#keys")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {			   
		List<String> dictNameList = null;
		for (Map<String, Object> key : keys) {			
			Object caseCode = key.get("CASE_CODE");
			if (caseCode != null) {
				if("eloanProduct".equals(code)){
					 dictNameList = jdbcTemplate.queryForList(sqlEloanProduct, String.class, caseCode.toString());					
				}else if("eloanKa".equals(code)){
					 dictNameList = jdbcTemplate.queryForList(sqlEloanKa, String.class, caseCode.toString());					
				}else if("eloanKaAoumt".equals(code)){
					 dictNameList = jdbcTemplate.queryForList(sqlEloanKaAmountCount, String.class, caseCode.toString());
				}
				key.put("val", StringUtils.join(dictNameList, ";"));			
				
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
