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
 * 组成形如“公积金贷款：原因”
 * @author zhoujp
 * @date 2016年11月15日
 */
public class QuickQueryGetFontAgentInfoServiceImpl implements
		CustomDictService {
	private static String AGENT_SQL = "SELECT CASE_CODE,AGENT_NAME,AGENT_PHONE FROM SCTRANS.T_TO_CASE_INFO WHERE CASE_CODE in (:caseCode)";
	private static String FONT_SQL = "SELECT ID,REAL_NAME AS FONT_NAME,MOBILE AS FONT_MOBILE FROM SCTRANS.SYS_USER WHERE ID in (:userId) AND IS_DELETED='0'";
	
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if(keys==null || keys.isEmpty()){
			return null;
		}
		QuickQueryBatchWarpper batchWarpper = new QuickQueryBatchWarpper(new BatchQuery(){
			public <T> List<T> query(List<T> resultSet) {
				List<String> caseCode = new ArrayList<String>();
				List<String> userId = new ArrayList<String>();
				List<Map<String, Object>> rs = null;
				if(resultSet!=null && !resultSet.isEmpty()){
					rs = (List<Map<String, Object>>) resultSet;
					for (Map<String, Object> key : rs) {
						Object case_code   = key.get("CASE_CODE");
						Object id     = key.get("LEADING_PROCESS_ID");
						if(case_code!=null){
							caseCode.add(case_code.toString());
						}
						if(id!=null){
							userId.add(id.toString());
						}
					}
					Map<String,Object> paramMap = new HashMap<String,Object>();
					List<Map<String, Object>>  agents = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>>  fonts = new ArrayList<Map<String, Object>>();
					paramMap.put("caseCode", caseCode);
					agents = jdbcTemplate.queryForList(AGENT_SQL, paramMap);
					paramMap.clear();
					paramMap.put("userId", userId);
					fonts = jdbcTemplate.queryForList(FONT_SQL, paramMap);
					for (Map<String, Object> key : rs) {
						Object case_code   = key.get("CASE_CODE");
						Object id     = key.get("LEADING_PROCESS_ID");
						for(Map<String, Object> agent:agents){
							if(case_code.equals(agent.get("CASE_CODE"))){
								key.put("AGENT_NAME", agent.get("AGENT_NAME"));
								key.put("AGENT_PHONE", agent.get("AGENT_PHONE"));
								continue;
							}
						}
						for(Map<String, Object> font:fonts){
							if(id.equals(font.get("ID"))){
								key.put("FONT_NAME", font.get("FONT_NAME"));
								key.put("FONT_MOBILE", font.get("FONT_MOBILE"));
								continue;
							}
						}
						
					}
				}
				
				return (List<T>) rs;
			}
		},1000);
		
		return batchWarpper.batchWarp(keys);
	}

	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	

}
