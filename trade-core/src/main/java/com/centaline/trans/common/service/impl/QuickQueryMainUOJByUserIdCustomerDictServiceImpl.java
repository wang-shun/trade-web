package com.centaline.trans.common.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

public class QuickQueryMainUOJByUserIdCustomerDictServiceImpl implements CustomDictService{
	
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if(keys == null || keys.isEmpty()){
			return null;
		}
		Set<String> set = new HashSet<String>();
		for (Map<String, Object> map : keys) {
			if(null == map || !map.containsKey("USER_ID")){
				continue;
			}
			String userId =String.valueOf(map.get("USER_ID")) ;
			set.add(userId);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("paramMap", set);
		String sql = "select USER_ID ,ORG_NAME " + CustomDictService.DICTVALCOL +" from sctrans.v_user_job_org_main where USER_ID in (:paramMap)";
		List<Map<String, Object>> result =  jdbcTemplate.queryForList(sql, paramMap);
		return result;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}

	@Override
	public Boolean isCacheable() {
		return false;
	}
	
}
