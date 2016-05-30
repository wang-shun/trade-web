package com.centaline.trans.report.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
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
	UamSessionService uamSessionService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;

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
			@RequestParam(value = "orgs", required = false) String orgs,
			@RequestParam(value = "orgName", required = false) String orgName,
			@RequestParam(value = "createTimeStart", required = false) String createTimeStart,
			@RequestParam(value = "createTimeEnd", required = false) String createTimeEnd) {

		request.setAttribute("createTimeStart", createTimeStart);
		request.setAttribute("createTimeEnd", createTimeEnd);
		request.setAttribute("orgs", orgs);
		request.setAttribute("orgName", orgName);

		if (!"signed".equals(status) && !"transfered".equals(status)
				&& "closed".equals(status)) {
			status = "received";
		}
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

		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 如果是总经理
			List<Org> orgList = uamUserOrgService.getOrgByParentId(null);
			orgs = orgListToListStr(orgList);
		} else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) {// 如果是总监
			districtId =user.getServiceDepId();
			List<Org> orgList = uamUserOrgService.getOrgByParentId(districtId);
			orgs = orgListToListStr(orgList);
		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())
				|| TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {// 如果是交易主管
			org = user.getServiceDepId();
		} else {// 交易顾问
			userId = user.getId();
		}
	    String month =(new Date().getMonth()+1)+"";
		request.setAttribute("orgs", orgs);
		request.setAttribute("orgs", orgs);
		request.setAttribute("userId", userId);
		request.setAttribute("districtId", districtId);
		request.setAttribute("month", month);
		return "report/case_query_count";
	}

//	private Map<String, String> buildWorkSpaceBean(String serachId) {
//		Map<String, String> map = new HashMap<String, String>();
//		SessionUser user = uamSessionService.getSessionUser();
//		String orgs = null;
//		String org = null;
//		String userId = null;
//		if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode())) {// 如果是总经理
//			if (!StringUtils.isBlank(serachId)) {
//				List<Org> orgList = uamUserOrgService
//						.getOrgByParentId(serachId);
//				orgs = orgListToListStr(orgList);
//			}
//		} else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode())) { //
//			// 如果是总监
//			if (!StringUtils.isBlank(serachId)) {
//				org = serachId;
//			} else {
//				List<Org> orgList = uamUserOrgService.getOrgByParentId(user
//						.getServiceDepId());
//				orgs = orgListToListStr(orgList);
//			}
//		} else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode())
//				|| TransJobs.TJYZG.getCode().equals(user.getServiceJobCode())) {//
//			// 如果是交易主管
//			if (!StringUtils.isBlank(serachId)) {
//				userId = serachId;
//			} else {
//				org = user.getServiceDepId();
//			}
//		} else {
//			// 交易顾问
//			userId = user.getId();
//		}
//		map.put("userId", userId);
//		map.put("org", org);
//		map.put("orgs", orgs);
//		return map;
//	}

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
