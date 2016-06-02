package com.centaline.trans.report.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

		String statusVal = null;
		if("signed".equals(status)){
			statusVal="2";
		}else if("transfered".equals(status)){
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
	 * 案件状态基本统计
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "caseQuery")
	public String caseQuery(Model model, ServletRequest request) {
		SessionUser user = uamSessionService.getSessionUser();
		String orgs = null;
		String org = null;
		String userId = null;
		String districtId = null;
		String transJob = null;

		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 如果是总经理
			List<Org> orgList = uamUserOrgService.getOrgByParentId(user
					.getServiceDepId());
			orgs = orgListToListStr(orgList);
			transJob = "GeneralManager";
		} else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) {// 如果是总监
			districtId = user.getServiceDepId();
			List<Org> orgList = uamUserOrgService.getOrgByParentId(districtId);
			orgs = orgListToListStr(orgList);
			transJob = "director";
		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())
				|| TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {// 如果是交易主管
			org = user.getServiceDepId();
		} else {// 交易顾问
			userId = user.getId();
		}
		String month = (new Date().getMonth() + 1) + "";
		request.setAttribute("org", org);
		request.setAttribute("orgs", orgs);
		request.setAttribute("userId", userId);
		request.setAttribute("districtId", districtId);
		request.setAttribute("transJob", transJob);
		request.setAttribute("month", month);
		return "report/case_query_count";
	}

	/**
	 * 将组织转换为字符串
	 * 
	 * @param orgs
	 * @return
	 */
	private String orgListToListStr(List<Org> orgs) {
		StringBuffer sb = new StringBuffer();
		if (orgs == null || orgs.isEmpty())
			return null;
		for (Org org : orgs) {
			sb.append(org.getId() + ",");
		}
		String strOrgs = sb.toString();
		strOrgs.substring(0, strOrgs.length() - 1);
		return strOrgs;
	}
}
