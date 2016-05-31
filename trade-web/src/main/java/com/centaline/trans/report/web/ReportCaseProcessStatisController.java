package com.centaline.trans.report.web;

import java.util.ArrayList;
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
			@RequestParam(value = "status", required = true) String status,
			@RequestParam(value = "org", required = true) String org,
			@RequestParam(value = "createTimeStart", required = true) String createTimeStart,
			@RequestParam(value = "createTimeEnd", required = true) String createTimeEnd) {

		SessionUser user = uamSessionService.getSessionUser();
		String depId = user.getServiceDepId();
		
		/*验证当前用户所属组织和url传来的是否一致*/
		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 如果是总经理
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
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的ID不符合!!!");
			}
		} else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) {// 如果是总监
			List<Org> orgList = uamUserOrgService.getOrgByParentId(depId); //组织集合
			List<String> orgs = new ArrayList<String>();
			if (orgList != null && !orgList.isEmpty()) {
				for (Org o : orgList) {
					orgs.add(o.getId());
				}
			}
			if (!depId.equals(org) && !orgs.contains(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的ID不符合!!!");
			}
		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())
				|| TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {// 如果是交易主管
			if (!depId.equals(org)) {
				throw new RuntimeException("不好意思,发生错误,组织ID与当前用户的ID不符合!!!");
			}
		}

		String orgName = uamUserOrgService.getOrgById(org).getOrgName(); //获取组织名
		request.setAttribute("createTimeStart", createTimeStart);
		request.setAttribute("createTimeEnd", createTimeEnd);
		request.setAttribute("org", org);
		request.setAttribute("orgName", orgName);
		request.setAttribute("depId", depId);
		request.setAttribute("status", status);

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
