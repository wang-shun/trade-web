package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.eloan.entity.ToCloseLoan;
import com.centaline.trans.eloan.service.ToCloseLoanService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.utils.UiImproveUtil;

@Controller
@RequestMapping(value = "/task/closeLoan")
public class CloseLoanController {

	@Autowired
	private ToCloseLoanService toCloseLoanService;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;

	@Autowired
	private TgGuestInfoService tgGuestInfoService;

	@RequestMapping(value = "process")
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("loanClose",
				toCloseLoanService.qureyToCloseLoan(caseCode));
		return "task" + UiImproveUtil.getPageType(request) + "/taskLoanClose";
	}

	@RequestMapping(value = "saveToCloseLoan")
	public String saveToCloseLoan(HttpServletRequest request,
			ToCloseLoan toCloseLoan, String taskitem) {
		toCloseLoanService.saveToCloseLoan(toCloseLoan);
		return "task/task" + taskitem;
	}

	@RequestMapping(value = "submitCloseLoan")
	@ResponseBody
	public Result submitCloseLoan(HttpServletRequest request,
			ToCloseLoan toCloseLoan, String taskId, String processInstanceId,
			String taskitem) {
		toCloseLoanService.saveToCloseLoan(toCloseLoan);

		/* 流程引擎相关 */
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(toCloseLoan
				.getCaseCode());
		workFlowManager.submitTask(variables, taskId, processInstanceId,
				toCase.getLeadingProcessId(), toCloseLoan.getCaseCode());

		/**
		 * 功能: 给客户发送短信 作者：zhangxb16
		 */
		Result rs = new Result();
		try {
			int result = tgGuestInfoService.sendMsgHistory(
					toCloseLoan.getCaseCode(), taskitem);
			if (result > 0) {
			} else {
				rs.setMessage("短信发送失败, 请您线下手工再次发送！");
			}
		} catch (BusinessException ex) {
			ex.getMessage();
		}

		return rs;
	}

}
