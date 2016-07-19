package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.service.ToTransPlanService;

@Controller
@RequestMapping("task/PSFApply")
public class PSFApplyController {
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private ToTransPlanService toTransPlanService;
	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private ToCaseService toCaseService;

	@RequestMapping("process")
	public String toLoanLostApproveManagerProcess(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		toAccesoryListService.getAccesoryList(request, taskitem);
		ToTransPlan toTransPlan = new ToTransPlan();
		toTransPlan.setPartCode(taskitem);
		toTransPlan.setCaseCode(caseCode);
		request.setAttribute("toTransPlan", toTransPlanService.findTransPlan(toTransPlan));
		request.setAttribute("apply", toMortgageService.findToMortgageByMortTypeAndCaseCode(caseCode, "30016003"));// --
		return "task/taskPSFApply";
	}
}
