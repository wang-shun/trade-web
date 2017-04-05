package com.centaline.trans.perform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.common.enums.OrgNameEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.perform.entity.GustFollowEntity;
import com.centaline.trans.perform.repository.GustFollowMapper;
import com.centaline.trans.perform.service.GustFollowService;
import com.centaline.trans.perform.vo.GustFollowVo;

@Transactional(readOnly = true)
@Service
public class GustFollowServiceImpl implements GustFollowService {
	@Autowired
	private GustFollowMapper mapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Transactional(readOnly = false,propagation=Propagation.REQUIRES_NEW)
	@Override
	public boolean importOne(GustFollowVo vo) {
		List<GustFollowEntity> listEntity = getFullEntity(vo);
		if(listEntity==null||listEntity.isEmpty())return false;
		for (GustFollowEntity gustFollowEntity : listEntity) {
			mapper.deleteByBizKey(gustFollowEntity);
			mapper.insertSelective(gustFollowEntity);
		}
		return true;
	}

	/**
	 * 将VO转换成相应的Entity
	 * 
	 * @param vo
	 * @return
	 */
	private List<GustFollowEntity> getFullEntity(GustFollowVo vo) {
		List<GustFollowEntity> result = new ArrayList<>();
		User user = uamUserOrgService.getUserByEmployeeCode(vo.getEmployeeCode());
		if (user == null) {
			vo.setErrorMsg("人员不存在");
			return null;
		}
		if(!vo.getUserName().equals(user.getRealName())){
			vo.setErrorMsg("人员编号不匹配");
			return null;
		}
		UserOrgJob uoj = getUserOrgJob(user.getId(), vo.getJobName());
		if (uoj == null) {
			vo.setErrorMsg("人员没有角色:" + vo.getJobName());
			return null;
		}
		GustFollowEntity entity = VTE(vo, uoj, vo.getPhoneAccuracy());
		result.add(entity);
		// 交易主管
		User manager = getUser(entity.getOrgId(), TransJobs.TJYZG.getCode(), vo.getDirector());
		if (manager == null) {
			vo.setErrorMsg("所属主管不存在");
			return null;
		}
		uoj = getUserOrgJob(manager.getId(), TransJobs.TJYZG.getName());
		GustFollowEntity managerEntity = VTE(vo, uoj, vo.getDirectorPhoneAccuracy());
		result.add(managerEntity);

		// 总监
		Org org = uamUserOrgService.getOrgById(entity.getOrgId());
		User chiefInspector = getUser(org.getParentId(), TransJobs.TZJ.getCode(), vo.getChiefInspector());
		if (chiefInspector == null) {
			vo.setErrorMsg("所属总监不存在");
			return null;
		}
		uoj = getUserOrgJob(chiefInspector.getId(), TransJobs.TZJ.getName());
		GustFollowEntity chiefInspectorEntity = VTE(vo, uoj, vo.getDirectorPhoneAccuracy());
		result.add(chiefInspectorEntity);

		// 总经理
		org = uamUserOrgService.getOrgByCode(OrgNameEnum.T_FATHER_ORG.getCode());
		User generalManager = getUser(org.getId(), TransJobs.TZJL.getCode(), vo.getGeneralManager());
		if (generalManager == null) {
			vo.setErrorMsg("所属总经理不存在");
			return null;
		}
		uoj = getUserOrgJob(generalManager.getId(), TransJobs.TZJL.getName());
		GustFollowEntity generalManagerEntity = VTE(vo, uoj, vo.getDirectorPhoneAccuracy());
		result.add(generalManagerEntity);

		return result;
	}

	/**
	 * VO to Entity
	 * 
	 * @param vo
	 * @param uoj
	 * @param pa
	 * @return
	 */
	private GustFollowEntity VTE(GustFollowVo vo, UserOrgJob uoj, Double pa) {
		GustFollowEntity entity = new GustFollowEntity();
		entity.setCaseCode(vo.getCaseCode());
		entity.setJobId(uoj.getJobId());
		entity.setOrgId(uoj.getOrgId());
		entity.setParticipantId(uoj.getUserId());
		entity.setSatisfyDegree(vo.getSatisfyDegree());
		entity.setPhoneAccuracy(pa);
		entity.setCreateTime(new Date());
		return entity;
	}

	/**
	 * 根据条件查询用户
	 * 
	 * @param orgId
	 * @param jobCode
	 * @param userNmae
	 * @return
	 */
	private User getUser(String orgId, String jobCode, String userNmae) {
		List<User> managerUsers = uamUserOrgService.getUserByOrgIdAndJobId(orgId, jobCode);
		User manager = null;
		for (User user2 : managerUsers) {
			if (userNmae.equals(user2.getRealName())) {
				manager = user2;
				break;
			}
		}
		return manager;
	}

	/**
	 * 根据Userid 和jobName获得UserOrgJob
	 * 
	 * @param userId
	 * @param jobName
	 * @return
	 */
	private UserOrgJob getUserOrgJob(String userId, String jobName) {
		List<UserOrgJob> userOrgJobs = uamUserOrgService.getUserOrgJobByUserId(userId);
		UserOrgJob uoj = null;
		for (UserOrgJob userOrgJob : userOrgJobs) {
			if (jobName.equals(userOrgJob.getJobName())) {
				uoj = userOrgJob;
				break;
			}
		}
		return uoj;
	}
}
