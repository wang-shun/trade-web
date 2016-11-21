package com.centaline.trans.report.web;

import javax.servlet.ServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@RequiresPermissions("TRADE.REPORT.CASELOSS")
	public String caseLossRateReasonList(Model model, ServletRequest request) {
		return "report/case_loss_rate_reason_list";
	}
	@RequestMapping(value = "caseLossRateList")
	@RequiresPermissions("TRADE.REPORT.CASELOSSRATE")
	public String caseLossrateList(Model model, ServletRequest request) {
		return "report/case_loss_rate_list";
	}
}
