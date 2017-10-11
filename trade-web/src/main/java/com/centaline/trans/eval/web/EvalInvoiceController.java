package com.centaline.trans.eval.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.utils.UiImproveUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.cases.service.MyCaseListService;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.eval.entity.ToEvaCommissionChange;
import com.centaline.trans.eval.entity.ToEvaFeeRecord;
import com.centaline.trans.eval.service.ToEvaCommissionChangeService;
import com.centaline.trans.eval.service.ToEvaFeeRecordService;



/**
 * @author xiefei1
 * @since 2017年9月25日 上午11:19:52 
 * @description 评估费发票管理
 */
@Controller
@RequestMapping(value = "/eval")
public class EvalInvoiceController {

	@Autowired(required = true)
	private UamSessionService uamSessionService;
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	private UamBasedataService uamBasedataService;
	@Autowired
	private ToEvaCommissionChangeService toEvaCommissionChangeService;
	@Autowired
	private UamPermissionService uamPermissionService;


	@RequestMapping(value = "invoiceAudit")
	public String toInvoiceAudit(HttpServletRequest request,Model model,String caseCode) {
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		model.addAttribute("caseCode", caseCode);
		return "eval/invoiceAudit";
	}
	
	@RequestMapping(value = "invoiceApply")
	public String toInvoiceApply(HttpServletRequest request,Model model,String caseCode) {
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		model.addAttribute("caseCode", caseCode);
		return "eval/invoiceApply";
	}
	
	@RequestMapping(value = "issueInvoice")
	public String toIssueInvoice(HttpServletRequest request,Model model,String caseCode) {
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		model.addAttribute("caseCode", caseCode);
		return "eval/issueInvoice";
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public HashMap<String, Object> saveEval(HttpServletRequest request,ToEvaCommissionChange toEvaCommissionChange) {
		int insertSelective = toEvaCommissionChangeService.insertSelective(toEvaCommissionChange);
		HashMap<String, Object> resultMap = new HashMap<String,Object>();
		if(insertSelective==1){
			resultMap.put("data", "success");
		}else{
			resultMap.put("data", "error");
		}
		return resultMap;
	}

	
	

	


}
