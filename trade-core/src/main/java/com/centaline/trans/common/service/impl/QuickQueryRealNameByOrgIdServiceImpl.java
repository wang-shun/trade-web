package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

public class QuickQueryRealNameByOrgIdServiceImpl implements CustomDictService {
	
	private String prop;
	
	@Autowired
	private QuickQueryJdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if (CollectionUtils.isNotEmpty(keys)) {
			 List<Map<String,Object>> totalList = new ArrayList<Map<String,Object>>();
			 List<String> orgIds = new ArrayList<String>();
			 int i = 0;
			 String alias = null;
			 for(Map<String, Object> m:keys){
				 if(prop.equals("TeamAssistant") || prop.equals("Manager")) {
					 //得到所有的参数
					 if(m.get("OP_ORG_ID")==null) {
						 continue;
					 }
					 orgIds.add(m.get("OP_ORG_ID").toString());
					 alias = "OP_ORG_ID";
				 } else if(prop.equals("JFHJL")) {
					 if(m.get("AGENT_ORG_ID")==null) {
						 continue;
					 }
					 orgIds.add(m.get("AGENT_ORG_ID").toString());
					 alias = "AGENT_ORG_ID";
				 } else if(prop.equals("JQYJL")) {
					 if(m.get("AGENT_BUSIAR_ID")==null) {
						 continue;
					 }
					 orgIds.add(m.get("AGENT_BUSIAR_ID").toString());
					 alias = "AGENT_BUSIAR_ID";
				 } else if(prop.equals("JQYZJ")) {
					 if(m.get("AGENT_BUSISWZ_ID")==null) {
						 continue;
					 }
					 orgIds.add(m.get("AGENT_BUSISWZ_ID").toString());
					 alias = "AGENT_BUSISWZ_ID";
				 } else if(prop.equals("JQYDS")) {
					 if(m.get("AGENT_BUSIWZ_ID")==null) {
						 continue;
					 }
					 orgIds.add(m.get("AGENT_BUSIWZ_ID").toString());
					 alias = "AGENT_BUSIWZ_ID";
				 }
				 i++;
				 
				 if(i==1000) {
					 
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("orgIds", orgIds);
					 map.put("prop", prop);
					 
					 StringBuffer buffer = new StringBuffer();
					 buffer.append("SELECT ORG_ID AS "+alias);
					 buffer.append(",REAL_NAME AS val FROM SCTRANS.V_USER_ORG_JOB WHERE is_leader = 1 AND JOB_CODE= :prop AND ORG_ID IN (:orgIds)");
					 
					 List<Map<String,Object>> result1 = jdbcTemplate.queryForList(buffer.toString(), map);
					 
					 totalList.addAll(result1);
					 
					 orgIds.clear();
					 
					 i = 0 ;
				 }
			 }
			 
			 Map<String,Object> map = new HashMap<String,Object>();
			 if(CollectionUtils.isNotEmpty(orgIds)) {
				 map.put("orgIds", orgIds);
				 map.put("prop", prop);
				 
				 StringBuffer buffer = new StringBuffer();
				 buffer.append("SELECT ORG_ID AS "+alias);
				 buffer.append(",REAL_NAME AS val FROM SCTRANS.V_USER_ORG_JOB WHERE is_leader = 1 AND JOB_CODE= :prop AND ORG_ID IN (:orgIds)");
					 
				 List<Map<String,Object>> result1 = jdbcTemplate.queryForList(buffer.toString(), map);
				 totalList.addAll(result1);
			 }
			 
			/* if(CollectionUtils.isNotEmpty(result)){
				for(Map<String,Object> m: result){
					m.put(CustomDictService.DICTVALCOL, m.get("EVAL_FEE"));
				}
			 }*/
				 				 
			 return totalList;
		}
		return null;
	}

	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
	@Override
	public Boolean isCacheable(){
    	return false;
    }
	
	public void setProp(String prop) {
		this.prop = prop;
	}
	
	public String getProp() {
		return prop;
	}
}
