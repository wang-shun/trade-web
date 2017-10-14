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
import com.centaline.trans.eval.service.EvalServiceRestartService;

/**
 * @Description:评估流程重启
 * @author：jinwl6
 * @date:2017年10月12日
 * @version:
 */
@Controller
@RequestMapping(value = "/eval/restart")
public class EvalServiceRestartController {
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private EvalServiceRestartService evalServiceRestartService;
	
	/**
	 * @评估流程初始化处理
	 * @return
	 */
	@RequestMapping(value = "init")
	@ResponseBody
	public AjaxResponse<StartProcessInstanceVo> restart(Model model,ServiceRestartVo vo) {
		
		AjaxResponse<StartProcessInstanceVo> resp = new AjaxResponse<>();
		SessionUser u = uamSessionService.getSessionUser();
		String userId = u.getId();
		String userJob = u.getServiceJobCode();
		try{
			boolean flag = evalServiceRestartService.checkIsCanRestart(vo,userJob);
			if(flag == false){
				 resp.setSuccess(false);
				 resp.setMessage("此评估报告已使用，不能重启流程！");
				 return resp;
			}else{
				vo.setUserId(userId);
				vo.setUserName(u.getUsername());
				vo.setOrgId(u.getServiceDepId());		
				
				//评估相关流程挂起并启动评估重启流程
				StartProcessInstanceVo piv = evalServiceRestartService.SuspendEvalSubProcess(vo);		
				resp.setContent(piv);
				return resp;
			}
		}catch(Exception e){
			throw new BusinessException("重启流程异常！",e);	 
		}
	}
	
	/**
	 * 评估流程重启申请提交
	 * @param model
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "submit")
	@ResponseBody
	public AjaxResponse apply(Model model, ServiceRestartVo vo) {
		SessionUser u = uamSessionService.getSessionUser();
		vo.setUserId(u.getId());
		vo.setUserName(u.getUsername());
		boolean piv = evalServiceRestartService.apply(vo);
		AjaxResponse resp = new AjaxResponse(piv);
		return resp;
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

		request.setAttribute("approveType", "10");
		request.setAttribute("operator", user != null ? user.getId() : "");
		return "eval/taskevalServiceRestartApply";
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
			HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("source", source);

		request.setAttribute("approveType", "10");
		request.setAttribute("operator", user != null ? user.getId() : "");
		return "eval/taskevalServiceRestartApprove";
	}
	
	/**
	 * 评估重启流程审批提交
	 * @param model
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/approve")
	@ResponseBody
	public AjaxResponse approve(Model model, ServiceRestartVo vo) {
		boolean result = evalServiceRestartService.approve(vo);
		AjaxResponse resp = new AjaxResponse(result);
		return resp;
	}
}
