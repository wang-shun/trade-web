package com.centaline.trans.taskList.web;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.mortgage.service.ToMortgageService;
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
public class LoanerProcessController {
	
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
	 * 手机端实现
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
	 * 手机端实现
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

		SessionUser user = uamSessionService.getSessionUser();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
		//根据caseCode去查询相关页面信息，并且设置 页面的流程变量
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toMortgage", toMortgage);
		//判断案件是否有效
		if (toMortgage != null) {
			TgGuestInfo guest = tgGuestInfoService.selectByPrimaryKey(Long
					.parseLong(toMortgage.getCustCode()));
			if (null != guest) {
				request.setAttribute("custCompany", guest.getWorkUnit());
				request.setAttribute("custName", guest.getGuestName());
			}
		}
		//response = loanerProcessService.startLoanerOrderWorkFlow(caseCode,loanerUserId,loanerOrgId,bankOrgCode,bankLevel);
		
		return "task/taskComLoanerChangeProcess";
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
	
	
	/*
	 * @author:zhuody
	 * @date:2017-03-28
	 * @des:信贷员流程 结束
	 * */
	@RequestMapping(value = "loanerProcessSubmit")
	public AjaxResponse<String> loanerProcessSubmit(HttpServletRequest request, HttpServletResponse response, ToMortgage toMortgage,String caseCode,String taskitem, String processInstanceId,int bankLevel) {
		
		AjaxResponse<String>  responseStr = new AjaxResponse<String>();
		//根据caseCode去查询相关页面信息，并且设置 页面的流程变量
		try{
			responseStr = loanerProcessService.loanerProcessSubmit(toMortgage, caseCode, taskitem, processInstanceId, bankLevel);
		}catch(BusinessException e){
			throw new BusinessException("交易顾问派单流程删除异常！");
		}
		
		return responseStr;
	}
	
	
	/*
	 * @author:zhuody
	 * @date:2017-03-28
	 * @des:信贷员流程 结束
	 * */
	@RequestMapping(value = "loanerProcessDelete")
	public AjaxResponse<String> loanerProcessDelete(HttpServletRequest request, HttpServletResponse response, String caseCode,String taskitem, String processInstanceId) {
		
		AjaxResponse<String>  responseStr = new AjaxResponse<String>();
		//根据caseCode去查询相关页面信息，并且设置 页面的流程变量
		try{
			responseStr = loanerProcessService.loanerProcessDelete(caseCode,taskitem,processInstanceId);
		}catch(BusinessException e){
			throw new BusinessException("交易顾问派单流程删除异常！");
		}
		
		return responseStr;
	}
	
	
/*	
	
	@RequestMapping(value = "submit")
	@ResponseBody
	public boolean submit(HttpServletRequest request,FirstFollowVO firstFollowVO) {
		SessionUser user = uamSessionService.getSessionUser();
		firstFollowVO.setUserId(user.getId());
		firstFollowVO.setUserOrgId(getOrgId(user.getId()));
		firstFollowVO.setUserName(user.getUsername());
		
		firstFollowService.saveFirstFollow(firstFollowVO);

		 无效案件保存到审批记录表 
		if (firstFollowVO.getCaseProperty().equals("30003001")) {
			saveToApproveRecord(firstFollowVO, firstFollowVO.getOperator(),	firstFollowVO.getApproveType());
		} else {
			firstFollowVO = firstFollowService.switchWorkFlowWithCurrentVersion(firstFollowVO);
		}

		 流程引擎相关 
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable restVariable = new RestVariable();
		restVariable.setName("isvalid");
		restVariable.setValue(firstFollowVO.getCaseProperty().equals("30003001"));
		variables.add(restVariable);
		if (firstFollowVO.getCaseProperty().equals("30003001")) {
			if (!StringUtils.isBlank(firstFollowVO.getInvalid_reason())) {
				RestVariable restVariable6 = new RestVariable();
				restVariable6.setName("invalid_reason");
				restVariable6.setValue(firstFollowVO.getInvalid_reason());
				variables.add(restVariable6);
			}
		} else {
			RestVariable restVariable3 = new RestVariable(); 限购 
			restVariable3.setName("PurLimitCheckNeed");
			RestVariable restVariable4 = new RestVariable(); 抵押 
			restVariable4.setName("LoanCloseNeed");

			restVariable3
					.setValue(firstFollowVO.getChaxiangou().equals("true"));
			restVariable4.setValue(firstFollowVO.getDiya().equals("true"));

			variables.add(restVariable3);
			variables.add(restVariable4);

			// variables = editRestVariables(variables,
			// firstFollowVO.getMortageService());
		}
		RestVariable signAssignee = new RestVariable();
		signAssignee.setName("signAssignee");
		signAssignee.setValue(user.getUsername());
		variables.add(signAssignee);
		ToCase toCase = toCaseService.findToCaseByCaseCode(firstFollowVO
				.getCaseCode());
		return workFlowManager.submitTask(variables, firstFollowVO.getTaskId(),
				firstFollowVO.getProcessInstanceId(),
				toCase.getLeadingProcessId(), firstFollowVO.getCaseCode());
		// return false;
	}
	*/
	

}
