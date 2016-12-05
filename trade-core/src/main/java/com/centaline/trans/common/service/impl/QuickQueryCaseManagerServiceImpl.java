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

public class QuickQueryCaseManagerServiceImpl implements CustomDictService {

	@Override
	public Boolean isCacheable() {
		return false;
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;

	@Override
	public String getValue(String key) {
		if (StringUtils.isBlank(key)) {
			return StringUtils.EMPTY;
		}
		String sql = "SELECT uoj.REAL_NAME ,uoj.ORG_NAME,uoj.MOBILE FROM sctrans.T_TO_CASE toCase "
				   + " inner join sctrans.V_USER_ORG_JOB uoj on uoj.ORG_ID = toCase.ORG_ID"
				   + " inner join SCTRANS.T_TO_CASE_INFO A on A.CASE_CODE = toCase.CASE_CODE"
				   + " WHERE A.CASE_CODE= ? and A.IS_RESPONSED = 1 AND  uoj.JOB_CODE = 'Manager'";
		List<Map<String, Object>> orgIdList = jdbcTemplate.queryForList(sql, key);
		if(CollectionUtils.isEmpty(orgIdList)) {
			String sql1 = "select su.REAL_NAME ,so.ORG_NAME,su.MOBILE " + "	from sctrans.T_TS_TEAM_SCOPE_TARGET tst "
					+ " left join sctrans.SYS_ORG so on tst.YU_TEAM_CODE = so.ORG_CODE "
					+ " left join sctrans.SYS_USER_ORG_JOB uoj on uoj.ORG_ID = so.id "
					+ " left join sctrans.SYS_JOB sj on uoj.JOB_ID = sj.ID "
					+ " left join sctrans.SYS_USER su on su.ID = uoj.USER_ID "
					+ " where tst.grp_code = (SELECT TARGET_CODE from sctrans.T_TO_CASE_INFO where CASE_CODE = ?) and sj.JOB_CODE = 'Manager'  and tst.IS_RESPONSE_TEAM = 1";
			List<Map<String, Object>> userList = jdbcTemplate.queryForList(sql1, key);
			String managerInfo = getJoinUserInfo(userList);
			return managerInfo;
		} else {
			String managerInfo = getJoinUserInfo(orgIdList);
			return managerInfo;
		}
	}
	
	@Override
	@Cacheable(value="QuickQueryCaseManagerServiceImpl",key="#root.targetClass + #root.methodName")
	public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys) {
		for(Map<String, Object> keyer:keys){
			String val = "";
			Object key = keyer.values().iterator().next();
			
			if(key!=null){
				
				String sql = "SELECT uoj.REAL_NAME ,uoj.ORG_NAME,uoj.MOBILE FROM sctrans.T_TO_CASE toCase "
						   + " inner join sctrans.V_USER_ORG_JOB uoj on uoj.ORG_ID = toCase.ORG_ID"
						   + " inner join SCTRANS.T_TO_CASE_INFO A on A.CASE_CODE = toCase.CASE_CODE"
						   + " WHERE A.CASE_CODE= ? and A.IS_RESPONSED = 1 AND  uoj.JOB_CODE = 'Manager'";
				List<Map<String, Object>> orgIdList = jdbcTemplate.queryForList(sql, key);
				if(CollectionUtils.isEmpty(orgIdList)) {
					String sql1 = "select su.REAL_NAME ,so.ORG_NAME,su.MOBILE " + "	from sctrans.T_TS_TEAM_SCOPE_TARGET tst "
							+ " left join sctrans.SYS_ORG so on tst.YU_TEAM_CODE = so.ORG_CODE "
							+ " left join sctrans.SYS_USER_ORG_JOB uoj on uoj.ORG_ID = so.id "
							+ " left join sctrans.SYS_JOB sj on uoj.JOB_ID = sj.ID "
							+ " left join sctrans.SYS_USER su on su.ID = uoj.USER_ID "
							+ " where tst.grp_code = (SELECT TARGET_CODE from sctrans.T_TO_CASE_INFO where CASE_CODE = ?) and sj.JOB_CODE = 'Manager'  and tst.IS_RESPONSE_TEAM = 1";
					List<Map<String, Object>> userList = jdbcTemplate.queryForList(sql1, key);
					val = getJoinUserInfo(userList);
				} else {
					val = getJoinUserInfo(orgIdList);
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
			String userStr = user.get("REAL_NAME") + ";" + user.get("ORG_NAME") + ";" + user.get("MOBILE");
			tempStrs.add(userStr);
		}
		return StringUtils.join(tempStrs, "/");
	}
	
	@Override
	public Boolean getIsBatch() {
		return true;
	}
}
