package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
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
