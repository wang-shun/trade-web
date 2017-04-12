package com.centaline.trans.taskList.web;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.LoanerProcessService;

@Controller
@RequestMapping(value = "/task")
public class LoanerProcessController{

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
	 * @date:2017-03-24
	 * @des:信贷员接单流程启动
	 * */
	@RequestMapping("oldSendOrderStart")
	@ResponseBody
	public AjaxResponse<String> oldSendOrderStart(String caseCode,String loanerUserId, String loanerOrgId, String bankOrgCode,int bankLevel,String isMainLoanBank) {	
		
		AjaxResponse<String> response = new AjaxResponse<String>();
		if((null == caseCode  || "".equals(caseCode)) || (null == loanerUserId  || "".equals(loanerUserId)) || (null == loanerOrgId  || "".equals(loanerOrgId))  || (null == bankOrgCode  || "".equals(bankOrgCode) || (null == isMainLoanBank  || "".equals(isMainLoanBank)))){
			throw new BusinessException("信贷员流程启动请求参数为空！");
		}
		
		try{
			response = loanerProcessService.startLoanerOrderWorkFlow(caseCode,loanerUserId,loanerOrgId,bankOrgCode,bankLevel,isMainLoanBank);
		}catch(BusinessException e){
			throw new BusinessException("信贷员流程启动异常！");
		}	
	
		return response;	
	}
	
	/*
	 * @author:zhuody
	 * @date:2017-04-12
	 * @des:信贷员接单流程启动  时  保存贷款信息
	 * */
	@RequestMapping("sendOrderStart")
	@ResponseBody
	public AjaxResponse<String> sendOrderStart(HttpServletRequest request, ToMortgage toMortgage) {	
		
		AjaxResponse<String> response = new AjaxResponse<String>();
		if(null == toMortgage){
			throw new BusinessException("信贷员流程启动请求参数为空！");
		}
		
		try{
			response = loanerProcessService.newStartLoanerOrderWorkFlow(toMortgage);
		}catch(BusinessException e){
			throw new BusinessException("信贷员流程启动异常！");
		}	
	
		return response;	
	}
	
	
	/*
	 * @author:zhuody
	 * @date:2017-03-24
	 * @des:信贷员接单流程启动判断
	 * */
	@RequestMapping("isLoanerProcessStart")
	@ResponseBody
	public AjaxResponse<String> isLoanerProcessStart(String caseCode) {	
		
		AjaxResponse<String> response = new AjaxResponse<String>();
		if(null == caseCode  || "".equals(caseCode)){
			throw new BusinessException("判断流程是否启动请求参数为空！");
		}
		
		try{
			response = loanerProcessService.isLoanerProcessStart(caseCode);
		}catch(BusinessException e){
			throw new BusinessException("判断流程是否启动程序异常！");
		}	
	
		return response;	
	}
	
	/*
	 * @author:zhuody
	 * @date:2017-03-27
	 * @des:信贷员接单流程驳回
	 * */
	@RequestMapping(value = "comLoanerChange/process")
	public String comLoanerChangeProcess(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {

		SessionUser user = uamSessionService.getSessionUser();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
		//根据caseCode去查询相关页面信息，并且设置 页面的流程变量
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toMortgage", toMortgage);
		//判断案件是否有效
		if (toMortgage != null) {
			TgGuestInfo guest = tgGuestInfoService.selectByPrimaryKey(Long.parseLong(toMortgage.getCustCode()));
			if (null != guest) {
				request.setAttribute("custCompany", guest.getWorkUnit());
				request.setAttribute("custName", guest.getGuestName());
			}
		}		
		
		return "task/taskComLoanerChangeProcess";
	}	
	
	/*
	 * @author:zhuody
	 * @date:2017-03-28
	 * @des:信贷员流程 结束
	 * */
	@RequestMapping(value = "loanerProcessSubmit")
	@ResponseBody
	public AjaxResponse loanerProcessSubmit(HttpServletRequest request, HttpServletResponse response, ToMortgage toMortgage,String caseCode,String taskId, String processInstanceId,int bankLevel) {
		try{
			loanerProcessService.loanerProcessSubmit(toMortgage, caseCode, taskId, processInstanceId, bankLevel);
			return AjaxResponse.success();
		}catch (BusinessException e){
			logger.error(e.getMessage(),e);
			return AjaxResponse.fail(e.getMessage());
		}

	}
	
	
	/*
	 * @author:zhuody
	 * @date:2017-03-28
	 * @des:信贷员流程 结束
	 * */
	@RequestMapping(value = "loanerProcessDelete")
	@ResponseBody
	public AjaxResponse<String> loanerProcessDelete(HttpServletRequest request, HttpServletResponse response, String caseCode,String taskId, String processInstanceId) {
		try{
			loanerProcessService.loanerProcessDelete(caseCode,taskId,processInstanceId);
			return AjaxResponse.success("交易顾问派单流程成功结束");
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			return AjaxResponse.fail(e.getMessage());
		}

	}
}
