package com.centaline.trans.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

public class QuickQueryGetOperatorInfoServiceImpl implements CustomDictService {

	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		
		String userSql = "SELECT REAL_NAME,MOBILE FROM sctrans.SYS_USER WHERE ID = :id";
		String orgSql = "SELECT ORG_NAME FROM sctrans.SYS_ORG WHERE ID = :id";
		
		for (Map<String, Object> key : keys) {	
			Object userId = key.get("EXCUTOR_ID");
			Object teamId = key.get("EXCUTOR_TEAM");
			Map map = new HashMap<String,String>();
			map.put("id", userId);
			if(null!=userId){
				Map<String,Object> user = jdbcTemplate.queryForMap(userSql, map);
				if(!org.springframework.util.CollectionUtils.isEmpty(user)){
					key.put("operatorName", user.get("REAL_NAME"));
					key.put("operatorPhone", user.get("MOBILE"));
					
				}
			}
			map.put("id", teamId);
			if(null!=teamId){
				Map<String,Object> team = jdbcTemplate.queryForMap(orgSql, map);
				if(!org.springframework.util.CollectionUtils.isEmpty(team)){
					key.put("operatorOrg", team.get("ORG_NAME"));
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
