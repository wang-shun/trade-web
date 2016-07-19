package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;

public class QuickQueryCaseManagerServiceImpl implements CustomDictService {

	@Override
	public Boolean isCacheable() {
		return false;
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String getValue(String key) {
		if (StringUtils.isBlank(key)) {
			return StringUtils.EMPTY;
		}
		String sql = "select su.REAL_NAME ,so.ORG_NAME,su.MOBILE " + "	from sctrans.T_TS_TEAM_SCOPE_TARGET tst "
				+ " left join sctrans.SYS_ORG so on tst.YU_TEAM_CODE = so.ORG_CODE "
				+ " left join sctrans.SYS_USER_ORG_JOB uoj on uoj.ORG_ID = so.id "
				+ " left join sctrans.SYS_JOB sj on uoj.JOB_ID = sj.ID "
				+ " left join sctrans.SYS_USER su on su.ID = uoj.USER_ID "
				+ " where grp_code = ? and sj.JOB_CODE = 'Manager' ";
		List<Map<String, Object>> userList = jdbcTemplate.queryForList(sql, key);
		String managerInfo = getJoinUserInfo(userList);
		return managerInfo;
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
}
