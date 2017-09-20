package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public Boolean sumbitKnotCommission(HttpServletRequest request,KnotCommissionVO knotCommissionVO){
		/* 流程引擎相关 */
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService
				.findToCaseByCaseCode(knotCommissionVO.getCaseCode());
		return workFlowManager.submitTask(variables, knotCommissionVO.getTaskId(), knotCommissionVO.getProcessInstanceId(),
				toCase.getLeadingProcessId(),
				knotCommissionVO.getCaseCode());
	}
	
}
