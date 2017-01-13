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
	public String getValue(String key) {
		if (StringUtils.isBlank(key)) {
			return StringUtils.EMPTY;
		}
		String sql = "SELECT uoj.REAL_NAME ,uoj.ORG_NAME,uoj.MOBILE FROM sctrans.T_TO_CASE toCase "
				   + " inner join sctrans.V_USER_ORG_JOB uoj on uoj.ORG_ID = toCase.ORG_ID"
				   + " inner join SCTRANS.T_TO_CASE_INFO A on A.CASE_CODE = toCase.CASE_CODE"
				   + " WHERE A.CASE_CODE= ? and A.IS_RESPONSED = 1 AND  uoj.JOB_CODE = 'Manager'"
				   + "	and uoj.is_deleted = 0 ";
		List<Map<String, Object>> orgIdList = jdbcTemplate.queryForList(sql, key);
		if(CollectionUtils.isEmpty(orgIdList)) {
			String sql1 = "select su.REAL_NAME ,so.ORG_NAME,su.MOBILE " + "	from sctrans.T_TS_TEAM_SCOPE_TARGET tst "
					+ " left join sctrans.SYS_ORG so on tst.YU_TEAM_CODE = so.ORG_CODE "
					+ " left join sctrans.SYS_USER_ORG_JOB uoj on uoj.ORG_ID = so.id "
					+ " left join sctrans.SYS_JOB sj on uoj.JOB_ID = sj.ID "
					+ " left join sctrans.SYS_USER su on su.ID = uoj.USER_ID "
					+ " where tst.grp_code = (SELECT TARGET_CODE from sctrans.T_TO_CASE_INFO where CASE_CODE = ?) "
					+ "	and sj.JOB_CODE = 'Manager'  and tst.IS_RESPONSE_TEAM = 1 and uoj.is_deleted = 0";
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
				
				String sql0 = 
						"(SELECT u.ID USER_ID,u.ORG_ID  ORG_ID FROM sctrans.T_TO_CASE c inner join sctrans.sys_user u on c.LEADING_PROCESS_ID = u.id              WHERE CASE_CODE = ?)"
					   +" UNION " 
					   +"(SELECT u.ID USER_ID,u.ORG_ID  ORG_ID FROM sctrans.T_TG_SERV_ITEM_AND_PROCESSOR p inner join sctrans.sys_user u on p.PROCESSOR_ID = u.id WHERE CASE_CODE = ?)";
				List<Map<String, Object>> orgIdList0 = jdbcTemplate.queryForList(sql0, key,key);
				
				if(CollectionUtils.isEmpty(orgIdList0)) {
				
					String sql = "SELECT u.REAL_NAME ,u.ID USER_ID,o.ORG_NAME,o.ID ORG_ID,u.MOBILE FROM sctrans.T_TO_CASE toCase "
							   + " inner join sctrans.SYS_USER_ORG_JOB uoj on uoj.ORG_ID = toCase.ORG_ID"
							   + " inner join sctrans.sys_job j on uoj.job_id = j.id "
							   + " inner join sctrans.sys_user u on uoj.user_id = u.id "
							   + " inner join sctrans.sys_org o on o.id = uoj.org_id "
							   + " inner join SCTRANS.T_TO_CASE_INFO A on A.CASE_CODE = toCase.CASE_CODE"
							   + " WHERE A.CASE_CODE= ? and A.IS_RESPONSED = 1 AND  j.JOB_CODE = 'Manager'"
							   + "	 and uoj.IS_DELETED = 0";
					List<Map<String, Object>> orgIdList = jdbcTemplate.queryForList(sql, key);
					if(CollectionUtils.isEmpty(orgIdList)) {
						String sql1 = "select su.REAL_NAME  ,su.ID USER_ID,so.ORG_NAME ,so.ID ORG_ID,su.MOBILE from sctrans.T_TS_TEAM_SCOPE_TARGET tst "
								+ " left join sctrans.SYS_ORG so on tst.YU_TEAM_CODE = so.ORG_CODE "
								+ " left join sctrans.SYS_USER_ORG_JOB uoj on uoj.ORG_ID = so.id "
								+ " left join sctrans.SYS_JOB sj on uoj.JOB_ID = sj.ID "
								+ " left join sctrans.SYS_USER su on su.ID = uoj.USER_ID "
								+ " where tst.grp_code = (SELECT TARGET_CODE from sctrans.T_TO_CASE_INFO where CASE_CODE = ?) "
								+ "		and sj.JOB_CODE = 'Manager'  and tst.IS_RESPONSE_TEAM = 1 and uoj.IS_DELETED = 0 ";
						List<Map<String, Object>> userList = jdbcTemplate.queryForList(sql1, key);
						val = getJoinUserInfo(userList);
					} else {
						val = getJoinUserInfo(orgIdList);
					} 
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
