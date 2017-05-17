package com.centaline.parportal.mobile.taskflow.web;

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
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.attachment.service.ToAttachmentService;
import com.centaline.trans.cases.service.ServiceRestartService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.utils.UiImproveUtil;

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
	
	@RequestMapping("apply/process")
	@ResponseBody
	public String toApplyProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);

		request.setAttribute("approveType", "7");
		request.setAttribute("operator", user != null ? user.getId() : "");
		return "task" + UiImproveUtil.getPageType(request)
				+ "/taskserviceRestartApply";
	}

	@RequestMapping("approve/process")
	@ResponseBody
	public Object toApproveProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId,String taskId) {
		JSONObject jo = new JSONObject();
		jo.put("partCode", taskitem);
		jo.put("caseCode", caseCode);
		jo.put("taskId", taskId);
		jo.put("instCode", processInstanceId);
		jo.put("approveType", 7);
		jo.put("processInstanceId", processInstanceId);
		return jo;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/apply")
	@ResponseBody
	public Object apply(Model model, ServiceRestartVo vo) {
		SessionUser u = uamSessionService.getSessionUser();
		vo.setUserId(u.getId());
		vo.setUserName(u.getUsername());
		boolean piv =false;
		try{
			piv = serviceRestart.apply(vo);
		}catch (Exception e){

		}
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
	public Object approve(Model model, ServiceRestartVo vo) {
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
