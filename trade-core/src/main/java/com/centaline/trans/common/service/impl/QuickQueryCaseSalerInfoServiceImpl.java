package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class QuickQueryCaseSalerInfoServiceImpl implements CustomDictService{
	
	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;
	
	private String transPosition;
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		if(keys == null){
			return null;
		}
		
		List<String> caseCodelist = new ArrayList<String>();
		for (Map<String, Object> map : keys) {
			Object obj = map.get("caseCode");
			if(obj == null){
				continue;
			}
			caseCodelist.add(String.valueOf(obj));
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("caseCodelist", caseCodelist);
		paramMap.put("transPosition", transPosition);
		
		// 查询上家信息
		String sql = "SELECT CASE_CODE as caseCode,GUEST_NAME as name,GUEST_PHONE as mobile FROM sctrans.T_TG_GUEST_INFO WHERE CASE_CODE in (:caseCodelist) AND TRANS_POSITION = :transPosition order by CASE_CODE desc";
		// 根据案件编号集合查询上家姓名、上家手机号
		List<Map<String, Object>> sellerMapList = jdbcTemplate.queryForList(sql, paramMap);
		if(sellerMapList == null){
			return null;
		}
		
		Map<String,JSONArray> valMap = new HashMap<String,JSONArray>();
		for (Map<String, Object> map : sellerMapList) {
			Object nameObj = map.get("name");
			Object mobileObj = map.get("mobile");
			
			JSONArray ja = null ;
			
			String caseCode = String.valueOf(map.get("caseCode"));
			
			if(valMap.containsKey(caseCode)){
				ja = valMap.get(caseCode);
			}else{
				ja = new JSONArray();
			}
			JSONObject jo = new JSONObject();
			jo.put("name", nameObj == null ? "" : String.valueOf(nameObj));
			jo.put("mobile", mobileObj== null ? "" : String.valueOf(mobileObj));
			jo.put("avatar", "");
			ja.add(jo);
			valMap.put(caseCode, ja);
		}
		
		List<Map<String, Object>> sallerList = new ArrayList<Map<String, Object>>();
		for (Map.Entry<String,JSONArray> entry : valMap.entrySet()) {
			Map<String,Object> map = new HashMap<String,Object>();
			String caseCode = entry.getKey();
			map.put("caseCode",caseCode);
			map.put(DICTVALCOL,entry.getValue().toJSONString());
			sallerList.add(map);
		}
		
		return sallerList;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
	public Boolean isCacheable(){
		return false;
	}

	public String getTransPosition() {
		return transPosition;
	}

	public void setTransPosition(String transPosition) {
		this.transPosition = transPosition;
	}
}
