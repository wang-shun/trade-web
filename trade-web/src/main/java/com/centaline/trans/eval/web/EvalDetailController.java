package com.centaline.trans.eval.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.EvalStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.evaPricing.service.EvaPricingService;
import com.centaline.trans.eval.entity.ToEvaInvoice;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.entity.ToEvalSettle;
import com.centaline.trans.eval.service.ToEvaCommissionChangeService;
import com.centaline.trans.eval.service.ToEvaInvoiceService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.eval.service.ToEvalSettleService;
import com.centaline.trans.task.service.ActRuTaskService;

/**
 * @Description:评估单详情-天津
 * @author：jinwl6
 * @date:2017年10月10日
 * @version:
 */
@Controller
@RequestMapping(value = "/eval")
public class EvalDetailController {
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private EvaPricingService evaPricingService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	ActRuTaskService actRuTaskService;
	@Autowired
	ToEvalReportProcessService toEvalReportProcessService;
	@Autowired
	ToEvalSettleService toEvalSettleService;
	@Autowired
	ToEvaInvoiceService toEvaInvoiceService;
	@Autowired
	ToEvaCommissionChangeService toEvaCommissionChangeService;
	/**
	 * 评估单详情for tj
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request,String caseCode,String evaCode) {
		// TODO
		SessionUser user = uamSessionService.getSessionUser();
		String userOrgId = user.getServiceDepId();
		
		ToEvaPricingVo toEvaPricingVo = evaPricingService.findEvaPricingDetailByCaseCode(caseCode);//查询询价信息
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByEvalCode(evaCode);//查询
		
		// 工作流
		ToWorkFlow inWorkFlow = new ToWorkFlow();
		inWorkFlow.setBusinessKey(WorkFlowEnum.EVAL_PROCESS.getCode());
		inWorkFlow.setBizCode(evaCode);
		ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(inWorkFlow);
		
		//评估发票信息
		ToEvaInvoice toEvaInvoice = toEvaInvoiceService.selectByCaseCode(caseCode);
		
		//评估返利报告审批信息
		
		
		//评估爆单信息
		//
		
		//评估公司变更信息
		
		//评估结算信息
		ToEvalSettle toEvalSettle=toEvalSettleService.findToCaseByCaseCode(caseCode);
		
		//调佣审批信息
		
		//附件
		
		//备注
		
		request.setAttribute("toEvaPricingVo", toEvaPricingVo);
		request.setAttribute("toEvalReportProcess", toEvalReportProcess);
		request.setAttribute("toEvaInvoice", toEvaInvoice);
		request.setAttribute("toEvalSettle", toEvalSettle);
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("evaCode", evaCode);
		request.setAttribute("queryOrg", userOrgId);
		request.setAttribute("toWorkFlow", toWorkFlow);
		return "eval/evalDetail";
	}
	
	@RequestMapping(value = "reject")
	@ResponseBody
	public AjaxResponse<?> evalReject(HttpServletRequest request,String caseCode,String evaCode){
		
		//删除评估流程
		List<TaskVo> taskVos = actRuTaskService.getRuTaskByBizCode(evaCode);
		for (TaskVo task : taskVos) {
				try{
					workFlowManager.deleteProcess(task.getInstCode());
				}catch(WorkFlowException e){
                    throw e;					
				}
		}
		
		//更新评估单状态为驳回
		toEvalReportProcessService.updateStatusByEvalCode(EvalStatusEnum.BBH.getCode(),evaCode);
		
		//TODO 发站内信息通知申请人
		
		return new AjaxResponse<>(true);
	}
}
