/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

/**
 * @author hejf10
 * @date 2016年11月18日
 */
@Service
public class QuickQueryGetDirectorServiceImpl implements CustomDictService {

	private static String sql = "select top 1 REAL_NAME from sctrans.V_USER_ORG_JOB_ACTIVE uoj where uoj.JOB_CODE = 'director' and uoj.ORG_ID= :orgId ; ";

	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for (Map<String, Object> key : keys) {
			Object orgId= key.get("parentId");
			Map paramMap = new HashMap();
			paramMap.put("orgId", orgId);
			String realName1 = "";
			if(null != orgId){
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
}
