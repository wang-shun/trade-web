package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.mortgage.service.ToEguPricingService;
import com.centaline.trans.mortgage.service.ToEvaReportService;
import com.centaline.trans.remote.vo.MortgageAttamentVo;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Controller
@RequestMapping(value="/task")
public class ToEvaReportController {
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private ToEvaReportService toEvaReportService;
	
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private ToEguPricingService toEguPricingService;
	@RequestMapping("evaReportArise/process")
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source){
		toAccesoryListService.getAccesoryList(request, "evaReportArise");
		request.setAttribute("toEguPricing", toEguPricingService.findIsFinalEguPricing(caseCode));	
		return "task/taskEvaReportArise";
	}
	/**
	 * 非egu的报告走线下流程
	 * @param toEvaReport
	 * @param processInstanceVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/submitEvaReport")  
	@ResponseBody
    public AjaxResponse<String> submitEvaReport(ToEvaReport toEvaReport,ProcessInstanceVO processInstanceVO,MortgageAttamentVo mortgageAttament,HttpServletRequest request) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			
			toEvaReportService.submitEvaReport(toEvaReport, processInstanceVO, mortgageAttament);
			
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
        return response;
    }
	
	/**
	 * 完成报告单任务
	 * @param processInstanceVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/completeEvaReport")  
	@ResponseBody
    public AjaxResponse<String> submitEvaReport(ProcessInstanceVO processInstanceVO,HttpServletRequest request) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			List<RestVariable> variables = new ArrayList<RestVariable>();
			
			ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
			workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
					toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());			
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("操作失败！"+e.getMessage());
		}
        return response;
    }
	
	/**
	 * 确认报告单
	 * @param pkid
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/confirmReport")  
	@ResponseBody
    public AjaxResponse<String> confirmReport(Long pkid,HttpServletRequest request) {

		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			
			ToEvaReport toEvaReport = toEvaReportService.findToEvaReportById(pkid);
			toEvaReport.setIsFinalReport("1");
			
			toEvaReportService.saveToEvaReport(toEvaReport);	
			
		}catch(Exception e){
			response.setSuccess(false);
			response.setMessage("确认报告单失败！"+e.getMessage());
		}
        return response;
    }
}
