package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.SelfLoanApprService;
import com.centaline.trans.task.vo.MortgageSelecteVo;
import com.centaline.trans.task.vo.ToAppRecordInfoVO;

@Controller
@RequestMapping(value="/task")
public class SelfLoanApprController {

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private SelfLoanApprService selfLoanApprService;
	
	@RequestMapping("selfAssessLoanAppl/process")
	public String toProcess(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);	
		toAccesoryListService.getAccesoryList(request, taskitem);
		/*流程引擎相关*/
		return "task/taskSelfAssessLoanAppl";
	}
	
	@ResponseBody
	@RequestMapping(value = "selfAssessLoanAppl/submit")
	public boolean submit(ToAppRecordInfoVO vo){
		return selfLoanApprService.saveAndSubmit(vo);
		//List<RestVariable> variables = new ArrayList<RestVariable>();
		//boolean b = workFlowManager.submitTask(variables, vo.getTaskId(), vo.getProcessInstanceId(), null, vo.getCaseCode());
		//return b;
	}
	
	/**
	 * 事总审批
	 */
	@RequestMapping("generalAppro/process")
	public String toProcess1(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);	
		toAccesoryListService.getAccesoryList(request, taskitem);
		return "task/taskgeneralAppro";
	}
	/**
	 * 失踪提交
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "generalAppro/submit")
	public boolean submit1(MortgageSelecteVo vo){
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable restVariable = new RestVariable();
		restVariable.setName("approval");
		restVariable.setValue(true);
		variables.add(restVariable);
		boolean b = workFlowManager.submitTask(variables, vo.getTaskId(), vo.getProcessInstanceId(), null, vo.getCaseCode());
		return b;
	}
}
