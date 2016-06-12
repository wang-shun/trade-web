package com.centaline.trans.taskList.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.task.service.MortgageSelectService;
import com.centaline.trans.task.vo.MortgageSelecteVo;

@Controller
@RequestMapping(value = "/task/mortgageSelect")
public class MortgageSelectController {
	@Inject
	private UamSessionService uamSessionService;
	@Inject
	private MortgageSelectService mortgageSelectService;
	@ResponseBody
	@RequestMapping(value = "submit")
	public boolean submit(MortgageSelecteVo vo, HttpServletRequest request){
		if(StringUtils.isBlank(vo.getPartner())){
			SessionUser u=uamSessionService.getSessionUser();
			vo.setPartner(u.getId());
		}
		String crossPartner = (String)request.getParameter("crossPartner");
		if(("-1".equals(vo.getPartner()))&&(crossPartner!=null)&&(!"".equals(crossPartner))){
			vo.setPartner(crossPartner);
		}
		return mortgageSelectService.submit(vo);
	}
}
