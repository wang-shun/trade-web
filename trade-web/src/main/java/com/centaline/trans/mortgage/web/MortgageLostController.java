package com.centaline.trans.mortgage.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;

@Controller
@RequestMapping(value = "mortgage")
public class MortgageLostController {
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@RequestMapping(value = "/mortgageApproveLost")
	public String personBonus(HttpServletRequest request) {
		return "mortgage/mortgageApproveLost";
	}
}
