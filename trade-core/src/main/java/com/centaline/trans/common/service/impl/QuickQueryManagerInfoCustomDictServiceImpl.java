package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;

public class QuickQueryManagerInfoCustomDictServiceImpl implements CustomDictService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	UamUserOrgService uamUserOrgService;
	
	//private static String sql = "SELECT u.mobile, u.real_name  FROM SCTRANS.SYS_USER AS u WHERE ORG_ID= ? ";
	//private static String sql = "SELECT JOB_CODE from sctrans.V_USER_ORG_JOB vuoj where vuoj.ORG_ID = 'A9E9D96B7E6A43D38A53B97C3852CC24' and JOB_NAME = '分行经理';";
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String jobCode;
	@Override
	public Boolean getIsBatch(){
    	return true;
    }
	
	@Override
	public List<Map<String,Object>> findDicts(List<Map<String,Object>> keys){
		 List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(int i = 0 ;i< keys.size(); i++){
			 String key = keys.get(i).get("ORG_ID").toString();
			 Map<String,Object> map = new HashMap<String,Object>();
			 List<User> managerInfoList = uamUserOrgService.getUserByOrgIdAndJobCode(key,jobCode);
			 User  user = null;
				if(managerInfoList.size()>0){
					user=managerInfoList.get(0);
				}
			 map.put("ORG_ID", key);
			 map.put(CustomDictService.DICTVALCOL, JSONObject.toJSON(user));
			 result.add(map);
		}
    	return result;
    }
	
	
	
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	
	
}
