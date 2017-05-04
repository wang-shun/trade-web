package com.centaline.trans.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.SessionUserExtPropsService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
@Service(value="sessionUserExtPropsService")
public class SessionUserExtPropsServiceImpl implements SessionUserExtPropsService {
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Override
	public Map<String, Object> getExtProps(SessionUser user) {
		String teamId = null;// 组ID
		String districtId = null;// 贵宾服务部ID
		String hqId = null;// 总部ID
		if (DepTypeEnum.TYCTEAM.getCode().equals(user.getServiceDepHierarchy())) {
			teamId = user.getServiceDepId();
			Org districtOrg = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(),
					DepTypeEnum.TYCQY.getCode());
			districtId = districtOrg.getId();
			hqId = districtOrg.getParentId();
		}
		if (DepTypeEnum.TYCQY.getCode().equals(user.getServiceDepHierarchy())) {
			Org districtOrg = uamUserOrgService.getOrgById(user.getServiceDepId());
			districtId = user.getServiceDepId();
			hqId = districtOrg.getParentId();
		}
		if (DepTypeEnum.TYCZB.getCode().equals(user.getServiceDepHierarchy())) {
			hqId = user.getServiceDepId();
		}
		return buildExtPropsMap(hqId, districtId, teamId);
	}

	/**
	 * 封装返回的数据
	 * 
	 * @param hqId
	 * @param districtId
	 * @param teamId
	 * @return
	 */
	private Map<String, Object> buildExtPropsMap(String hqId, String districtId, String teamId) {
		Map<String, Object> extProps = new HashMap<>();
		// 设置
		if (hqId != null) {
			extProps.put("hqId", hqId);
			User u = getLeaderUserIdByOrgIdAndJobCode(hqId, TransJobs.TZJL.getCode());
			if (u != null){
				extProps.put("generalManagerId", u.getId());
			}
		}
		if (districtId != null) {
			extProps.put("districtId", districtId);
			User u = getLeaderUserIdByOrgIdAndJobCode(districtId, TransJobs.TZJ.getCode());
			if (u != null){
				extProps.put("directorId", u.getId());
			}
		}
		if (teamId != null) {
			extProps.put("teamId", teamId);
			User u = getLeaderUserIdByOrgIdAndJobCode(teamId, TransJobs.TJYZG.getCode());
			if (u != null){	
				extProps.put("managerId", u.getId());
			}
			User u1 = getLeaderUserIdByOrgIdAndJobCode(teamId, TransJobs.TSJYZG.getCode());
			if (u1 != null){
				extProps.put("seniorManagerId", u1.getId());
			}
		}
		return extProps;
	}

	/**
	 * 根据OrgId和JobCode获得领导Id(uamUserOrgService中的方法会返回无效的和已经删除的领导)
	 * 
	 * @param orgId
	 * @param jobCode
	 * @return
	 */
	private User getLeaderUserIdByOrgIdAndJobCode(String orgId, String jobCode) {
		List<User> users = uamUserOrgService.getUserByOrgIdAndJobCode(orgId, jobCode);
		if (users == null || users.isEmpty())
			return null;
		if (users.size() == 1) {
			return users.get(0);
		}
		for (User user : users) {
			List<UserOrgJob> uojs = uamUserOrgService.getUserOrgJobByUserIdAndJobCode(user.getId(), jobCode);
			for (UserOrgJob userOrgJob : uojs) {
				if (userOrgJob.getIsLeader()!=null&&userOrgJob.getIsLeader().intValue() == 1) {
					return user;
				}
			}
		}
		return null;
	}
}
