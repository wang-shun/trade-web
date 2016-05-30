package com.centaline.trans.cases.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ServiceRestartService;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Controller
@RequestMapping(value = "/service")
public class ServiceRestartController {
	@Autowired
	private ServiceRestartService serviceRestart;
	@Autowired
	private UamSessionService uamSessionService;
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
		StartProcessInstanceVo piv = serviceRestart.restart(vo);
		AjaxResponse<StartProcessInstanceVo> resp = new AjaxResponse<>();
		resp.setContent(piv);
		return resp;
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
