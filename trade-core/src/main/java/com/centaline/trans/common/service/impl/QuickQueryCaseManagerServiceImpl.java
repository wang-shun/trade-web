package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.common.enums.TransJobs;

public class QuickQueryCaseManagerServiceImpl implements CustomDictService {
	
	@Override
	public Boolean isCacheable() {
		return false;
	}
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;
	
	@Autowired
	private ToCaseMapper toCaseMapper;

	private static String sql = "select TEAM_NOW from sctrans.T_TS_TEAM_TRANSFER where IS_DELETE='0' and CASE_CODE=?";
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getValue(String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("QuickQueryCaseManagerServiceImpl Dict : " + key);
		}
		System.out.println("QuickQueryCaseManagerServiceImpl Dict : " + key);
		
		List<User> users = null;
 		ToCaseInfo caseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(key);
		if("1".equals(caseInfo.getIsResponsed())){
			System.out.println("QuickQueryCaseManagerServiceImpl Dict:"+key+" Case : 1" );
			ToCase toCase = toCaseMapper.findToCaseByCaseCode(key);
			Org org = uamUserOrgService.getOrgById(toCase.getOrgId());
			users = uamUserOrgService.getUserByOrgIdAndJobCode(toCase.getOrgId(), TransJobs.TJYZG.getCode());
			setUserOrgName(users,org.getOrgName());
			return getJoinUserInfo(users);
		}
		List<String> guestNameList = jdbcTemplate.queryForList(sql, String.class, key);
		if (guestNameList != null && !guestNameList.isEmpty()) {
			System.out.println("QuickQueryCaseManagerServiceImpl Dict:"+key+"Case : 2" );
			String teamNow = guestNameList.get(0);
			Org org = uamUserOrgService.getOrgByCode(teamNow);
			users = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(), TransJobs.TJYZG.getCode());
			setUserOrgName(users,org.getOrgName());
			return getJoinUserInfo(users);
		}
		sql = "select  YU_TEAM_CODE from sctrans.T_TS_TEAM_SCOPE_GRP where IS_RESPONSE_TEAM='1' and GRP_CODE=?;";
		List<String> yuTeamCode = jdbcTemplate.queryForList(sql, String.class, caseInfo.getGrpCode());
		if (yuTeamCode != null && !yuTeamCode.isEmpty()) {
			System.out.println("QuickQueryCaseManagerServiceImpl Dict:"+key+"Case : 3" );
			users=new ArrayList<>(1);
			for (String teamCode : yuTeamCode) {
				Org org = uamUserOrgService.getOrgByCode(teamCode);
				List<User> us=uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(), TransJobs.TJYZG.getCode());
				setUserOrgName(us,org.getOrgName());
				users.addAll(us);
			}
			return getJoinUserInfo(users);
		} 
		sql = "select  YU_TEAM_CODE from sctrans.T_TS_TEAM_SCOPE_AR where IS_RESPONSE_TEAM='1' and AR_CODE=?;";
		yuTeamCode = jdbcTemplate.queryForList(sql, String.class, caseInfo.getArCode());
		if (yuTeamCode != null && !yuTeamCode.isEmpty()) {
			System.out.println("QuickQueryCaseManagerServiceImpl Dict:"+key+"Case : 4" );
			users=new ArrayList<>(1);
			for (String teamCode : yuTeamCode) {
				Org org = uamUserOrgService.getOrgByCode(teamCode);
				List<User> us=uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(), TransJobs.TJYZG.getCode());
				setUserOrgName(us,org.getOrgName());
				users.addAll(us);
			}
			return getJoinUserInfo(users);
		} 
		return "";
	}
	private void setUserOrgName(List<User>users,String orgName){
		if(users!=null&&!users.isEmpty()){
			for (User user : users) {
				user.setOrgName(orgName);
			}
		}
	}

	private String getJoinUserInfo(List<User> users) {
		if(users!=null&&!users.isEmpty()){
			List<String>tempStrs=new ArrayList<>(1);
			for (User user : users) {
				String userStr=user.getRealName()+";"+user.getOrgName()+";"+user.getMobile();
				tempStrs.add(userStr);
			}
			return StringUtils.join(tempStrs, "/");
		}
		return "";
	}
}
