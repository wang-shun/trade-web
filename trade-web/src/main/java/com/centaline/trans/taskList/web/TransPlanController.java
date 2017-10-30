package com.centaline.trans.taskList.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.common.enums.ToApproveRecordEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ToApproveRecordMapper;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.entity.TsTransPlanHistory;
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

/**
 * @author wbzhouht
 */
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
	@Autowired(required =true)
	private UamSessionService uamSessionService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private LoanlostApproveService loanlostApproveService;
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
		/*获取案件信息，用以判断贷款类型 by wbzhouht*/
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
	public Boolean submitTransPlan(HttpServletRequest request,
			TransPlanVO transPlanVO) {
		Boolean boo=false;
		try {
			boo = transplanServiceFacade.submitTransPlan(request, transPlanVO);
		}catch (Exception e){
			e.printStackTrace();
		}
		return boo;
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

	/**
	 * 交易计划变更审核页面
	 * @author wbzhouht
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "detail")
	public String tranPlanAppver(String caseCode, ServletRequest request) {
		request.setAttribute("caseCode", caseCode);
		return "trans/trans_plan_appver";
	}
	@RequestMapping(value = "submitTransAppver")
	@ResponseBody
	public AjaxResponse<?> submitTranPlanAppver(String[]pkids, String[] newDates, String[] partCodes,Boolean audit,TransPlanVO transPlanVO){
			return transplanServiceFacade.submitTranPlanAppver(pkids,newDates,partCodes,audit,transPlanVO);
	}
}
