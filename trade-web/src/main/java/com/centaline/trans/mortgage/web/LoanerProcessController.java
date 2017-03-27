package com.centaline.trans.mortgage.web;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.mortgage.service.LoanerProcessService;

@Controller
@RequestMapping(value = "/loaner")
public class LoanerProcessController {
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired(required = true)
	private LoanerProcessService loanerProcessService;	
	
	/*
	 * @author:zhuody
	 * @date:2017-03-24
	 * @des:信贷员接单流程启动
	 * */
	@RequestMapping("sendOrderStart")
	@ResponseBody
	public AjaxResponse<String> sendOrderStart(String caseCode,String loanerUserId, String loanerOrgId, String bankOrgCode,int bankLevel) {	
		
		AjaxResponse<String> response = new AjaxResponse<String>();
		if((null == caseCode  || "".equals(caseCode)) || (null == loanerUserId  || "".equals(loanerUserId)) ||  (null == loanerOrgId  || "".equals(loanerOrgId))  || (null == bankOrgCode  || "".equals(bankOrgCode))){
			throw new BusinessException("信贷员流程启动请求参数为空！");
		}
		
		try{
			response = loanerProcessService.startLoanerOrderWorkFlow(caseCode,loanerUserId,loanerOrgId,bankOrgCode,bankLevel);
		}catch(BusinessException e){
			throw new BusinessException("信贷员流程启动异常！");
		}	
	
		return response;	
	}
	
	
	/*
	 * @author:zhuody
	 * @date:2017-03-27
	 * @des:信贷员接单流程启动
	 * */
	@RequestMapping("loanerApproveCase")
	@ResponseBody
	public AjaxResponse<String> loanerApproveCase(boolean isLonaerAcceptCase,String taskId, String processInstanceId, String caseCode) {	
		
		AjaxResponse<String> response = new AjaxResponse<String>();
		if((null == caseCode  || "".equals(caseCode)) || (null == taskId  || "".equals(taskId)) ||  (null == processInstanceId  || "".equals(processInstanceId))){
			throw new BusinessException("信贷员流程推进请求参数为空！");
		}
		
		try{
			response = loanerProcessService.isLoanerAcceptCase(true,taskId,processInstanceId,caseCode);
		}catch(BusinessException e){
			throw new BusinessException("信贷员流程推进异常！");
		}	
	
		return response;	
	}
	
	
	/*
	 * @author:zhuody
	 * @date:2017-03-27
	 * @des:信贷员接单流程启动
	 * */
	@RequestMapping("bankApproveCase")
	@ResponseBody
	public AjaxResponse<String> bankApproveCase(boolean isBankAcceptCase,String taskId, String processInstanceId, String caseCode) {	
		
		AjaxResponse<String> response = new AjaxResponse<String>();
		if((null == caseCode  || "".equals(caseCode)) || (null == taskId  || "".equals(taskId)) ||  (null == processInstanceId  || "".equals(processInstanceId))){
			throw new BusinessException("银行审核信贷员信息流程推进请求参数为空！");
		}
		
		try{
			response = loanerProcessService.isBankAcceptCase(true,taskId,processInstanceId,caseCode);
		}catch(BusinessException e){
			throw new BusinessException("银行审核信贷员信息流程异常！");
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
		
		
		//根据caseCode去查询相关页面信息，并且设置 页面的流程变量
		
		//判断案件是否有效

		//response = loanerProcessService.startLoanerOrderWorkFlow(caseCode,loanerUserId,loanerOrgId,bankOrgCode,bankLevel);
		
		return "task/taskComLoanerChangeProcess";
	}
	
	
	/*
	 * @author:zhuody
	 * @date:2017-03-27
	 * @des:信贷员接单流程页面
	 * */
	@RequestMapping(value = "loanerAcceptTest/process")
	public String loanerAcceptTest(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		
		

		return "task/taskLoanerAccept";
	}
	
	
	/*
	 * @author:zhuody
	 * @date:2017-03-27
	 * @des:银行接对接单流程审核
	 * */
	@RequestMapping(value = "bankAcceptTest/process")
	public String bankAcceptTest(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		
		
		//根据caseCode去查询相关页面信息，并且设置 页面的流程变量

		
		return "task/taskBankAccept";
	}
	
	
	

}
