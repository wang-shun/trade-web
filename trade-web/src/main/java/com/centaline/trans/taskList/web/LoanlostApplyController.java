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
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;

@Controller
@RequestMapping("/task/loanlostApply")
public class LoanlostApplyController {
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	@Autowired
	private ToApproveRecordService toApproveRecordService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private ToCaseService toCaseService;

	@RequestMapping(value = "process")
	public String toProcess(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("approveType", "1");
		request.setAttribute("operator", user != null ? user.getId() : "");
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		toAccesoryListService.getAccesoryList(request, taskitem);
		/** 这里应该和自办贷款一样 */
		ToMortgage mortgage = toMortgageService.findToSelfLoanMortgage(caseCode);
		request.setAttribute("mortgage", mortgage);
		if (mortgage != null && mortgage.getCustCode() != null) {
			TgGuestInfo guest = tgGuestInfoService.selectByPrimaryKey(Long.parseLong(mortgage.getCustCode()));
			if (null != guest) {
				request.setAttribute("custCompany", guest.getWorkUnit());
				request.setAttribute("custName", guest.getGuestName());
			}
		}

		ToApproveRecord r = new ToApproveRecord();
		r.setCaseCode(caseCode);
		r.setPartCode("LoanlostApply");
		r.setProcessInstance(processInstanceId);
		request.setAttribute("toApproveRecord", toApproveRecordService.queryToApproveRecord(r));
		return "task/taskLoanlostApply";
	}
}
