package com.centaline.trans.TestAction.web;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.MortStepService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.MortgageSelectService;


@Controller
@RequestMapping(value="/test")
public class TestActionController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	ToMortgageService ToMortgageService;
	@Autowired(required = true)
	 UamUserOrgService uamUserOrgService;
	//caseLog
	@RequestMapping("caseLog")
	public String caseLog(HttpServletRequest request) {
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("user", user);
		return "/testAction/synCaseLog";
	}
	@RequestMapping("taskLog")
	public String taskLog(HttpServletRequest request) {
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("user", user);
		return "/testAction/taskLog";
	}
	
	@RequestMapping("mortgageList")
	public String mortgageList(HttpServletRequest request) {
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("user", user);
		return "/testAction/mortgageList";
	}
	
	@RequestMapping("updateMortgage")
	public String updateMortgage(HttpServletRequest request,Long pkid) {
		SessionUser user = uamSessionService.getSessionUser();
		ToMortgage mortgage= ToMortgageService.findToMortgageById(pkid);
		User createBy  =uamUserOrgService.getUserById(mortgage.getCreateBy());
		User updateBy  =uamUserOrgService.getUserById(mortgage.getUpdateBy());
		if(createBy!=null){
		request.setAttribute("createBy", createBy.getRealName());
		}else{
			request.setAttribute("createBy", "");	
		}
		if(updateBy!=null){
		request.setAttribute("updateBy", updateBy.getRealName());
		}else{
			request.setAttribute("updateBy", "");				
		}
		request.setAttribute("user", user);
		request.setAttribute("mortgage", mortgage);
		return "/testAction/updateMortgage";
	}
	@RequestMapping("saveMortgage")
	public AjaxResponse<String> saveMortgage(Model model,ToMortgage mortgage){
		try {
			SessionUser user = uamSessionService.getSessionUser();
			String updateBy = user.getRealName();
			mortgage.setUpdateBy(updateBy);
			mortgage.setUpdateTime(new Date());
			ToMortgageService.updateByTest(mortgage);
			return AjaxResponse.success("操作成功");
		} catch(Exception e) {
			logger.debug("保存E+申请失败", e);
			return AjaxResponse.fail("操作失败");
		}
	}
	
	
}
