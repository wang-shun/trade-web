package com.centaline.trans.eval.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.eval.service.EvalServiceStopService;

/**
 * @Description:评估爆单
 * @author：jinwl6
 * @date:2017年10月16日
 * @version:
 */
@Controller
@RequestMapping(value = "/eval/stop")
public class evalServiceStopController {
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private EvalServiceStopService evalServiceStopService;
	
	/**
	 * @评估流程初始化处理
	 * @return
	 */
	@RequestMapping(value = "init")
	@ResponseBody
	public AjaxResponse<StartProcessInstanceVo> restart(Model model,ServiceRestartVo vo) {
		
		try{
			return evalServiceStopService.checkIsCanStop(vo);
		}catch(Exception e){
			throw new BusinessException("评估爆单异常！",e);	 
		}
	}
	
	/**
	 * 评估流程爆单申请提交
	 * @param model
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "apply/submit")
	@ResponseBody
	public AjaxResponse apply(Model model, ServiceRestartVo vo) {
		return evalServiceStopService.applySubmit(vo);
	}
	
	/**
	 * 从代办任务进入评估流程重启流程申请页面  (流程重启审批驳回后出现的申请任务)
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping("apply/process")
	public String toApplyProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("source", source);

		request.setAttribute("approveType", "11");
		request.setAttribute("operator", user != null ? user.getId() : "");
		return "eval/taskevalServiceStopApply";
	}
    
	/**
	 * 从代办任务进入评估流程重启流程审批页面
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping("approve/process")
	public String toApproveProcess(HttpServletRequest request,
			HttpServletResponse response,String source,String  businessKey,
			String taskitem, String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("source", source);
        request.setAttribute("evaCode", businessKey);
		request.setAttribute("approveType", "11");
		request.setAttribute("operator", user != null ? user.getId() : "");
		return "eval/taskevalServiceStopApprove";
	}
	
	/**
	 * 评估重启流程审批提交
	 * @param model
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/approve/submit")
	@ResponseBody
	public AjaxResponse approve(Model model, ServiceRestartVo vo) {
		return evalServiceStopService.approveSubmit(vo);
	}

}
