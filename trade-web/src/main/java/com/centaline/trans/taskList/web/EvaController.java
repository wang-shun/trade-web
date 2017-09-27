package com.centaline.trans.taskList.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.evaPricing.service.EvaPricingService;
import com.centaline.trans.eval.entity.ToEvaReportProcess;
import com.centaline.trans.eval.service.ToEvaReportProcessService;

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
	private ToEvaReportProcessService toEvaReportProcessService;
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
	
	@RequestMapping(value = "evalTaskList")
	public String evalTaskList(HttpServletRequest request, HttpServletResponse response){
		SessionUser user = uamSessionService.getSessionUser();
		String[] lamps = LampEnum.getCodes();
		request.setAttribute("userId", user.getId());
		request.setAttribute("Lamp1", lamps[0]);
		request.setAttribute("Lamp2", lamps[1]);
		request.setAttribute("Lamp3", lamps[2]);
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
		ToEvaPricingVo toEvaPricingVo = evaPricingService.findEvaPricingDetailByCaseCode(caseCode);
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
	public String submitEvalApply(HttpServletRequest request,HttpServletResponse response,ToEvaReportProcess toEvaReportProcess){
		
		//保存申请信息
		toEvaReportProcessService.insertEvaApply(toEvaReportProcess);
		//启动流程引擎
		ProcessInstance process = new ProcessInstance();
		process.setBusinessKey(toEvaReportProcess.getCaseCode());
    	process.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_PROCESS.getCode()));
		//流程引擎相关
    	//Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.EVAL_PROCESS.getCode());
    	Map<String, Object> defValsMap = new HashMap<String,Object>();
    	SessionUser user = uamSessionService.getSessionUser();
    	defValsMap.put("assistant", user.getUsername());
    	StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_PROCESS.getCode()), 
    			toEvaReportProcess.getCaseCode(), defValsMap);
    	
    	//入交易系统工作流表
    	ToWorkFlow toWorkFlow = new ToWorkFlow();
    	toWorkFlow.setInstCode(processInstance.getId());
    	toWorkFlow.setBusinessKey(WorkFlowEnum.EVAL_PROCESS.getCode());
    	toWorkFlow.setProcessDefinitionId(processInstance.getProcessDefinitionId());
    	toWorkFlow.setProcessOwner(user.getId());
    	toWorkFlow.setCaseCode(toEvaReportProcess.getCaseCode());
    	toWorkFlow.setBizCode(toEvaReportProcess.getCaseCode());
    	toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
    	toWorkFlowService.insertSelective(toWorkFlow);
    	return null;
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
	public String report(HttpServletRequest request, HttpServletResponse response,String caseCode,String source,
			String taskitem, String processInstanceId){
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//查询评估申请信息
		ToEvaReportProcess toEvaReportProcess = toEvaReportProcessService.selectToEvaReportProcessByCaseCode(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toEvaReportProcess", toEvaReportProcess);
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
	public String submitReport(HttpServletRequest request, HttpServletResponse response, ToEvaReportProcess toEvaReportProcess,String taskId){
		//评估上报保存
		toEvaReportProcess.setStatus(EvalStatusEnum.YSB.getCode());
		toEvaReportProcessService.updateEvaReport(toEvaReportProcess);
		//提交任务
		Map<String, Object> variables = new HashMap<String, Object>();
		SessionUser user = uamSessionService.getSessionUser();
		variables.put("assistant", user.getUsername());
		taskService.submitTask(taskId,variables);
	    return null;
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
	public String issue(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId){
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//查询评估申请信息
		ToEvaReportProcess toEvaReportProcess = toEvaReportProcessService.selectToEvaReportProcessByCaseCode(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toEvaReportProcess", toEvaReportProcess);
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
	public String submitIssue(HttpServletRequest request, HttpServletResponse response, ToEvaReportProcess toEvaReportProcess,String taskId){
		//评估出具信息保存
		toEvaReportProcess.setStatus(EvalStatusEnum.YCNBG.getCode());
		toEvaReportProcessService.updateEvaReport(toEvaReportProcess);
		//提交任务
		SessionUser user = uamSessionService.getSessionUser();
		//提交任务
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("assistant", user.getUsername());
		taskService.submitTask(taskId,variables);
	    return null;
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
	public String used(HttpServletRequest request, HttpServletResponse response, String caseCode,String source,
			String taskitem, String processInstanceId){
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//查询评估申请
		ToEvaReportProcess toEvaReportProcess = toEvaReportProcessService.selectToEvaReportProcessByCaseCode(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("toEvaReportProcess", toEvaReportProcess);
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
	public String submitUsed(HttpServletRequest request, HttpServletResponse response,ToEvaReportProcess toEvaReportProcess,String taskId){
		
        //评估使用信息保存
		toEvaReportProcess.setStatus(EvalStatusEnum.YSYBG.getCode());
		toEvaReportProcessService.updateEvaReport(toEvaReportProcess);
		taskService.submitTask(taskId,null);
	    return null;
	}
}
