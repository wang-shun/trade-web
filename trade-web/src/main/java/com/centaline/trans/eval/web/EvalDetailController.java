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
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.evaPricing.service.EvaPricingService;
import com.centaline.trans.eval.service.ToEvaReportProcessService;

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
	private ToEvaReportProcessService toEvaReportProcessService;
	
	/**
	 * 评估单详情for tj
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request,String caseCode) {
		// TODO
		SessionUser user = uamSessionService.getSessionUser();
		String userOrgId = user.getServiceDepId();
		
		ToEvaPricingVo toEvaPricingVo = evaPricingService.findEvaPricingDetailByCaseCode(caseCode);//查询询价信息
		// 工作流
		ToWorkFlow inWorkFlow = new ToWorkFlow();
		inWorkFlow.setBusinessKey("operation_process");
		inWorkFlow.setCaseCode(caseCode);
		ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(inWorkFlow);
		
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("queryOrg", userOrgId);
		request.setAttribute("toEvaPricingVo", toEvaPricingVo);
		request.setAttribute("toWorkFlow", toWorkFlow);
		return "eval/evalDetail";
	}
	
	/**
	 * 评估单爆单for tj
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "evalBaodan")
	@ResponseBody
	public AjaxResponse<?> evalBaodan(String caseCode){
		ToWorkFlow record = new ToWorkFlow();
		record.setBusinessKey(WorkFlowEnum.EVAL_PROCESS.getCode());
		record.setCaseCode(caseCode);
		List<ToWorkFlow> wfls = toWorkFlowService.queryActiveToWorkFlowByCaseCode(record);
		//工作流数据更新：爆单
		if(wfls != null && wfls.size() > 0){
			for(ToWorkFlow toWorkFlow : wfls){
				toWorkFlow.setStatus(WorkFlowStatus.BAODAN.getCode());
				toWorkFlowService.updateByPrimaryKey(toWorkFlow);
        		workFlowManager.deleteProcess(toWorkFlow.getInstCode());
			}
		}
		
		//评估单状态更新
		//TODO 
		int res=0;
		if(res == 0){
			return AjaxResponse.fail("更新案件失败!");
		}
		
		return AjaxResponse.success();
	}
}
