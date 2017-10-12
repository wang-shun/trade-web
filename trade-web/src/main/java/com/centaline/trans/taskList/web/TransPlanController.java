package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.transplan.vo.TransPlanVO;
import com.centaline.trans.utils.UiImproveUtil;

@Controller
@RequestMapping(value = "/task/transPlan")
public class TransPlanController {

	@Autowired
	private TransplanServiceFacade transplanServiceFacade;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private ToCaseInfoService toCaseInfoService;

	@RequestMapping("process")
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source,
			String taskitem, String instCode) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
		if (cou > 0) {
			caseBaseVO.setLoanType("30004005");
		}
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		/*获取案件信息，用以判断贷款类型*/
		ToCaseInfo toCaseInfo=toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
		if(null!=toCaseInfo){
			request.setAttribute("toCaseInfo",toCaseInfo);
		}
		request.setAttribute("transPlan",
				transplanServiceFacade.findTransPlanByCaseCode(caseCode));
		return "task" + UiImproveUtil.getPageType(request)
				+ "/taskTransPlanFilling";
	}

	@RequestMapping(value = "saveTransPlan")
	@ResponseBody
	public Boolean saveTransPlan(HttpServletRequest request,
			TransPlanVO transPlanVO) {
		transplanServiceFacade.saveToTransPlan(transPlanVO);

		return true;
	}

	@RequestMapping(value = "submitTransPlan")
	@ResponseBody
	public boolean submitTransPlan(HttpServletRequest request,
			TransPlanVO transPlanVO) {
		transplanServiceFacade.saveToTransPlan(transPlanVO);

		/* 流程引擎相关 */
		List<RestVariable> variables = new ArrayList<RestVariable>();

		ToCase toCase = toCaseService.findToCaseByCaseCode(transPlanVO
				.getCaseCode());

		return workFlowManager.submitTask(variables, transPlanVO.getTaskId(),
				transPlanVO.getProcessInstanceId(),
				toCase.getLeadingProcessId(), transPlanVO.getCaseCode());
	}

	/**
	 * 页面初始化
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "showTransPlanHistory")
	public String showTransPlanHistory(String caseCode, ServletRequest request) {
		request.setAttribute("caseCode", caseCode);
		return "trans/trans_history_list";
	}
}
