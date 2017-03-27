package com.centaline.trans.mortgage.web;


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
	 * @date:20170324
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

}
