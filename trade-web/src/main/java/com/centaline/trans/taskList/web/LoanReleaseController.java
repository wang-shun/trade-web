package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;

@Controller
@RequestMapping("/task/loanRelease")
public class LoanReleaseController {
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private ToCaseService toCaseService;

	@RequestMapping(value = "process")
	public String toProcess(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {

		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);

		RestVariable psf = workFlowManager.getVar(processInstanceId, "PSFLoanNeed");/* 公积金 */
		boolean tz = !(boolean)(psf==null?false:psf.getValue());
		toAccesoryListService.getAccesoryList(request, taskitem);
		ToMortgage mortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
		//公积金的话无他证送抵时间
		if("30016003".equals(mortgage.getMortType())&&"1".equals(mortgage.getIsDelegateYucui())) {
			tz = false;
		}
		request.setAttribute("tz", tz);
		request.setAttribute("loanRelease", mortgage);
		return "task/taskLoanRelease";
	}
}
