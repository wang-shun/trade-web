package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;

public class QuickQueryCaseManagerToQfServiceImpl implements CustomDictService {

	@Override
	public Boolean isCacheable() {
		return false;
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;

	
	@Override
	@Cacheable(value="QuickQueryCaseManagerServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> keyer:keys){
			String val = "";
			Object key = keyer.values().iterator().next();
			
			if(key!=null){
				
				String sql0 =  "(SELECT u.ID USER_ID,c.ORG_ID  ORG_ID FROM sctrans.T_TO_CASE c inner join sctrans.sys_user u on c.LEADING_PROCESS_ID = u.id              WHERE CASE_CODE = ?)";
				List<Map<String, Object>> orgIdList0 = jdbcTemplate.queryForList(sql0, key);
				
				if(CollectionUtils.isEmpty(orgIdList0)) {
				
					String sql = "select top 1 c.REQUIRE_PROCESSOR_ID USER_ID ,so.id ORG_ID from sctrans.T_TS_TEAM_PROPERTY t inner join  sctrans.T_TO_CASE_INFO c on c.TARGET_CODE=t.YU_TEAM_CODE " 
							+"left join sctrans.SYS_ORG so on c.TARGET_CODE = so.ORG_CODE "
							+"where t.IS_RESPONSE_TEAM=1  and c.CASE_CODE= ?";
					List<Map<String, Object>> orgIdList = jdbcTemplate.queryForList(sql, key);
						val = getJoinUserInfo(orgIdList);
				}else {
					val = getJoinUserInfo(orgIdList0);
				}
			}
			
			keyer.put("val", val);
		}
		
		return keys;
	}

	private String getJoinUserInfo(List<Map<String, Object>> users) {
		if(CollectionUtils.isEmpty(users)){
			return "";
		}
		List<String> tempStrs = new ArrayList<>();
		for (Map<String, Object> user : users) {
			String userStr = user.get("USER_ID") + ";" + user.get("ORG_ID") + ";" ;
			tempStrs.add(userStr);
		}
		return StringUtils.join(tempStrs, "");
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
}
