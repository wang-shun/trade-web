package com.centaline.trans.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

public class QuickQueryGetRishControlOfficerInfoServiceImpl implements CustomDictService {

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		
		String sql = "SELECT S.REAL_NAME,S.MOBILE,O.ORG_NAME FROM sctrans.SYS_USER S LEFT JOIN sctrans.SYS_ORG O ON S.ORG_ID = O.ID WHERE S.ID = :id";
		
		for (Map<String, Object> key : keys) {	
			Object userId = key.get("RISK_CONTROL_OFFICER");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", userId);
			if(null!=userId){
				Map<String,Object> user = jdbcTemplate.queryForMap(sql, map);
				if(!org.springframework.util.CollectionUtils.isEmpty(user)){
					key.put("operatorName", user.get("REAL_NAME"));
					key.put("operatorPhone", user.get("MOBILE"));
					key.put("operatorOrg", user.get("ORG_NAME"));
					
				}
			}
		}
		return keys;
	}	
  
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
}
