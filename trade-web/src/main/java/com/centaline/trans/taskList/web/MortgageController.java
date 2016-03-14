package com.centaline.trans.taskList.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.entity.TsMsgSendHistory;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.ToTransPlanService;
import com.centaline.trans.task.service.TsMsgSendHistoryService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Controller
@RequestMapping(value="/task/mortgage")
public class MortgageController {

	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private ToTransPlanService toTransPlanService;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private ToApproveRecordService toApproveRecordService;
	
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	
	@Autowired
	private ToPropertyInfoService topropertyInfoService;
	
	@Autowired
    private UamBasedataService   uambasedataService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private TsMsgSendHistoryService tsmsgSendHistoryService;
	
	@RequestMapping(value="saveMortgage")
	@ResponseBody
	public AjaxResponse<String> saveMortgage(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		ToTransPlan toTransPlan = new ToTransPlan();
		toTransPlan.setCaseCode(toMortgage.getCaseCode());
		
		// 修改人：zhangxb16  时间：2015-11-12 
		// toTransPlan.setPartCode(taskitem);
		toTransPlan.setPartCode("PSFSign");
		if(estPartTime == null) {
			if(taskitem.equals("LoanRelease")) {
				estPartTime = estPartTime==null?toMortgage.getLendDate():estPartTime;
			} else if(taskitem.equals("PSFApprove")) {
				estPartTime = estPartTime==null?toMortgage.getApprDate():estPartTime;
			}
			
		}
		toTransPlan.setEstPartTime(estPartTime);
		toTransPlanService.updateTransPlan(toTransPlan);
		ToMortgage mortgage = toMortgageService.saveToMortgage(toMortgage);
		response.setContent(String.valueOf(mortgage.getPkid()));
		return response;
	}
	
	@RequestMapping(value="saveSelfLoanApprove")
	@ResponseBody
	public AjaxResponse<String> saveMortgage(HttpServletRequest request, ToMortgage toMortgage) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)));
		toMortgage.setComAmount(toMortgage.getComAmount() !=null?toMortgage.getComAmount().multiply(new BigDecimal(10000)):null);
		toMortgage.setPrfAmount(toMortgage.getPrfAmount()!=null?toMortgage.getPrfAmount().multiply(new BigDecimal(10000)):null);
		ToMortgage mortgage = toMortgageService.saveToMortgage(toMortgage);
		response.setContent(String.valueOf(mortgage.getPkid()));
		return response;
	}
	
	@RequestMapping(value="saveLoanlostApply")
	@ResponseBody
	public AjaxResponse<String> saveLoanlostApply(HttpServletRequest request, ToMortgage toMortgage) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)));
		ToMortgage mortgage = toMortgageService.saveToMortgage(toMortgage);
		response.setContent(String.valueOf(mortgage.getPkid()));
		return response;
	}
	
	@RequestMapping(value="submitPsfApply")
	@ResponseBody
	public boolean submitPsfApply(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime,
			String taskId, String processInstanceId) {
		ToTransPlan toTransPlan = new ToTransPlan();
		toTransPlan.setCaseCode(toMortgage.getCaseCode());
		
		// 修改人：zhangxb16  时间：2015-11-12 
		toTransPlan.setPartCode("PSFSign");
		// toTransPlan.setPartCode("PSFApply");
		toTransPlan.setEstPartTime(estPartTime);
		toMortgage.setIsDelegateYucui("1");
		toTransPlanService.updateTransPlan(toTransPlan);
		toMortgageService.saveToMortgage(toMortgage);
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());	
		return workFlowManager.submitTask(variables, taskId, processInstanceId, 
				toCase.getLeadingProcessId(), toMortgage.getCaseCode());
	}
	
	@RequestMapping(value="submitLoanRelease")
	@ResponseBody
	public Result submitLoanRelease(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime,
			String taskId, String processInstanceId, String partCode) {
		ToTransPlan toTransPlan = new ToTransPlan();
		toTransPlan.setCaseCode(toMortgage.getCaseCode());
		toTransPlan.setPartCode("LoanRelease");
		toTransPlan.setEstPartTime(estPartTime==null?toMortgage.getLendDate():estPartTime);
		toTransPlanService.updateTransPlan(toTransPlan);
		toMortgageService.saveToMortgage(toMortgage);
		
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
	
	@RequestMapping(value="submitSelfLoanApprove")
	@ResponseBody
	public Boolean submitSelfLoanApprove(HttpServletRequest request, ToMortgage toMortgage,
			String taskId, String processInstanceId) {
		toMortgageService.saveToMortgage(toMortgage);

		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());	
		return workFlowManager.submitTask(variables, taskId, processInstanceId, 
				toCase.getLeadingProcessId(), toMortgage.getCaseCode());
	}
	
	@RequestMapping(value="submitLoanlostApply")
	@ResponseBody
	public Result submitLoanlostApply(HttpServletRequest request, ToMortgage toMortgage, 
			ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO, String partCode) {
		toMortgageService.saveToMortgage(toMortgage);
		
		/*保存流失申请 审核记录*/
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setCaseCode(toMortgage.getCaseCode());
		toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
		toApproveRecord.setContent(loanlostApproveVO.getContent());
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setPartCode(processInstanceVO.getPartCode());
		toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
		toApproveRecordService.saveToApproveRecord(toApproveRecord);
		
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
