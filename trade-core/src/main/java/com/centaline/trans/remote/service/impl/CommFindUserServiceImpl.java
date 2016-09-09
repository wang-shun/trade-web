package com.centaline.trans.remote.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.remote.service.CommFindUserService;
import com.centaline.trans.task.service.impl.TsTaskDelegateServiceImpl;

@Service(value = "commFindUserService")
public class CommFindUserServiceImpl implements CommFindUserService {
	/**
	 * yucui总部orgCode
	 */
	public static final String YC_ORG_CODE = TsTaskDelegateServiceImpl.YC_ORG_CODE;
	@Autowired
	private ToCaseService toCaseService;

	@Autowired
	private UamBasedataService dictService;
	@Autowired
	private TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Value("${trade.yucui.headquarter}")
	private String yucuiHeadQuarte;
	@Value("${trade.yucui.director}")
	private String yucuiDistrict;
	@Value("${trade.yucui.team}")
	private String yucuiTeam;

	private Set<String> yucuiHeadQuarteSet = null;
	private Set<String> yucuiDistrictSet = null;

	/**
	 * 
	 * @return yucuiHeadQuarteSet
	 */
	public Set<String> getYucuiHeadQuartes() {
		if (yucuiHeadQuarteSet == null) {
			yucuiHeadQuarteSet = new HashSet<String>();
			if (!StringUtils.isBlank(yucuiHeadQuarte)) {
				yucuiHeadQuarteSet.addAll(Arrays.asList(yucuiHeadQuarte.split(",")));
			}
		}
		return yucuiHeadQuarteSet;
	}

	/**
	 * 
	 * @return yucuiDistrictSet
	 */
	public Set<String> getYucuiDistricts() {
		if (yucuiDistrictSet == null) {
			yucuiDistrictSet = new HashSet<String>();
			if (!StringUtils.isBlank(yucuiDistrict)) {
				yucuiDistrictSet.addAll(Arrays.asList(yucuiDistrict.split(",")));
			}
		}
		return yucuiDistrictSet;
	}

	public String findUserByCase(String jobCode, String caseCode) {
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		return findWorkFlowUser(jobCode, toCase.getLeadingProcessId(), toCase.getOrgId());
	}

	@Override
	public String findUserBySrv(String jobCode, String caseCode, String taskDfKey) {
		List<TgServItemAndProcessor> serviceMap = tgServItemAndProcessorService
				.findTgServItemAndProcessorByCaseCode(caseCode);
		TgServItemAndProcessor tsg = getServiceCode(serviceMap, taskDfKey);

		if (tsg != null && !StringUtils.isBlank(tsg.getProcessorId())) {
			return findWorkFlowUser(jobCode, tsg.getProcessorId(), tsg.getOrgId());
		} else {
			return null;
			// return findUserByCase(jobCode, caseCode);
		}
	}

	/**
	 * 流程通用找人逻辑
	 * 
	 * @param groupId
	 *            对应Sales系统中的jobCode
	 * @param baseonUserId
	 * @return
	 */
	public String findWorkFlowUser(String groupId, String baseonUserId, String orgId) {
		if (baseonUserId == null)
			return null;
		User baseonuser = uamUserOrgService.getUserById(baseonUserId);
		if (baseonuser == null)
			return null;
		if (containsJob(baseonuser.getUsername(), groupId))
			return baseonuser.getUsername();
		if (getYucuiHeadQuartes().contains(groupId))
			return findHQUser(groupId, orgId);
		else if (getYucuiDistricts().contains(groupId))
			return findDistrictUser(groupId, orgId);
		else
			return findTeam(groupId, orgId);

	}

	/**
	 * 判断用户是否有相关岗位
	 * 
	 * @param username
	 * @param jobCode
	 * @return
	 */
	private boolean containsJob(String username, String jobCode) {
		List<UserOrgJob> jobs = uamUserOrgService.findUserOrgJobByUsername(username);
		if (jobs == null || jobs.isEmpty() || StringUtils.isBlank(jobCode))
			return false;
		for (UserOrgJob userOrgJob : jobs) {
			if (jobCode.equals(userOrgJob.getJobCode())) {
				return true;
			}
		}
		return false;
	}

	private String findHQUser(String groupId, String orgId) {
		Org org = uamUserOrgService.getOrgByCode(YC_ORG_CODE);
		if (org == null)
			return null;
		return findTeam(groupId, org.getId());
	}

	/**
	 * 查找大区相关人员()
	 * 
	 * @param groupId
	 * @param orgId
	 * @return
	 */
	private String findDistrictUser(String groupId, String orgId) {
		if (StringUtils.isBlank(orgId))
			return null;
		Org org = uamUserOrgService.getOrgById(orgId);
		if (org == null || YC_ORG_CODE.equals(org.getOrgCode()))
			return null;
		String usernme = findTeam(groupId, org.getParentId());
		if (StringUtils.isBlank(usernme))
			return findDistrictUser(groupId, org.getParentId());
		return usernme;
	}

	/**
	 * 查找本组织相关人员
	 * 
	 * @param groupId
	 * @param orgId
	 * @return
	 */
	private String findTeam(String groupId, String orgId) {
		List<User> users = uamUserOrgService.getUserByOrgIdAndJobCode(orgId, groupId);
		if (users != null && !users.isEmpty() && users.size() < 2)
			return users.get(0).getUsername();
		else if (users.size() > 1) {
			String userNames = "";
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				if (i == 0) {
					userNames += user.getUsername();
				} else {
					userNames += ("-" + user.getUsername());
				}
			}
			return userNames;
		}
		return null;
	}

	/**
	 * 查找当前Task的ServiceCode
	 * 
	 * @param serviceMap
	 * @param taskDefinitionKey
	 * @return
	 */
	private TgServItemAndProcessor getServiceCode(List<TgServItemAndProcessor> serviceMap, String taskDefinitionKey) {
		String dicCode = taskDefinitionKey;
		if (serviceMap != null && !serviceMap.isEmpty()) {
			for (TgServItemAndProcessor tgs : serviceMap) {
				Dict dic = dictService.findDictByType(tgs.getSrvCode());
				if (containsDic(dic, dicCode)) {
					return tgs;
				}
			}
		}
		return null;
	}

	/**
	 * dicCode是否存在dic中
	 * 
	 * @param dic
	 * @param dicCode
	 * @return
	 */
	private boolean containsDic(Dict dic, String dicCode) {
		if (StringUtils.isBlank(dicCode) || dic == null)
			return false;
		if (dicCode.equals(dic.getCode())) {
			return true;
		}
		for (Dict subDic : dic.getChildren()) {
			if (containsDic(subDic, dicCode)) {
				return true;
			}
		}
		return false;
	}
}
