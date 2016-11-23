/**
 * 
 */
package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper;
import com.centaline.trans.common.repository.utils.QuickQueryBatchWarpper.BatchQuery;

/**
 * 
 * @author zhoujp
 * @date 2016年11月22日
 */
public class QuickQueryGetRealNameAndOrgNameServiceImpl implements
		CustomDictService {
	
	
	private static String USER_SQL = "SELECT ID,REAL_NAME FROM SCTRANS.SYS_USER WHERE ID in (:userId)";
	private static String ORG_SQL = "SELECT ID,ORG_NAME FROM SCTRANS.SYS_ORG WHERE ID in (:orgId)";
	
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if(keys==null || keys.isEmpty()){
			return null;
		}
		QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(new BatchQuery(){
			@Override
			public <T> List<T> query(List<T> resultSet) {
				List<Map<String, Object>> rs = null;
				List<String> userId = new ArrayList<String>();
				List<String> orgId = new ArrayList<String>();
				if(resultSet!=null && !resultSet.isEmpty()){
					rs = (List<Map<String, Object>>) resultSet;
					for (Map<String, Object> key : rs) {
						Object user_id   = key.get("PR_APPLIANT");
						Object org_id     = key.get("PR_DISTRICT_ID");
						if(user_id!=null){
							userId.add(user_id.toString());
						}
						if(org_id!=null){
							orgId.add(org_id.toString());
						}
					}
					
					Map<String,Object> paramMap = new HashMap<String,Object>();
					
					List<Map<String, Object>>  users = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>>  orgs = new ArrayList<Map<String, Object>>();
					
					paramMap.put("userId", userId);
					users = jdbcTemplate.queryForList(USER_SQL, paramMap);
					paramMap.clear();
					paramMap.put("orgId", orgId);
					orgs = jdbcTemplate.queryForList(ORG_SQL, paramMap);
					for (Map<String, Object> key : rs) {
						Object user_id   = key.get("PR_APPLIANT");
						Object org_id     = key.get("PR_DISTRICT_ID");
						for(Map<String, Object> user:users){
							if(user_id.equals(user.get("ID"))){
								key.put("REAL_NAME", user.get("REAL_NAME"));
								continue;
							}
						}
						for(Map<String, Object> org:orgs){
							if(org_id.equals(org.get("ID"))){
								key.put("ORG_NAME", org.get("ORG_NAME"));
								continue;
							}
						}
					}
				}
				return (List<T>) rs;
			}
		}, 1000);
		
		return batchWarpper.batchWarp(keys);
	}

	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	

}
