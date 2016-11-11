package com.centaline.trans.report.web;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;

/**
 * 
 * <p>
 * Project: 驳回率和流失率
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright (c) 2016
 * </p>
 * 
 * @author hejf</a>
 */
@Controller
@RequestMapping(value = "/report")
public class CaseRejectionRateController {
	@RequestMapping(value = "caseLossRateReasonList")
	public String caseLossRateReasonList(Model model, ServletRequest request) {
		return "report/case_loss_rate_reason_list";
	}
	@RequestMapping(value = "caseLossRateList")
	public String caseLossrateList(Model model, ServletRequest request) {
		return "report/case_loss_rate_list";
	}
}
