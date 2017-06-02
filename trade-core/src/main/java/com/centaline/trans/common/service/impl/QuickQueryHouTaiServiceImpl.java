package com.centaline.trans.common.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class QuickQueryHouTaiServiceImpl implements CustomDictService{
	
	@Value("${agent.img.url}")
	private String imgUrl;
	
	@Autowired
	protected QuickQueryJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		
		if(keys == null){
			return null;
		}
		List<Object> caseCodeList = new ArrayList<Object>();
		for (Map<String, Object> map : keys) {
			Object caseCode = map.get("caseCode");
			caseCodeList.add(caseCode);
		}
		
		Map<String,List<Object>> paramMap = new HashMap<String,List<Object>>();
		paramMap.put("caseCodeList", caseCodeList);
		String sql = "select distinct su.REAL_NAME name,su.EMPLOYEE_CODE employeeCode,su.mobile mobile,so.ORG_NAME orgName,tc.case_code caseCode  from "
				+ " sctrans.T_TG_SERV_ITEM_AND_PROCESSOR tp"
				+ " 	left join sctrans.T_TO_CASE tc on tp.CASE_CODE = tc.CASE_CODE and tp.PROCESSOR_ID != tc.LEADING_PROCESS_ID"
				+ " 	left join sctrans.SYS_USER su on su.ID = tp.PROCESSOR_ID"
				+ " 	left join sctrans.SYS_ORG so on so.ID = tp.ORG_ID"
				+ " where tc.CASE_CODE in (:caseCodeList) and tp.srv_code in ('3000401002','3000400201')";
		List<Map<String, Object>>  List = jdbcTemplate.queryForList(sql, paramMap);
		
		Map<String,JSONArray> valMap = new HashMap<String,JSONArray>();
		for (Map<String, Object> map : List) {
			String caseCode = !map.containsKey("caseCode") ? "" : String.valueOf(map.get("caseCode"));
			String employeeCode = !map.containsKey("employeeCode") ? "" : String.valueOf(map.get("employeeCode"));
			String orgName = !map.containsKey("orgName") ? "" : String.valueOf(map.get("orgName"));
			String name = !map.containsKey("name") ? "" : String.valueOf(map.get("name"));
			String avatar = null == employeeCode ? ""
					: MessageFormat.format(imgUrl, String.valueOf(employeeCode)) + ".jpg";
			String mobile = !map.containsKey("mobile") ? "" : String.valueOf(map.get("mobile"));
			
			JSONArray ja = null ;
			if(valMap.containsKey(caseCode)){
				ja = valMap.get(caseCode);
			}else{
				ja = new JSONArray();
			}
			JSONObject jo = new JSONObject();
			jo.put("name", name);
			jo.put("org", orgName);
			jo.put("avatar", avatar);
			jo.put("mobile", mobile);
			ja.add(jo);
			valMap.put(caseCode, ja);
		}
		
		List<Map<String, Object>> houtaiList = new ArrayList<Map<String, Object>>();
		for (Map.Entry<String,JSONArray> entry : valMap.entrySet()) {
			Map<String,Object> map = new HashMap<String,Object>();
			String caseCode = entry.getKey();
			map.put("caseCode",caseCode);
			map.put(DICTVALCOL,entry.getValue().toJSONString());
			houtaiList.add(map);
		}
		
		return houtaiList;
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
	
	public Boolean isCacheable(){
		return false;
	}
}
