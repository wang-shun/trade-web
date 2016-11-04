package com.centaline.trans.cases.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ServiceRestartService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;


@Controller
@RequestMapping(value = "/service")
public class ServiceRestartController {
	@Autowired
	private ServiceRestartService serviceRestart;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private ToCaseService toCaseService;
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/restart")
	@ResponseBody
	public AjaxResponse<StartProcessInstanceVo> restart(Model model, ServiceRestartVo vo) {
		SessionUser u= uamSessionService.getSessionUser();
		vo.setUserId(u.getId());
		vo.setUserName(u.getUsername());
		vo.setOrgId(u.getServiceDepId());
		// 删除相关
		StartProcessInstanceVo piv = serviceRestart.restartAndDeleteSubProcess(vo);
		AjaxResponse<StartProcessInstanceVo> resp = new AjaxResponse<>();
		resp.setContent(piv);
		return resp;
	}
	@RequestMapping("apply/process")
	public String toApplyProcess(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId) {
		SessionUser user= uamSessionService.getSessionUser();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		request.setAttribute("approveType", "7");
		request.setAttribute("operator", user != null ? user.getId() : "");
		return "task/taskserviceRestartApply";
	}
	@RequestMapping("approve/process")
	public String toApproveProcess(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId) {
		SessionUser user= uamSessionService.getSessionUser();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//税费卡
		int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
		if ( cou >0) {
			caseBaseVO.setLoanType("30004005");
		}
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		request.setAttribute("approveType", "7");
		request.setAttribute("operator", user != null ? user.getId() : "");
		return "task/taskserviceRestartApprove";
	}
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/apply")
	@ResponseBody
	public AjaxResponse apply(Model model, ServiceRestartVo vo) {
		SessionUser u= uamSessionService.getSessionUser();
		vo.setUserId(u.getId());
		vo.setUserName(u.getUsername());
		boolean piv = serviceRestart.apply(vo);
		AjaxResponse resp = new AjaxResponse(piv);
		return resp;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/approve")
	@ResponseBody
	public AjaxResponse approve(Model model, ServiceRestartVo vo) {
		SessionUser u= uamSessionService.getSessionUser();
		vo.setUserId(u.getId());
		vo.setUserName(u.getUsername());
		boolean piv = serviceRestart.approve(vo);
		AjaxResponse resp = new AjaxResponse(piv);
		return resp;
	}
}
