package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.KnotCommissionVO;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.utils.UiImproveUtil;


/**
 * @author wbzhouht
 * 允许结佣
 */
@Controller
@RequestMapping(value="/task/KnotCommission")
public class KnotCommissionController {
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private FlowApiService flowApiService;

	@RequestMapping(value="process")
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId){
		System.out.println("================进来了！=================");
		//根据交易单编号，查询案件相关信息
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		//获取流程变量
		RestVariable psf = workFlowManager.getVar(processInstanceId,
				"PSFLoanNeed");
		RestVariable self = workFlowManager.getVar(processInstanceId,
				"SelfLoanNeed"); 
		RestVariable com = workFlowManager.getVar(processInstanceId,
				"ComLoanNeed"); 

		toAccesoryListService.getAccesoryListLingZheng(request, taskitem,
				(boolean) (psf == null ? false : psf.getValue()),
				(boolean) (self == null ? false : self.getValue()),
				(boolean) (com == null ? false : com.getValue()));
		return "task" + UiImproveUtil.getPageType(request) + "/taskKnotCommission";
	}
	@RequestMapping(value="submitKnotCommission")
	@ResponseBody
	public Result sumbitKnotCommission(HttpServletRequest request,KnotCommissionVO knotCommissionVO){
		/* 流程引擎相关 */
		Result rs=new Result();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService
				.findToCaseByCaseCode(knotCommissionVO.getCaseCode());
		 workFlowManager.submitTask(variables, knotCommissionVO.getTaskId(), knotCommissionVO.getProcessInstanceId(),
				toCase.getLeadingProcessId(),
				knotCommissionVO.getCaseCode());
		/**
		 * 与ccai交互
		 */
		SessionUser sessionUser=uamSessionService.getSessionUser();
		FlowFeedBack info=new FlowFeedBack(sessionUser, CcaiFlowResultEnum.SUCCESS,"进入结案归档环节");
		ApiResultData apiResultData=flowApiService.tradeFeedBackCcai(knotCommissionVO.getCaseCode(), CcaiTaskEnum.TRADE_ACCESS_BROKERAGE,info);
		if(apiResultData.isSuccess()){
			//修改案件状态为驳回CCAI
			ToCase ca  = toCaseService.findToCaseByCaseCode(knotCommissionVO.getCaseCode());
			ca.setStartDate(CaseStatusEnum.YJY.getCode());
			toCaseService.updateByCaseCodeSelective(ca);
			rs.setMessage("交互成功！");
		}else {
			rs.setMessage("交互失败！请联系过户权证！"+apiResultData.toString());
		}
		return rs;
	}
	
}
