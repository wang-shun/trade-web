package com.centaline.trans.report.web;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;

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

	/**
	 * 案件详情
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="caseDetail")
	public String caseDetail(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		String userJob=user.getServiceJobCode();

		return "report/case_detail";
	}
}
