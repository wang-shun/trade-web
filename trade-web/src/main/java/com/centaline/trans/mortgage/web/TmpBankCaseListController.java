package com.centaline.trans.mortgage.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;

@Controller
@RequestMapping(value="/tmpBank")
public class TmpBankCaseListController {
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private TsFinOrgService tsFinOrgService;

	@RequestMapping(value="list")
	public String list(Model model, HttpServletRequest request){
		SessionUser currentUser = uamSessionService.getSessionUser();
		
		List<TsFinOrg> tsFinOrgList = tsFinOrgService.getMainBankListInTempBankReport();
		
		request.setAttribute("tsFinOrgList", tsFinOrgList);
		request.setAttribute("currentOrgId", currentUser.getAdminOrg());
		
		return "tmpBank/list";
	}

}
