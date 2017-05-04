package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.LoanerProcessService;
import com.centaline.trans.mortgage.service.ToMortgageService;

@Controller
@RequestMapping(value = "/task")
public class LoanerProcessController {

	private Logger logger = Logger.getLogger(LoanerProcessController.class);

	@Autowired(required = true)
	UamSessionService uamSessionService;

	@Autowired
	private ToCaseService toCaseService;

	@Autowired
	private ToMortgageService toMortgageService;

	@Autowired
	private TgGuestInfoService tgGuestInfoService;

	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Autowired(required = true)
	private LoanerProcessService loanerProcessService;

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-24
	 * 
	 * @des:信贷员接单流程启动
	 */
	@RequestMapping("oldSendOrderStart")
	@ResponseBody
	public AjaxResponse<String> oldSendOrderStart(String caseCode, String loanerUserId, String loanerOrgId, String bankOrgCode, int bankLevel, String isMainLoanBank) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		if ((null == caseCode || "".equals(caseCode)) || (null == loanerUserId || "".equals(loanerUserId)) || (null == loanerOrgId || "".equals(loanerOrgId)) || (null == bankOrgCode || "".equals(bankOrgCode) || (null == isMainLoanBank || "".equals(isMainLoanBank)))) {
			throw new BusinessException("信贷员流程启动请求参数为空！");
		}

		try {
			response = loanerProcessService.startLoanerOrderWorkFlow(caseCode, loanerUserId, loanerOrgId, bankOrgCode, bankLevel, isMainLoanBank);
		} catch (BusinessException e) {
			throw new BusinessException("信贷员流程启动异常！");
		}

		return response;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-12
	 * 
	 * @des:信贷员接单流程启动 时 保存贷款信息
	 */
	@RequestMapping("sendOrderStart")
	@ResponseBody
	public AjaxResponse<String> sendOrderStart(ToMortgage toMortgage) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		if (null == toMortgage) {
			throw new BusinessException("信贷员流程启动请求参数为空！");
		}

		try {
			response = loanerProcessService.newStartLoanerOrderWorkFlow(toMortgage);
		} catch (BusinessException e) {
			throw new BusinessException("信贷员流程启动异常！");
		}

		return response;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-24
	 * 
	 * @des:信贷员接单流程启动判断
	 */
	@RequestMapping("isLoanerProcessStart")
	@ResponseBody
	public AjaxResponse<String> isLoanerProcessStart(String caseCode, String isMainLoanBank) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		if ((null == caseCode || "".equals(caseCode)) || (null == isMainLoanBank || "".equals(isMainLoanBank))) {
			throw new BusinessException("判断流程是否启动请求参数为空！");
		}

		try {
			response = loanerProcessService.isLoanerProcessStart(caseCode, isMainLoanBank);
		} catch (BusinessException e) {
			throw new BusinessException("判断流程是否启动程序异常！");
		}

		return response;
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-27
	 * 
	 * @des:信贷员接单流程驳回
	 */
	@RequestMapping(value = "comLoanerChange/process")
	public String comLoanerChangeProcess(HttpServletRequest request, HttpServletResponse response, String caseCode, String source, String taskitem, String processInstanceId) {

		if ((null == caseCode || "".equals(caseCode)) || (null == processInstanceId || "".equals(processInstanceId))) {
			throw new BusinessException("重新派单流程请求参数异常！");
		}

		try {
			loanerProcessService.comLoanerChangeProcess(request, caseCode, taskitem, processInstanceId);
		} catch (BusinessException e) {
			throw new BusinessException("重新派单流程 查询信息异常");
		}

		return "task/taskComLoanerChangeProcess";
	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-28
	 * 
	 * @des:信贷员流程 结束
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "loanerProcessSubmit")
	@ResponseBody
	public AjaxResponse loanerProcessSubmit(HttpServletRequest request, HttpServletResponse response, ToMortgage toMortgage, String caseCode, String taskId, String processInstanceId, int bankLevel) {
		try {
			loanerProcessService.loanerProcessSubmit(toMortgage, caseCode, taskId, processInstanceId, bankLevel);
			return AjaxResponse.success();
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.fail(e.getMessage());
		}

	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-28
	 * 
	 * @des:信贷员流程 结束
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "loanerProcessDelete")
	@ResponseBody
	public AjaxResponse<String> loanerProcessDelete(HttpServletRequest request, HttpServletResponse response, String caseCode, String taskId, String processInstanceId, String isMainLoanBank) {
		try {
			loanerProcessService.loanerProcessDelete(caseCode, taskId, processInstanceId, isMainLoanBank);
			return AjaxResponse.success("取消交易顾问派单流程成功！");
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.fail(e.getMessage());
		}

	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-03-28
	 * 
	 * @des:信贷员流程 结束
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "loanerProcessCancle")
	@ResponseBody
	public AjaxResponse<String> loanerProcessCancle(HttpServletRequest request, HttpServletResponse response, String caseCode, String taskId, String processInstanceId, String isMainLoanBankProcess, String loanerPkid) {
		try {
			loanerProcessService.loanerProcessCancle(caseCode, taskId, processInstanceId, isMainLoanBankProcess, loanerPkid);
			return AjaxResponse.success("恭喜，取消派单流程成功！");
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.fail(e.getMessage());
		}

	}

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-04-14
	 * 
	 * @des:跳转到 信贷员派单列表
	 */
	@RequestMapping(value = "/loanerProcessList")
	public String loanerProcessList(HttpServletRequest request) {

		try {
			loanerProcessService.loanerProcessList(request);

		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
		}

		return "mortgage/loanerProcessList";
	}

}
