package com.centaline.trans.eval.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.eval.service.EvalDetailService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
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
	private WorkFlowManager workFlowManager;
	@Autowired
	ActRuTaskService actRuTaskService;
	@Autowired
	ToEvalReportProcessService toEvalReportProcessService;
	@Autowired
	EvalDetailService evalDetailService;
	/**
	 * 评估单详情for tj
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request,String caseCode,String evaCode) {
		evalDetailService.evalDetail(request, caseCode, evaCode);
		return "eval/evalDetail";
	}
	
	@RequestMapping(value = "/detail/reject")
	@ResponseBody
	public AjaxResponse<?> evalReject(HttpServletRequest request,String caseCode,String evaCode){
		return evalDetailService.evalReject(request, caseCode, evaCode);
	}
	
	@RequestMapping(value = "/detail/saveEvaComChangeItems")
	@ResponseBody
	public AjaxResponse<?> saveEvaComChangeItems(HttpServletRequest request,String caseCode,String evaCode,String changeInfo){
		return evalDetailService.saveEvaComChangeItems(request,evaCode,changeInfo);
	}
	
	@RequestMapping(value = "/detail/checkTransferCommission")
	@ResponseBody
	public AjaxResponse<?> checkTransferCommission(HttpServletRequest request,String caseCode,String evaCode,String changeInfo){
		return evalDetailService.checkTransferCommission(request,evaCode);
	}
}
