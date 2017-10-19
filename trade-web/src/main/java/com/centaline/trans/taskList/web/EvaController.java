package com.centaline.trans.taskList.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.enums.EvalStatusEnum;
import com.centaline.trans.common.enums.LampEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.evaPricing.service.EvaPricingService;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.service.ToEvalReportProcessService;

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
	private ToCaseService toCaseService;
	@Autowired
	private EvaPricingService evaPricingService;
	@Autowired
	private ToEvalReportProcessService toEvalReportProcessService;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private UamSessionService  uamSessionService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private TaskService taskService;
	
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
	public String apply(HttpServletRequest request, HttpServletResponse response, String caseCode){
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		ToEvaPricingVo toEvaPricingVo = evaPricingService.findEvaPricingDetailByCaseCode(caseCode);//查询询价信息
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
		request.setAttribute("toEvalReportProcess", toEvalReportProcess);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toEvaPricingVo", toEvaPricingVo);
		request.setAttribute("caseCode", caseCode);
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
	public Boolean submitEvalApply(HttpServletRequest request,HttpServletResponse response,ToEvalReportProcess toEvalReportProcess){
		
		//保存申请信息
		toEvalReportProcessService.insertEvaApply(toEvalReportProcess);
		//启动流程引擎
		ProcessInstance process = new ProcessInstance();
		process.setBusinessKey(toEvalReportProcess.getEvaCode());
    	process.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_PROCESS.getCode()));
		//流程引擎相关
    	//Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.EVAL_PROCESS.getCode());
    	SessionUser user = uamSessionService.getSessionUser();
    	StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_PROCESS.getCode()), 
    			toEvalReportProcess.getEvaCode());
    	
    	//入交易系统工作流表
    	ToWorkFlow toWorkFlow = new ToWorkFlow();
    	toWorkFlow.setInstCode(processInstance.getId());
    	toWorkFlow.setBusinessKey(WorkFlowEnum.EVAL_PROCESS.getCode());
    	toWorkFlow.setProcessDefinitionId(processInstance.getProcessDefinitionId());
    	toWorkFlow.setProcessOwner(user.getId());
    	toWorkFlow.setCaseCode(toEvalReportProcess.getCaseCode());
    	toWorkFlow.setBizCode(toEvalReportProcess.getEvaCode());
    	toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
    	toWorkFlowService.insertSelective(toWorkFlow);
    	
    	Map<String, Object> defValsMap = new HashMap<String,Object>();
    	defValsMap.put("assistant", user.getUsername());
    	TaskVo taskvo = (TaskVo) taskService.listTasks(processInstance.getId()).getData().get(0);
    	taskService.submitTask(String.valueOf(taskvo.getId()),defValsMap);
    	return true;
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
		String caseCode = toEvalReportProcessService.findToEvalReportProcessByEvalCode(businessKey).getCaseCode();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//查询评估申请信息
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.selecttoEvalReportProcessByCaseCodeAndStatus(caseCode,EvalStatusEnum.YSQ.getCode());
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toEvalReportProcess", toEvalReportProcess);
		request.setAttribute("caseCode", caseCode);
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
	public Boolean submitReport(HttpServletRequest request, HttpServletResponse response, ToEvalReportProcess toEvalReportProcess,String taskId){
		//评估上报保存
		toEvalReportProcess.setStatus(EvalStatusEnum.YSB.getCode());
		toEvalReportProcessService.updateEvaReport(toEvalReportProcess);
		//提交任务
		Map<String, Object> variables = new HashMap<String, Object>();
		SessionUser user = uamSessionService.getSessionUser();
		variables.put("assistant", user.getUsername());
		taskService.submitTask(taskId,variables);
		return true;
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
		String caseCode = toEvalReportProcessService.findToEvalReportProcessByEvalCode(businessKey).getCaseCode();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//查询评估申请信息
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.selecttoEvalReportProcessByCaseCodeAndStatus(caseCode,EvalStatusEnum.YSB.getCode());
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toEvalReportProcess", toEvalReportProcess);
		request.setAttribute("caseCode", caseCode);
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
	public Boolean submitIssue(HttpServletRequest request, HttpServletResponse response, ToEvalReportProcess toEvalReportProcess,String taskId){
		//评估出具信息保存
		toEvalReportProcess.setStatus(EvalStatusEnum.YCNBG.getCode());
		toEvalReportProcessService.updateEvaReport(toEvalReportProcess);
		//提交任务
		SessionUser user = uamSessionService.getSessionUser();
		//提交任务
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("assistant", user.getUsername());
		taskService.submitTask(taskId,variables);
	    return true;
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
		String caseCode = toEvalReportProcessService.findToEvalReportProcessByEvalCode(businessKey).getCaseCode();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//查询评估申请
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.selecttoEvalReportProcessByCaseCodeAndStatus(caseCode,EvalStatusEnum.YCNBG.getCode());
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toEvalReportProcess", toEvalReportProcess);
		request.setAttribute("caseCode", caseCode);
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
	public Boolean submitUsed(HttpServletRequest request, HttpServletResponse response,ToEvalReportProcess toEvalReportProcess,String taskId){
		
        //评估使用信息保存
		toEvalReportProcess.setStatus(EvalStatusEnum.YSYBG.getCode());
		toEvalReportProcessService.updateEvaReport(toEvalReportProcess);
		taskService.submitTask(taskId,null);
	    return true;
	}
}
