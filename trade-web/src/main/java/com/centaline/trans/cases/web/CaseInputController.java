package com.centaline.trans.cases.web;



import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.CaseMergeService;
import com.centaline.trans.common.entity.CaseMergerParameter;


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
	 * 自录单列表详细
	 * @author hejf10
	 * @param request
	 * @return
	 */
	@RequestMapping(value="inputt")
	@ResponseBody
	public AjaxResponse<?> updateMergeCase(CaseMergerParameter caseInfo, HttpServletRequest request){
		AjaxResponse<Boolean> response = new AjaxResponse<>();
		try{
			//toCaseService.updateMergeCase(caseInfo);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			String sOut = "";
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement s : trace) {  sOut += "\tat " + s + "\r\n"; }
			/**response.setMessage(e.getMessage()+"异常："+sOut);**/
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		if(StringUtils.equals(caseInfo.getType(), "1")){response.setContent(true);}
		if(StringUtils.equals(caseInfo.getType(), "0")){response.setContent(false);}
		return response;
	}	
}
