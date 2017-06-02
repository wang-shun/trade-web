package com.centaline.trans.taskList.web;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.Result2;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.MortStep;
import com.centaline.trans.mortgage.entity.ToEguPricing;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.MortStepService;
import com.centaline.trans.mortgage.service.ToEguPricingService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;

@Controller
@RequestMapping(value="/task")
public class MortgageController {

	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private TransplanServiceFacade transplanServiceFacade;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private MortStepService mortStepService;
	
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	@Autowired
	private ToEguPricingService toEguPricingService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	MessageService messageService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private UamSessionService uamSessionService;
	
	@RequestMapping(value = "test")
	public String test(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		    workFlowManager.deleteProcess("648743");
			 
			ProcessInstance processIns = new ProcessInstance();
			processIns.setProcessDefinitionId("ComLoan_Process:4:645463");
			processIns.setBusinessKey("ZY-AJ-201512-1384");
		    workFlowManager.startWorkFlow(processIns);
		    
		    return "";
	}

	@RequestMapping(value = "comLoanProcess/process")
	public String toProcess(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {	
		
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
		if ( cou >0) {
			caseBaseVO.setLoanType("30004005");
		}
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);

		toAccesoryListService.getAccesoryLists(request, taskitem);
		MortStep mortStep = new MortStep();
		mortStep.setCaseCode(caseCode);
		Integer[] step = mortStepService.getMortStep(caseCode);
		request.setAttribute("step", step[0]);
		request.setAttribute("step1", step[1]);

		request.setAttribute("afterTimeFlag", false);
		if(caseBaseVO.getToCase()!=null&&caseBaseVO.getToCase().getCreateTime()!=null){
			request.setAttribute("afterTimeFlag", caseBaseVO.getToCase().getCreateTime().after(new Date(1467302399999l)));
		}

		ToEguPricing toEguPricing = toEguPricingService.findIsFinalEguPricing(caseCode);
		if(toEguPricing != null){
			request.setAttribute("isMainLoanBank", toEguPricing.getIsMainLoanBank());
			request.setAttribute("evaCode", toEguPricing.getEvaCode());
		}else{
			request.setAttribute("isMainLoanBank", "1");
			request.setAttribute("evaCode", "");
		}		
		return "task/taskComLoanProcess";
	}
	
	@RequestMapping(value="mortgage/saveMortgage")
	@ResponseBody
	public AjaxResponse<String> saveMortgage(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		ToTransPlan toTransPlan = new ToTransPlan();
		toTransPlan.setCaseCode(toMortgage.getCaseCode());
		
		// 修改人：zhangxb16  时间：2015-11-12 	
		toTransPlan.setPartCode("PSFApply");
		if (estPartTime == null) {
			if (taskitem.equals("LoanRelease")) {
				estPartTime = estPartTime == null ? toMortgage.getLendDate()
						: estPartTime;
			} else if (taskitem.equals("PSFApprove")) {
				estPartTime = estPartTime == null ? toMortgage.getApprDate()
						: estPartTime;
			}
			
		}
		toTransPlan.setEstPartTime(estPartTime);
		transplanServiceFacade.updateTransPlan(toTransPlan);
		toMortgage.setIsMainLoanBank("1");
		
		if(taskitem.equals("PSFApply")) {
			toMortgage.setIsDelegateYucui("1");
		}
		
		ToMortgage mortgage = toMortgageService.saveToMortgage(toMortgage);
		response.setContent(String.valueOf(mortgage.getPkid()));
		return response;
	}
	
	@RequestMapping(value="mortgage/saveSelfLoanApprove")
	@ResponseBody
	public AjaxResponse<String> saveMortgage(HttpServletRequest request, ToMortgage toMortgage) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount()!=null?toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)):null);
		toMortgage.setComAmount(toMortgage.getComAmount() !=null?toMortgage.getComAmount().multiply(new BigDecimal(10000)):null);
		toMortgage.setPrfAmount(toMortgage.getPrfAmount()!=null?toMortgage.getPrfAmount().multiply(new BigDecimal(10000)):null);
		toMortgage.setIsMainLoanBank("1");
		ToMortgage mortgage = toMortgageService.saveToMortgage(toMortgage);
		response.setContent(String.valueOf(mortgage.getPkid()));
		return response;
	}
	
	@RequestMapping(value="mortgage/saveLoanlostApply")
	@ResponseBody
	public AjaxResponse<String> saveLoanlostApply(HttpServletRequest request, ToMortgage toMortgage) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		if(toMortgage.getMortTotalAmount()!=null){
			toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)));
		}
		toMortgage.setIsMainLoanBank("1");
		
		ToMortgage mortgage = toMortgageService.saveToMortgage(toMortgage);
		response.setContent(String.valueOf(mortgage.getPkid()));
		return response;
	}
	
	@RequestMapping(value="mortgage/submitPsfApply")
	@ResponseBody
	public Boolean submitPsfApply(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime,
			String taskId, String processInstanceId) {
		return toMortgageService.submitPsfApply(request, toMortgage, taskitem, estPartTime, taskId, processInstanceId);
	}
	
	@RequestMapping(value="mortgage/submitLoanRelease")
	@ResponseBody
	public Result2 submitLoanRelease(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime,
			String taskId, String processInstanceId, String partCode) {
		return toMortgageService.submitLoanRelease(request, toMortgage, taskitem, estPartTime, taskId, processInstanceId, partCode);
	}
	
	@RequestMapping(value="mortgage/submitSelfLoanApprove")
	@ResponseBody
	public Boolean submitSelfLoanApprove(HttpServletRequest request, ToMortgage toMortgage,
			String taskId, String processInstanceId) {
		return toMortgageService.submitSelfLoanApprove(request, toMortgage, taskId, processInstanceId);
	}
	
	@RequestMapping(value="mortgage/submitLoanlostApply")
	@ResponseBody
	public Result2 submitLoanlostApply(HttpServletRequest request, ToMortgage toMortgage, 
			ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO, String partCode,Long lapPkid) {
		return toMortgageService.submitLoanlostApply(request, toMortgage, processInstanceVO, loanlostApproveVO, partCode, lapPkid);
	}
	
}
