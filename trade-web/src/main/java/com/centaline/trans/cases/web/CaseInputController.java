package com.centaline.trans.cases.web;



import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.CaseMergeService;


/**
 * @author  hejf
 * @description  自录单列表相关
 * @date  2017年4月11日10:45:58
 */
@Controller
@RequestMapping(value = "/newCase")
public class CaseInputController {
	
	@Autowired(required = true)
	CaseMergeService caseMergeService;	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	/**
	 * 页面跳转 
	 * @author hejf10
	 * @param request
	 * @return
	 */

	@RequestMapping(value="input")
	public String inputCaseInfo(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		String queryUserId = user.getId();
		request.setAttribute("queryUserId", queryUserId);
		return "case/newCaselist";
	}	
	/**
	 * 外单案件列表页面跳转 
	 * @author hejf10
	 * @2017年5月15日16:04:48
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="inputWD")
	public String inputWDCaseInfo(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		String queryUserId = user.getId();
		request.setAttribute("queryUserId", queryUserId);
		return "case/newWDCaselist";
	}	
}
