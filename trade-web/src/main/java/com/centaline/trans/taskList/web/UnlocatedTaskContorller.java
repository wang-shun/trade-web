package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.extint.web.vo.ResponseVo;
import com.centaline.trans.task.service.UnlocatedTaskService;

@Controller
@RequestMapping(value = "/unlocatedTasks")
public class UnlocatedTaskContorller {
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	@Autowired
	private UamSessionService uamSesstionService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@RequestMapping()
	public String unlocatedTask(HttpServletRequest request) {
		SessionUser user = uamSesstionService.getSessionUser();
		String jobCode = user.getServiceJobCode();
		request.setAttribute("candidateId", user.getUsername());
		
		if ("yucui_team".equals(user.getServiceDepHierarchy())) {
			Org currentOrg = uamUserOrgService.getOrgById(user.getServiceCompanyId());
			Org parentOrg = uamUserOrgService.getOrgById(currentOrg.getParentId());
			
			request.setAttribute("serviceDepId", parentOrg.getId());
		}
		else {
			request.setAttribute("serviceDepId",user.getServiceCompanyId());
		}
		
		if (!TransJobs.TJYZG.getCode().equals(jobCode)) {
			request.setAttribute("managerFlag", "1");
		} else {
			request.setAttribute("orgId", user.getServiceDepId());
		}

		//return "/task/unlocatedTask";
		return "/task/unlocatedTask3";
	}
	/*@RequestMapping("/a")
	public String unlocatedTaska(HttpServletRequest request) {
		SessionUser user = uamSesstionService.getSessionUser();
		String jobCode = user.getServiceJobCode();
		request.setAttribute("candidateId", user.getUsername());
		if (!TransJobs.TJYZG.getCode().equals(jobCode)) {
			request.setAttribute("managerFlag", "1");
		} else {
			request.setAttribute("orgId", user.getServiceDepId());
		}
		
		//return "/task/unlocatedTask";
		return "/task/unlocatedTask3";
	}*/

	/**
	 * 分配任务
	 * 
	 * @param candidateId
	 * @param taskId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "doLocateTask/{taskId}/{candidateId}")
	public ResponseVo doLocateTask(
			@PathVariable(value = "candidateId") String candidateId,
			@PathVariable(value = "taskId") String taskId) {
		ResponseVo result = new ResponseVo();
		result.setSc("0");
		try {
			unlocatedTaskService.doLocateTask(candidateId, taskId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSc("1");
		}
		return result;
	}

	/**
	 * 抢任务
	 * 
	 * @param taskId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "doGroupClaim/{taskId}")
	public ResponseVo doGroupClaim(@PathVariable(value = "taskId") String taskId) {
		ResponseVo result = new ResponseVo();
		result.setSc("0");
		try {
			SessionUser user = uamSesstionService.getSessionUser();
			unlocatedTaskService.doGroupClaim(user.getUsername(), taskId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSc("1");
		}
		return result;
	}
}
