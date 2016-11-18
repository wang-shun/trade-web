package com.centaline.trans.report.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;

/**
 * 
 * <p>
 * Project: 过户案件总览
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright (c) 2016
 * </p>
 * 
 * @author zhoujp</a>
 */
@Controller
@RequestMapping(value = "/report")
public class CaseTransferController {

	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	UamBasedataService uamBasedataService;

	/**
	 * 页面初始化
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "caseTransferList")
	public String caseTransferList(Model model, ServletRequest request) {
		// TODO
		SessionUser user = uamSessionService.getSessionUser();
		String userJob = user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;

		StringBuffer reBuffer = new StringBuffer();
		if (!userJob.equals(TransJobs.TJYGW.getCode())) {
			queryOrgFlag = true;
			String depString = user.getServiceDepHierarchy();
			String userOrgIdString = user.getServiceDepId();
			if (depString.equals(DepTypeEnum.TYCTEAM.getCode())) {
				reBuffer.append(userOrgIdString);
			} else if (depString.equals(DepTypeEnum.TYCQY.getCode())) {
				List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(
						userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
				for (Org org : orgList) {
					reBuffer.append(org.getId());
					reBuffer.append(",");
				}
				reBuffer.deleteCharAt(reBuffer.length() - 1);

			} else {
				isAdminFlag = true;
			}
		}
		request.setAttribute("queryOrgs", reBuffer.toString());

		request.setAttribute("queryOrgFlag", queryOrgFlag);
		request.setAttribute("isAdminFlag", isAdminFlag);
		// 页面获取 组织结构显示用 服务部门ID
		request.setAttribute("serviceDepId", user.getServiceDepId());

		String userId = null; // 交易顾问id
		String tempUser = null; // 交易主管下用户id
		String tempName = null; // 交易主管下用户姓名
		request.setAttribute("userId", userId);
		request.setAttribute("tempUser", tempUser);
		request.setAttribute("tempName", tempName);

		// 默认显示上周一至周日的时间
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		int dayOfWeek = c1.get(Calendar.DAY_OF_WEEK) - 1;
		c1.add(Calendar.DATE, -dayOfWeek - 6);
		c2.add(Calendar.DATE, -dayOfWeek);
		String start = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());// last
																				// Monday
		String end = new SimpleDateFormat("yyyy-MM-dd").format(c2.getTime());// last
																				// Sunday
		request.setAttribute("start", start);
		request.setAttribute("end", end);

		return "report/case_transfer";
	}
}
