package com.centaline.trans.taskList.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.enums.ToAttachmentEnum;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.TaskHistoricQuery;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.task.service.MortgageSelectService;
import com.centaline.trans.task.vo.MortgageSelecteVo;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;

@Controller
@RequestMapping(value = "/task/mortgageSelect")
public class MortgageSelectController {
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

	@ResponseBody
	@RequestMapping(value = "submit")
	public boolean submit(MortgageSelecteVo vo){
		if (!"2".equals(vo.getMortageService())) {//只有纯公积金才需要选择合作人否则都是取当前用户
			SessionUser u = uamSessionService.getSessionUser();
			vo.setPartner(u.getId());
		}
		return mortgageSelectService.submit2(vo);
	}
	@ResponseBody
	@RequestMapping(value = "getLoanReleasePlan")
	public ToTransPlan getLoanReleasePlan(String caseCode){
		ToTransPlan queryPlan = new ToTransPlan();
	
		queryPlan.setCaseCode(caseCode);
		queryPlan.setPartCode("LoanRelease");
		queryPlan = transplanServiceFacade.findTransPlan(queryPlan);
		return queryPlan;
	}
	@ResponseBody
	@RequestMapping(value = "loanRequirementChange")
	public AjaxResponse<?> loanRequirementChange(MortgageSelecteVo vo) {	
		//判断是否完成‘贷款需求选择’待办任务
		ToWorkFlow workF = toWorkFlowService.queryWorkFlowByInstCode(vo.getProcessInstanceId());
		if(workF!=null &&"operation_process:34:620096".compareTo(workF.getProcessDefinitionId())<=0){//在这个版本之前的流程是没有贷款需求选择的 要变更贷款只能做流程重启  之后的版本都可以做，但operation_process:40:645454之前的版本是子流程的方式
			vo.setProcessDefinitionId(workF.getProcessDefinitionId());
			if(!"operation_process:40:645454".equals(workF.getProcessDefinitionId())){//该版本没有贷款需求选择的环节，这个版本不做这个校验
				TaskHistoricQuery query =new TaskHistoricQuery();
				query.setFinished(true);
				query.setTaskDefinitionKey(ToAttachmentEnum.MORTGAGESELECT.getCode());
				query.setProcessInstanceId(vo.getProcessInstanceId());
				PageableVo pageableVo=workFlowManager.listHistTasks(query);
				if(pageableVo.getData()==null||pageableVo.getData().isEmpty()){
					//请先处理贷款需求选择任务
					return AjaxResponse.fail("请先处理贷款需求选择任务！");
				}
			}
		}else{
			return AjaxResponse.fail("当前流程版本下不允许变更贷款需求！");
		}

		if (!"2".equals(vo.getMortageService())) {//只有纯公积金才需要选择合作人否则都是取当前用户
			SessionUser u = uamSessionService.getSessionUser();
			vo.setPartner(u.getId());
		}
		try {
			mortgageSelectService.loanRequirementChange(vo);
		} catch (BusinessException ex) {
			return AjaxResponse.fail(ex.getMessage());
		}
		return AjaxResponse.success("变更成功");
	}
	
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
		plan.setPartCode("LoanRelease");//放款
		request.setAttribute("loanReleasePlan", transplanServiceFacade.findTransPlan(plan));
		return "task/taskMortgageSelect";
	}
}
