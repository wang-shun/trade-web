package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.mortgage.entity.ToMortLoaner;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.LoanerProcessService;
import com.centaline.trans.mortgage.service.ToMortLoanerService;

@Controller
@RequestMapping(value = "/task")
public class LoanerProcessController {

	private Logger logger = Logger.getLogger(LoanerProcessController.class);

	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	private LoanerProcessService loanerProcessService;
	@Autowired
	private ToMortLoanerService toMortLoanerService;
	/**
	 * 加载派单信息(找最后一条的派单信息)
	 */
	@RequestMapping("/loadOrderInfo")
	@ResponseBody
	public AjaxResponse<ToMortLoaner>  loadOrderInfo(String caseCode,String isMainLoanBank){
		ToMortLoaner tml = toMortLoanerService.findLastToMortLoaner(caseCode, isMainLoanBank);
		if(tml==null){
			return new AjaxResponse<ToMortLoaner>(true, "加载成功",tml) ;
		} 
		String loanerOrgId = tml.getLoanerOrgId();
		if (!StringUtils.isEmpty(loanerOrgId)) {
		    Org org= uamUserOrgService.getOrgById(loanerOrgId);		
			if (org!=null) {
				tml.setLoanerOrgCodeStr(org.getOrgName());
			}
		}
		return new AjaxResponse<ToMortLoaner>(true, "加载成功",tml) ;
	}
	/**
	 * 只加载 待接单、 待审批 、完成 状态的派单信息
	 */
	@RequestMapping("/findActiveOrder")
	@ResponseBody
	public AjaxResponse<ToMortLoaner> findActiveOrder(String caseCode,String isMainLoanBank){
		return new AjaxResponse<ToMortLoaner>(true, "加载成功", toMortLoanerService.findActiveToMortLoaner(caseCode, isMainLoanBank));
	}
	
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
	public AjaxResponse<String> sendOrderStart(ToMortLoaner toMortgage) {
		try {
			loanerProcessService.startLoanerOrderWorkFlow(toMortgage);
		} catch (BusinessException e) {
			throw new BusinessException("信贷员流程启动异常！");
		}

		return AjaxResponse.success("派单成功");
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

		if ((null == caseCode || "".equals(caseCode)) || (null == isMainLoanBank || "".equals(isMainLoanBank))) {
			throw new BusinessException("判断流程是否启动请求参数为空！");
		}

		try {
			boolean isStarted=loanerProcessService.isLoanerProcessStart(caseCode, isMainLoanBank);
			return AjaxResponse.successContent(isStarted);
		} catch (BusinessException e) {
			throw new BusinessException("判断流程是否启动程序异常！");
		}
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
	public AjaxResponse<String> loanerProcessCancle(HttpServletRequest request, HttpServletResponse response, String caseCode,  String isMainLoanBankProcess) {
		try {
			loanerProcessService.loanerProcessCancle(caseCode, isMainLoanBankProcess);
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
