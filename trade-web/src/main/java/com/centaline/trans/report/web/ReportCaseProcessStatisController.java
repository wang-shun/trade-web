package com.centaline.trans.report.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.enums.TransJobs;

/**
 * 案件流程统计
 * 
 * @author maximum-wong
 *
 */
@Controller
@RequestMapping("/report/statis")
public class ReportCaseProcessStatisController {

	@Autowired(required = true)
	private UamSessionService uamSessionService;
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;

	/**
	 * 案件详情
	 * 
	 * @param model
	 * @param request
	 * @param status
	 * @param orgs
	 * @param orgName
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @return
	 */
	@RequestMapping(value = "caseDetail")
	public String caseDetail(
			Model model,
			ServletRequest request,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "org", required = false) String org,
			@RequestParam(value = "createTimeStart", required = false) String createTimeStart,
			@RequestParam(value = "createTimeEnd", required = false) String createTimeEnd,
			@RequestParam(value = "arg", required = false) String arg) {

		SessionUser user = uamSessionService.getSessionUser();

		/* arg参数处理 */
		if (org != null && !"".equals(org)) {
			arg = null;
		}

		/* url传参空值处理 */
		if (null == status || "".equals(status)) {
			status = "received";
		}
		if (null == org || "".equals(org)) {
			org = user.getServiceDepId();
		}
		SimpleDateFormat format = null;
		if (null == createTimeStart || "".equals(createTimeStart)) {
			format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			createTimeStart = format.format(c.getTime());
		}
		if (null == createTimeEnd || "".equals(createTimeEnd)) {
			format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar ca = Calendar.getInstance();
			ca.set(Calendar.DAY_OF_MONTH,
					ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			createTimeEnd = format.format(ca.getTime());
		}

		String depId = user.getServiceDepId(); // 用户的部门
		String userId = null; // 交易顾问id
		String tempUser = null; // 交易主管下用户id
		String tempName = null; // 交易主管下用户姓名
		boolean isConsultant=false; //是否为交易顾问
		String personalId = user.getId();
		
		/* 验证当前用户所属组织和url传来的值是否一致 */
		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 总经理
			if (arg != null && !"".equals(arg)) {
				org = arg;
			}
			List<Org> orgList = uamUserOrgService.getOrgByParentId(depId);
			List<String> disOrgs = new ArrayList<String>(); // 贵宾服务组集合
			List<String> orgs = new ArrayList<String>(); // 组织集合
			if (orgList != null && !orgList.isEmpty()) {
				for (Org o : orgList) {
					disOrgs.add(o.getId());
					List<Org> subOrgs = uamUserOrgService.getOrgByParentId(o
							.getId());
					if (subOrgs != null && !subOrgs.isEmpty()) {
						for (Org oo : subOrgs) {
							orgs.add(oo.getId());
						}
					}
				}
			}
			if (!depId.equals(org) && !disOrgs.contains(org)
					&& !orgs.contains(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的不符合!!!");
			}
		} else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) {// 誉萃总监
			if (arg != null && !"".equals(arg)) {
				org = arg;
			}
			List<Org> orgList = uamUserOrgService.getOrgByParentId(depId); // 组织集合
			List<String> orgs = new ArrayList<String>();
			if (orgList != null && !orgList.isEmpty()) {
				for (Org o : orgList) {
					orgs.add(o.getId());
				}
			}
			if (!depId.equals(org) && !orgs.contains(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的不符合!!!");
			}
		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())
				|| TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {// 交易主管
			if (!depId.equals(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的不符合!!!");
			}
			if (arg != null && !"".equals(arg)) {
				tempUser = arg;
				tempName = uamUserOrgService.getUserById(tempUser).getRealName();
				List<String> uList = new ArrayList<String>();
				List<User> userList = uamUserOrgService
						.getUserByOrgIdAndJobCode(user.getServiceDepId(),
								TransJobs.TJYGW.getCode());
				if (null != userList && !userList.isEmpty()) {
					for (User u : userList) {
						uList.add(u.getId());
					}
				}
				if (!uList.contains(tempUser)) {
					throw new RuntimeException("不好意思,发生错误,此交易主管下无该交易顾问!!!");
				}
			}
		} else { // 交易顾问
			userId = user.getId();
			isConsultant = true;
			if (!depId.equals(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的不符合!!!");
			}
		}

		String orgName = uamUserOrgService.getOrgById(org).getOrgName(); // 获取组织名
		request.setAttribute("createTimeStart", createTimeStart);
		request.setAttribute("createTimeEnd", createTimeEnd);
		request.setAttribute("org", org);
		request.setAttribute("orgName", orgName);
		request.setAttribute("depId", depId);
		request.setAttribute("status", status);
		request.setAttribute("userId", userId);
		request.setAttribute("tempUser", tempUser);
		request.setAttribute("tempName", tempName);
		request.setAttribute("isConsultant", isConsultant);
		request.setAttribute("personalId", personalId);

		String statusVal = null;
		if("signed".equals(status)){
			statusVal="2";
		}else if("loanApply".equals(status)){
			statusVal="3";
		}else if("closed".equals(status)){
			statusVal="4";
		}else{
			statusVal="1";
		}
		request.setAttribute("statusVal", statusVal);
		
		return "report/case_detail";
	}

	/**
	 * 案件状汇总统计
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "caseQuery")
	public String caseQuery(			ServletRequest request,
			@RequestParam(value = "createTimeStart", required = false) String createTimeStart,
			@RequestParam(value = "createTimeEnd", required = false) String createTimeEnd,
			@RequestParam(value = "arg", required = false) String arg) {
		
		SessionUser user = uamSessionService.getSessionUser();

		/* url传参空值处理 */
		SimpleDateFormat format = null;
		if (null == createTimeStart || "".equals(createTimeStart)) {
			format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			createTimeStart = format.format(c.getTime());
		}
		if (null == createTimeEnd || "".equals(createTimeEnd)) {
			format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar ca = Calendar.getInstance();
			ca.set(Calendar.DAY_OF_MONTH,
					ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			createTimeEnd = format.format(ca.getTime());
		}
		
		String depId = user.getServiceDepId(); // 用户的部门
		String tempUser = null; // 交易主管下用户id
		String org = depId; //用户组织默认值
		String transJob = null; //用户职位
		
		/* 验证当前用户所属组织和url传来的值是否一致 */
		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 总经理
			transJob = "誉萃总经理";
			if (arg != null && !"".equals(arg)) {
				org = arg;
			}
			List<Org> orgList = uamUserOrgService.getOrgByParentId(depId);
			List<String> disOrgs = new ArrayList<String>(); // 贵宾服务组集合
			List<String> orgs = new ArrayList<String>(); // 组织集合
			if (orgList != null && !orgList.isEmpty()) {
				for (Org o : orgList) {
					disOrgs.add(o.getId());
					List<Org> subOrgs = uamUserOrgService.getOrgByParentId(o
							.getId());
					if (subOrgs != null && !subOrgs.isEmpty()) {
						for (Org oo : subOrgs) {
							orgs.add(oo.getId());
						}
					}
				}
			}
			if (!depId.equals(org) && !disOrgs.contains(org)
					&& !orgs.contains(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的不符合!!!");
			}
		} else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) {// 誉萃总监
			transJob="誉萃总监";
			if (arg != null && !"".equals(arg)) {
				org = arg;
			}
			List<Org> orgList = uamUserOrgService.getOrgByParentId(depId); // 组织集合
			List<String> orgs = new ArrayList<String>();
			if (orgList != null && !orgList.isEmpty()) {
				for (Org o : orgList) {
					orgs.add(o.getId());
				}
			}
			if (!depId.equals(org) && !orgs.contains(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的不符合!!!");
			}
		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())
				|| TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {// 交易主管
			transJob="交易主管";
			if (arg != null && !"".equals(arg)) {
				tempUser = arg;
				List<String> uList = new ArrayList<String>();
				List<User> userList = uamUserOrgService
						.getUserByOrgIdAndJobCode(user.getServiceDepId(),
								TransJobs.TJYGW.getCode());
				if (null != userList && !userList.isEmpty()) {
					for (User u : userList) {
						uList.add(u.getId());
					}
				}
				if (!uList.contains(tempUser)) {
					throw new RuntimeException("不好意思,发生错误,此交易主管下无该交易顾问!!!");
				}
			}
		} 
		
		String orgName = uamUserOrgService.getOrgById(org).getOrgName(); // 获取组织名
		
		request.setAttribute("orgName", orgName);
		request.setAttribute("org", org);
		request.setAttribute("depId", depId);
		request.setAttribute("tempUser", tempUser);
		request.setAttribute("transJob", transJob);
		request.setAttribute("createTimeStart", createTimeStart);
		request.setAttribute("createTimeEnd", createTimeEnd);
		
		return "report/case_query_count";
	}

	/**
	 * 历史任务统计
	 * @param model
	 * @param request
	 * @param status
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param arg
	 * @return
	 */
	@RequestMapping(value="historyTaskList")
	public String getTaskList(Model model,
			ServletRequest request,
			@RequestParam(value = "taskName", required = false) String taskName,
			@RequestParam(value = "handleTimeStart", required = false) String handleTimeStart,
			@RequestParam(value = "handleTimeEnd", required = false) String handleTimeEnd,
			@RequestParam(value = "arg", required = false) String arg){
		
		SessionUser user = uamSessionService.getSessionUser();
		String org = null;
		String consultantId = null;
		boolean isConsultant=false; //是否为交易顾问
		
		String depId = user.getServiceDepId(); // 用户的部门
		
		
		SimpleDateFormat format = null;
		if (null == handleTimeStart || "".equals(handleTimeStart)) {
			format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			handleTimeStart = format.format(c.getTime());
		}
		if (null == handleTimeEnd || "".equals(handleTimeEnd)) {
			format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar ca = Calendar.getInstance();
			ca.set(Calendar.DAY_OF_MONTH,
			ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			handleTimeEnd = format.format(ca.getTime());
		}
		
		
		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {//总经理
			if(arg != null && !"".equals(arg)){
				org = arg;
			}else{
				org=user.getServiceDepId();
			}
		} else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) { //总监
			if (arg != null && !"".equals(arg)) {
				org = arg;
			} else {
				org = user.getServiceDepId();
			}
		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())||TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {//交易主管
			if (arg != null && !"".equals(arg)) {
				consultantId = arg;
			} 
			org = user.getServiceDepId();
		} else { //交易顾问
			consultantId = user.getId();
			isConsultant=true;
			org = user.getServiceDepId();
		}
		
		String orgName=null;
		if(org!=null){
			orgName = uamUserOrgService.getOrgById(org).getOrgName(); // 获取组织名
		}
		
		String consultantName=null;
		if(consultantId!=null){
			consultantName= uamUserOrgService.getUserById(consultantId).getRealName();
		}
		
		model.addAttribute("taskName", taskName);
		model.addAttribute("handleTimeStart", handleTimeStart);
		model.addAttribute("handleTimeEnd", handleTimeEnd);
		model.addAttribute("org", org);
		model.addAttribute("consultantId", consultantId);
		model.addAttribute("consultantName", consultantName);
		model.addAttribute("isConsultant", isConsultant);
		model.addAttribute("depId", depId);
		model.addAttribute("orgName", orgName);
		return "report/history_taskList";
	}
	
}
