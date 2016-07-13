package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.ToTransPlanService;
import com.centaline.trans.task.vo.TransPlanVO;

@Controller
@RequestMapping(value="/task/transPlan")
public class TransPlanController {

	@Autowired
	private ToTransPlanService toTransPlanService;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@RequestMapping("process")
	public String toProcess(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String instCode) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);

		RestVariable dy = workFlowManager.getVar(instCode, "LoanCloseNeed");/*抵押*/
		
		RestVariable psf = workFlowManager.getVar(instCode, "PSFLoanNeed");/*公积金*/
		RestVariable self = workFlowManager.getVar(instCode, "SelfLoanNeed");/*自办*/
		RestVariable com = workFlowManager.getVar(instCode, "ComLoanNeed");/*贷款*/
		boolean dk =  ((boolean)(psf==null?false:psf.getValue())||(boolean)(self==null?false:self.getValue())||(boolean)(com==null?false:com.getValue()));
		request.setAttribute("dy", dy==null?false:dy.getValue());
		request.setAttribute("dk", dk);
		request.setAttribute("transPlan", toTransPlanService.findTransPlanByCaseCode(caseCode));
		return "task/taskTransPlanFilling";
	}
	@RequestMapping(value="saveTransPlan")
	@ResponseBody
	public Boolean saveTransPlan(HttpServletRequest request, TransPlanVO transPlanVO) {
		toTransPlanService.saveToTransPlan(transPlanVO);
		return true;
	}
	
	@RequestMapping(value="submitTransPlan")
	@ResponseBody
	public boolean submitTransPlan(HttpServletRequest request, TransPlanVO transPlanVO) {
		toTransPlanService.saveToTransPlan(transPlanVO);
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();

		ToCase toCase = toCaseService.findToCaseByCaseCode(transPlanVO.getCaseCode());	
		
		return workFlowManager.submitTask(variables, transPlanVO.getTaskId(), transPlanVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), transPlanVO.getCaseCode());
	}
	/**
	 * 页面初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="showTransPlanHistory")
	public String showTransPlanHistory(String caseCode, ServletRequest request){
		request.setAttribute("caseCode", caseCode);
		return "trans/trans_history_list";
	}
}
