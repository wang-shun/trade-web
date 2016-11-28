package com.centaline.trans.common.service.impl;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickQueryMortgageFinOrgValueServiceImpl implements CustomDictService {

	@Override
	public Boolean isCacheable() {
		return false;
	}

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	private String dictType;


	@Override
	@Cacheable(value="QuickQueryMortgageFinOrgValueServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		StringBuilder sql = new StringBuilder();
		if("mortgageFinOrg".equals(dictType)){
			
			sql.append("Select FIN_ORG_NAME_YC as v,FA_FIN_ORG_CODE as v2 from sctrans.T_TS_FIN_ORG  where FIN_ORG_CODE=:code");
		}else if("caseProperty".equals(dictType)){
			sql.append("select PKID as v,(select ORG_NAME from sctrans.SYS_ORG where id=toCase.DISTRICT_ID) as v2 from (select case_code,PKID,DISTRICT_ID from sctrans.T_TO_CASE where CASE_CODE=:code) toCase");
		}
		else{
			sql.append("");
		}

		for(Map<String, Object> keyer:keys){
			String val = "";
			String val2 = "";
			Object key = keyer.values().iterator().next();
			
			if(key!=null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("code", key);
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), param);
				if(list.size()!=0){
					val = String.valueOf(list.get(0).get("v"));
					val2 = String.valueOf(list.get(0).get("v2"));
				}
			}
			if("mortgageFinOrg".equals(dictType)){
				keyer.put("FIN_ORG_NAME_YC", val);
				keyer.put("FA_FIN_ORG_CODE", val2);
			}
			if("caseProperty".equals(dictType)){
				keyer.put("PKID", val);
				keyer.put("ORG_ORG_NAME", val2);
			}
		}
		return keys;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

	public String getDictType() {return dictType;}

	public void setDictType(String dictType) {this.dictType = dictType;}
}
