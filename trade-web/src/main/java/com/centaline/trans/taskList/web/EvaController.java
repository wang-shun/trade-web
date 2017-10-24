package com.centaline.trans.taskList.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseParticipantService;
import com.centaline.trans.common.enums.LampEnum;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.service.EvaProcessService;
import com.centaline.trans.eval.service.ToEvalRebateService;

/**
 * @Description:天津评估流程
 * @author：jinwl6
 * @date:2017年9月6日
 * @version:
 */
@Controller
@RequestMapping(value="task/eval")
public class EvaController {
	@Autowired
	private UamSessionService  uamSessionService;
	@Autowired
	ToCaseParticipantService toCaseParticipantService;
	@Autowired
	ToEvalRebateService toEvalRebateService; //评估返利
	@Autowired
	EvaProcessService evaProcessService;
	
	/**
	 * 评估代办任务
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "evalTaskList")
	public String evalTaskList(HttpServletRequest request, HttpServletResponse response){
		SessionUser user = uamSessionService.getSessionUser();
		String[] lamps = LampEnum.getCodes();
		request.setAttribute("userId", user.getId());
		request.setAttribute("Lamp1", lamps[0]);
		request.setAttribute("Lamp2", lamps[1]);
		request.setAttribute("Lamp3", lamps[2]);
		//request.setAttribute("processDfId", propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_PROCESS.getCode()));
		return "task/evalTaskList";
	}
	
	
	/**
	 * 评估申请
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value = "apply")
	public String apply(HttpServletRequest request, HttpServletResponse response, String caseCode,String evaCode,String source,
			String taskitem,String businessKey, String processInstanceId){
		evaProcessService.initApply(request, caseCode, evaCode, taskitem, businessKey);
		return "eval/evalApply";
	}
	
	/**
	 * 评估申请提交
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value="submitApply")
	@ResponseBody
	public AjaxResponse<?> submitEvalApply(HttpServletRequest request,HttpServletResponse response,ToEvalReportProcess toEvalReportProcess){
    	return evaProcessService.submitEvalApply(toEvalReportProcess);
	}
	
	/**
	 * 评估上报初始化
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value = "report")
	public String report(HttpServletRequest request, HttpServletResponse response,String source,
			String taskitem,String businessKey, String processInstanceId){
		evaProcessService.initReport(request,taskitem, businessKey);
	    return "eval/evalReport";
	}
	
	/**
	 * 评估上报提交
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value = "submitReport")
	@ResponseBody
	public  AjaxResponse<?> submitReport(HttpServletRequest request, HttpServletResponse response, ToEvalReportProcess toEvalReportProcess,String taskId){
		return evaProcessService.submitReport(toEvalReportProcess, taskId);
	}
	
	/**
	 * 出具评估报告初始化
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value = "issue")
	public String issue(HttpServletRequest request, HttpServletResponse response, String businessKey, String source,
			String taskitem, String processInstanceId){

		evaProcessService.initIssue(request, taskitem, businessKey);
	    return "eval/evalIssue";
	}
	
	/**
	 * 出具评估报告提交
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value = "submitIssue")
	@ResponseBody
	public AjaxResponse<?> submitIssue(HttpServletRequest request, HttpServletResponse response, ToEvalReportProcess toEvalReportProcess,String taskId){
	    return evaProcessService.submitIssue(toEvalReportProcess, taskId);
	}
	
	/**
	 * 使用评估报告初始化
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value = "used")
	public String used(HttpServletRequest request, HttpServletResponse response, String businessKey,String source,
			String taskitem, String processInstanceId){
		evaProcessService.initUsed(request, taskitem, businessKey);
	    return "eval/evalUsed";
	}
	
	/**
	 * 使用评估报告提交
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value = "submitUsed")
	@ResponseBody
	public AjaxResponse<?> submitUsed(HttpServletRequest request, HttpServletResponse response,ToEvalReportProcess toEvalReportProcess,String taskId){
	    return evaProcessService.submitUsed(toEvalReportProcess, taskId);
	}
}
