package com.centaline.trans.taskList.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.MortgageSelectService;
import com.centaline.trans.task.service.ToMortgageTosaveService;
import com.centaline.trans.task.vo.MortgageToSaveVO;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.utils.UiImproveUtil;

@Controller
@RequestMapping(value = "/task/mortgageTosave")
public class MortgageTosaveController {
	
	@Inject
	private UamSessionService uamSessionService;
	@Inject
	private MortgageSelectService mortgageSelectService;
	@Inject
	private TransplanServiceFacade transplanServiceFacade;
	@Inject
	private ToCaseService toCaseService;
	@Inject
	private WorkFlowManager workFlowManager;
	@Inject
	private ToWorkFlowService toWorkFlowService;
	@Inject
	private ToMortgageTosaveService toMortgageTosaveService;
	
	
	@RequestMapping(value = "process")
	public String toProcess(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//税费卡
		int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
		if ( cou >0) {
			caseBaseVO.setLoanType("30004005");
		}
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		ToTransPlan plan=new ToTransPlan();
		plan.setCaseCode(caseCode);
		return "task" + UiImproveUtil.getPageType(request) + "/taskMortgageTosave";

	}
	@RequestMapping(value = "submit")
	@ResponseBody
	public boolean submit(MortgageToSaveVO mortgageToSaveVO){
		SessionUser u = uamSessionService.getSessionUser();
		mortgageToSaveVO.setPartner(u.getId());
		 int count = toMortgageTosaveService.saveMortgageTosave(mortgageToSaveVO);
		 boolean b = false;
		 if( count > 0){
			  b = toMortgageTosaveService.submit(mortgageToSaveVO);
		 }
		 return b;
	}
	
	
	

}
