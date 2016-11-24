package com.centaline.trans.common.service.impl;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickQueryPropertyServiceImpl implements CustomDictService {

	@Override
	public Boolean isCacheable() {
		return false;
	}

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	private String dictType;


	@Override
	@Cacheable(value="QuickQueryCaseManagerServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		StringBuilder sql = new StringBuilder();
		if("mortgageAddr".equals(dictType)){
			sql.append("select PROPERTY_ADDR as v from sctrans.T_TO_PROPERTY_INFO  where CASE_CODE=:code");
		}else if("mortgageFinOrg".equals(dictType)){
			sql.append("Select FIN_ORG_NAME_YC as v from sctrans.T_TS_FIN_ORG  where FIN_ORG_CODE=:code");
		}else if("fenHang".equals(dictType)){
			sql.append("select TOP 1 FIN_ORG_NAME_YC as v from sctrans.T_TS_FIN_ORG where FIN_ORG_CODE in (select distinct FA_FIN_ORG_CODE  from sctrans.T_TS_FIN_ORG where FIN_ORG_CODE in(select FIN_ORG_CODE from sctrans.T_TS_SUP ts where ts.SUP_CAT='0' and ts.FIN_ORG_CODE=:code))");
		}
		else{
			sql.append("");
		}

		for(Map<String, Object> keyer:keys){
			String val = "";
			Object key = keyer.values().iterator().next();
			
			if(key!=null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("code", key);
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), param);
				if(list.size()!=0){
					val = String.valueOf(list.get(0).get("v"));
				}
			}
			
			keyer.put("val", val);
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
