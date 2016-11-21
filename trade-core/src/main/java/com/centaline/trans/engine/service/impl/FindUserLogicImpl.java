package com.centaline.trans.engine.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.engine.bean.TaskHistoricQuery;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.FindUserLogic;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.service.impl.TsTaskDelegateServiceImpl;

/**
 * 
 * @author jimmy 流程通用找人逻辑封装
 * 
 */
@Component
public class FindUserLogicImpl implements FindUserLogic {
	/**
	 * yucui总部orgCode
	 */
	public static final String YC_ORG_CODE = TsTaskDelegateServiceImpl.YC_ORG_CODE;

	@Autowired
	private UamBasedataService dictService;
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

	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private ToWorkFlowService workFlowService;
	private List<String>psfTasks=Arrays.asList("PSFApply","PSFSign","PSFApprove");

	/**
	 * 
	 * @return yucuiHeadQuarteSet
	 */
	public Set<String> getYucuiHeadQuartes() {
		if (yucuiHeadQuarteSet == null) {
			yucuiHeadQuarteSet = new HashSet<String>();
			if (!StringUtils.isBlank(yucuiHeadQuarte)) {
				yucuiHeadQuarteSet.addAll(Arrays.asList(yucuiHeadQuarte
						.split(",")));
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
				yucuiDistrictSet
						.addAll(Arrays.asList(yucuiDistrict.split(",")));
			}
		}
		return yucuiDistrictSet;
	}

	private String findRejectUser(String groupId, String taskDefinitionKey,
			String processInstanceId) {
		String[] groups = null;
		if (groupId != null && groupId.contains("-")) {
			groups = groupId.split("-");
		} else {
			groups = new String[] { groupId };
		}
		TaskHistoricQuery tq = new TaskHistoricQuery();
		tq.setTaskDefinitionKey(taskDefinitionKey);
		tq.setFinished(true);
		tq.setProcessInstanceId(processInstanceId);
		PageableVo vo = workFlowManager.listHistTasks(tq);
		List list = vo.getData();
		String tempRejectUser = null;
		Long maxId = null;
		for (Object obj : list) {
			TaskVo taskVo = (TaskVo) obj;
			if (maxId == null || maxId < taskVo.getId()) {
				maxId = taskVo.getId();
				tempRejectUser = taskVo.getAssignee();
			}
		}
		if(tempRejectUser==null)return null;
		User u = uamUserOrgService.getUserByUsername(tempRejectUser);
		if (u != null) {
			List<UserOrgJob> jobs = uamUserOrgService.getUserOrgJobByUserId(u
					.getId());
			for (String g : groups) {
				if (containsJob(jobs, g))
					return u.getUsername();
			}
		}
		return null;
	}

	/**
	 * @see com.centaline.trans.engine.service
	 *      。FindUserLogic#findWorkFlowUser(String, String, Map, String)
	 * @return
	 */
	@Override
	public String findWorkFlowUser(String groupId, String caseowner,
			Map<String, String> serviceMap, String taskDefinitionKey,
			String processInstanceId) {
		String[] groups = null;
		String tempUser = null;
		String rejectUser=null;
		if(!psfTasks.contains(taskDefinitionKey)){//公积金相关任务不取上次处理人
			rejectUser = findRejectUser(groupId, taskDefinitionKey,
					processInstanceId);
		}
		if (rejectUser != null) {
			return rejectUser;
		}
		if (groupId != null && groupId.contains("-")) {
			groups = groupId.split("-");
		} else {
			groups = new String[] { groupId };
		}

		ToWorkFlow twf = workFlowService
				.queryWorkFlowByInstCode(processInstanceId);
		if (twf != null) {
			caseowner = twf.getProcessOwner();
		} else {
			caseowner = null;
		}
		for (String g : groups) {
			String t = findWorkFlowUserForOneGroup(g, caseowner, serviceMap,
					taskDefinitionKey);
			if (t != null) {
				if (tempUser == null)
					tempUser = "";
				tempUser += (t + "-");
			}
		}
		if (tempUser != null && tempUser.length() > 1) {
			tempUser = tempUser.substring(0, tempUser.length() - 1);
		}
		return tempUser;
	}

	private String findWorkFlowUserForOneGroup(String groupId,
			String caseowner, Map<String, String> serviceMap,
			String taskDefinitionKey) {

		String userId = caseowner;
		String serviceCode = getServiceCode(serviceMap, taskDefinitionKey);
		String serviceUserId = serviceMap.get(serviceCode);
		if (!StringUtils.isBlank(serviceUserId)) {
			userId = serviceUserId;
		}
		return findWorkFlowUser(groupId, userId);
	}

	/**
	 * 流程通用找人逻辑
	 * 
	 * @param groupId
	 *            对应Sales系统中的jobCode
	 * @param baseonUserId
	 * @return
	 */
	public String findWorkFlowUser(String groupId, String baseonUserId) {
		if(baseonUserId==null)return null;
		User baseonuser = uamUserOrgService.getUserById(baseonUserId);
		if (baseonuser == null)
			return null;
		List<UserOrgJob> jobs = uamUserOrgService
				.getUserOrgJobByUserId(baseonuser.getId());
		if (containsJob(jobs, groupId))
			return baseonuser.getUsername();
		if (getYucuiHeadQuartes().contains(groupId))
			return findHQUser(groupId, baseonuser.getOrgId());
		else if (getYucuiDistricts().contains(groupId))
			return findDistrictUser(groupId, baseonuser.getOrgId());
		else
			return findTeam(groupId, baseonuser.getOrgId());

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
		List<User> users = uamUserOrgService.getUserByOrgIdAndJobCode(orgId,
				groupId);
		if (users != null && !users.isEmpty() && users.size() < 2)
			return users.get(0).getUsername();
		else if (users != null &&users.size() > 1) {
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
	 * 是否存在相应的Job
	 * 
	 * @param jobs
	 * @param groupId
	 * @return
	 */
	private boolean containsJob(List<UserOrgJob> jobs, String groupId) {
		if (jobs == null || jobs.isEmpty() || StringUtils.isBlank(groupId))
			return false;
		for (UserOrgJob userOrgJob : jobs) {
			if (groupId.equals(userOrgJob.getJobCode())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 查找当前Task的ServiceCode
	 * 
	 * @param serviceMap
	 * @param taskDefinitionKey
	 * @return
	 */
	private String getServiceCode(Map<String, String> serviceMap,
			String taskDefinitionKey) {
		String dicCode = taskDefinitionKey;
		if (serviceMap != null && !serviceMap.isEmpty()) {
			for (String serviceCode : serviceMap.keySet()) {
				Dict dic = dictService.findDictByType(serviceCode);
				if (containsDic(dic, dicCode)) {
					return serviceCode;
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
