package com.centaline.trans.taskList.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.service.MortgageSelectService;
import com.centaline.trans.task.service.ToTransPlanService;
import com.centaline.trans.task.vo.MortgageSelecteVo;

@Controller
@RequestMapping(value = "/task/mortgageSelect")
public class MortgageSelectController {
	@Inject
	private UamSessionService uamSessionService;
	@Inject
	private MortgageSelectService mortgageSelectService;
	@Inject
	private ToTransPlanService toTransPlanService;
	@Inject
	private ToCaseService toCaseService;

	@ResponseBody
	@RequestMapping(value = "submit")
	public boolean submit(MortgageSelecteVo vo){
		if (StringUtils.isBlank(vo.getPartner())) {
			SessionUser u = uamSessionService.getSessionUser();
			vo.setPartner(u.getId());
		}
		return mortgageSelectService.submit(vo);
	}
	@ResponseBody
	@RequestMapping(value = "getLoanReleasePlan")
	public ToTransPlan getLoanReleasePlan(String caseCode){
		ToTransPlan queryPlan = new ToTransPlan();
	
		queryPlan.setCaseCode(caseCode);
		queryPlan.setPartCode("LoanRelease");
		queryPlan = toTransPlanService.findTransPlan(queryPlan);
		return queryPlan;
	}
	@ResponseBody
	@RequestMapping(value = "loanRequirementChange")
	public AjaxResponse<?> loanRequirementChange(MortgageSelecteVo vo) {
		if (StringUtils.isBlank(vo.getPartner())) {
			SessionUser u = uamSessionService.getSessionUser();
			vo.setPartner(u.getId());
		}
		try {
			mortgageSelectService.loanRequirementChange(vo);
		} catch (BusinessException ex) {
			return AjaxResponse.fail(ex.getMessage());
		}
		return AjaxResponse.success("变更成功");
	}
	
	@RequestMapping(value = "process")
	public String toProcess(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		ToTransPlan plan=new ToTransPlan();
		plan.setCaseCode(caseCode);
		plan.setPartCode("LoanRelease");//放款
		request.setAttribute("loanReleasePlan", toTransPlanService.findTransPlan(plan));
		return "task/taskMortgageSelect";
	}
}
