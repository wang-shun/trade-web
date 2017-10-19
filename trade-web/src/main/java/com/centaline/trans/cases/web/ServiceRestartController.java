package com.centaline.trans.cases.web;

import javax.servlet.http.HttpServletRequest;
import com.centaline.trans.engine.service.ToWorkFlowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.attachment.service.ToAttachmentService;
import com.centaline.trans.cases.service.ServiceRestartService;
import com.centaline.trans.cases.service.ToCaseService;
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
	@Autowired
	private ToAttachmentService toAttachmentService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	/**
	 * @流程重启
	 * @return
	 */
	@RequestMapping(value = "/restart")
	@ResponseBody
	public AjaxResponse<StartProcessInstanceVo> restart(Model model,ServiceRestartVo vo) {
		
		AjaxResponse<StartProcessInstanceVo> resp = new AjaxResponse<>();
		SessionUser u = uamSessionService.getSessionUser();
		String userId = u.getId();
		String userJob = u.getServiceJobCode();
		try{
			AjaxResponse<StartProcessInstanceVo> result = serviceRestart.restartCheckout(vo,userJob);
			if(!result.getSuccess()){
				 return result;
			}else{
				vo.setUserId(userId);
				vo.setUserName(u.getUsername());
				vo.setOrgId(u.getServiceDepId());		
				
				// 删除相关
				StartProcessInstanceVo piv = serviceRestart.restartAndDeleteSubProcess(vo);		
				resp.setContent(piv);
				return resp;
			}
		}catch(BusinessException be){
			throw new BusinessException(be.getMessage());	 
		}catch(Exception e){
			throw new BusinessException("重启流程异常！");	 
		}
	}

	
	
	@RequestMapping("apply/process")
	public String toApplyProcess(HttpServletRequest request) {

		return "task/taskserviceRestartApply";
	}

	@RequestMapping("approve/process")
	public String toApproveProcess(HttpServletRequest request) {

		return "task/taskserviceRestartApprove";
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/apply")
	@ResponseBody
	public AjaxResponse apply(Model model, ServiceRestartVo vo) {
		SessionUser u = uamSessionService.getSessionUser();
		vo.setUserId(u.getId());
		vo.setUserName(u.getUsername());
		boolean piv = serviceRestart.apply(vo);
		AjaxResponse resp = new AjaxResponse(piv);
		return resp;
	}

	/**
	 * @des  流程重启审批
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/approve")
	@ResponseBody
	public AjaxResponse approve(Model model, ServiceRestartVo vo) {
		SessionUser u = uamSessionService.getSessionUser();
		// 新增判断 如果审批通过 则将对应casecode附件表 可用字段置为N
		if (vo != null) {
			if (vo.getIsApproved()) {
				toAttachmentService.updateToAttachmentByCaseCode(vo.getCaseCode() == null ? "" : vo.getCaseCode());
			}
		}

		vo.setUserId(u.getId());
		vo.setUserName(u.getUsername());
		boolean piv = serviceRestart.approve(vo);
		AjaxResponse resp = new AjaxResponse(piv);
		return resp;
	}
}
