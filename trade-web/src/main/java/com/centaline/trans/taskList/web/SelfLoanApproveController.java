package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.LoanlostApproveService;

@Controller
@RequestMapping("/task/selfLoanApprove")
public class SelfLoanApproveController {
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private LoanlostApproveService loanlostApproveService;
	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;

	@RequestMapping(value = "process")
	public String toProcess(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("approveType", "1");
		request.setAttribute("operator", user != null ? user.getId() : "");
		request.setAttribute("caseDetail",
				loanlostApproveService.queryCaseInfo(caseCode, "LoanlostApply", processInstanceId));
		ToMortgage mortgage = toMortgageService.findToSelfLoanMortgage(caseCode);
		request.setAttribute("SelfLoan", mortgage);
		if (mortgage != null && mortgage.getCustCode() != null) {
			TgGuestInfo guest = tgGuestInfoService.selectByPrimaryKey(Long.parseLong(mortgage.getCustCode()));
			if (null != guest) {
				request.setAttribute("custCompany", guest.getWorkUnit());
				request.setAttribute("custName", guest.getGuestName());
			}
		}
		;
		return "task/taskSelfLoanApprove";
	}
}
