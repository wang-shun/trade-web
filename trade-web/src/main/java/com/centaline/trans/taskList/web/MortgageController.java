package com.centaline.trans.taskList.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
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
	public boolean submitPsfApply(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime,
			String taskId, String processInstanceId) {
		ToTransPlan toTransPlan = new ToTransPlan();
		toTransPlan.setCaseCode(toMortgage.getCaseCode());

		// 修改人：zhangxb16 时间：2015-11-12
		toTransPlan.setPartCode("PSFApply");
		toTransPlan.setEstPartTime(estPartTime);
		toMortgage.setIsDelegateYucui("1");
		transplanServiceFacade.updateTransPlan(toTransPlan);
		toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount()!=null?toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)):null);
		toMortgage.setIsMainLoanBank("1");
		
		SessionUser user = uamSessionService.getSessionUser();
		toMortgage.setLoanAgent(user.getId());
		toMortgage.setLoanAgentTeam(user.getServiceDepId());
		toMortgageService.saveToMortgage(toMortgage);
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());	
		return workFlowManager.submitTask(variables, taskId, processInstanceId, 
				toCase.getLeadingProcessId(), toMortgage.getCaseCode());
	}
	
	@RequestMapping(value="mortgage/submitLoanRelease")
	@ResponseBody
	public Result submitLoanRelease(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime,
			String taskId, String processInstanceId, String partCode) {
		
		toMortgage.setIsMainLoanBank("1");
		ToMortgage mortage=toMortgageService.findToMortgageById(toMortgage.getPkid());
		mortage.setLendDate(toMortgage.getLendDate());
		mortage.setTazhengArrDate(toMortgage.getTazhengArrDate());
		mortage.setRemark(toMortgage.getRemark());
		toMortgageService.saveToMortgage(mortage);
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());	
		workFlowManager.submitTask(variables, taskId, processInstanceId, 
				toCase.getLeadingProcessId(), toMortgage.getCaseCode());
		
		/**
		 * 功能: 给客户发送短信
		 * 作者：zhangxb16
		 */
		Result rs=new Result();
		try{
			int result=tgGuestInfoService.sendMsgHistory(toMortgage.getCaseCode(), partCode);
			if(result>0){
			}else{
				rs.setMessage("短信发送失败, 请您线下手工再次发送！");
			}
		}catch(BusinessException ex){
			ex.getMessage();
		}
		
		return rs;
	}
	
	@RequestMapping(value="mortgage/submitSelfLoanApprove")
	@ResponseBody
	public Boolean submitSelfLoanApprove(HttpServletRequest request, ToMortgage toMortgage,
			String taskId, String processInstanceId) {
		if(toMortgage.getMortTotalAmount()!=null){
			toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)));
		}
		if(toMortgage.getComAmount()!=null){
			toMortgage.setComAmount(toMortgage.getComAmount().multiply(new BigDecimal(10000)));
		}
		if(toMortgage.getPrfAmount()!=null){
			toMortgage.setPrfAmount(toMortgage.getPrfAmount().multiply(new BigDecimal(10000)));
		}
		toMortgage.setIsMainLoanBank("1");
		toMortgageService.saveToMortgage(toMortgage);
		
		// 发送消息
		ToWorkFlow wf=new ToWorkFlow();
		wf.setCaseCode(toMortgage.getCaseCode());
		wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if(wordkFlowDB!=null && "operation_process:40:645454".compareTo(wordkFlowDB.getProcessDefinitionId())<=0) {
			messageService.sendMortgageFinishMsgByIntermi(wordkFlowDB.getInstCode());
			//设置主流程任务的assignee
			ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());
			workFlowManager.setAssginee(wordkFlowDB.getInstCode(), toCase.getLeadingProcessId(), wordkFlowDB.getCaseCode());
			
			// 结束当前流程
			ToWorkFlow workFlowOld =new ToWorkFlow();
			// 流程结束状态
			workFlowOld.setStatus("4");
			workFlowOld.setInstCode(processInstanceId);
			toWorkFlowService.updateWorkFlowByInstCode(workFlowOld);
		}

		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());	
		return workFlowManager.submitTask(variables, taskId, processInstanceId, 
				toCase.getLeadingProcessId(), toMortgage.getCaseCode());
	}
	
	@RequestMapping(value="mortgage/submitLoanlostApply")
	@ResponseBody
	public Result submitLoanlostApply(HttpServletRequest request, ToMortgage toMortgage, 
			ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO, String partCode,Long lapPkid) {
		if(toMortgage.getMortTotalAmount()!=null){
			toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)));
		}
		toMortgage.setIsMainLoanBank("1");
		
		SessionUser user = uamSessionService.getSessionUser();
		toMortgage.setLoanAgent(user.getId());
		toMortgage.setLoanAgentTeam(user.getServiceDepId());
		toMortgageService.saveToMortgage(toMortgage);
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		
		ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());	
		workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), toMortgage.getCaseCode());
		
		/**
		 * 功能: 给客户发送短信
		 * 作者：zhangxb16
		 */
		Result rs=new Result();
		try{
			int result=tgGuestInfoService.sendMsgHistory(toMortgage.getCaseCode(), partCode);
			if(result>0){
			}else{
				rs.setMessage("短信发送失败, 请您线下手工再次发送！");
			}
		}catch(BusinessException ex){
			ex.getMessage();
		}

		return rs;
	}
	
}
