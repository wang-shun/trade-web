package com.centaline.trans.cases.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.engine.entity.ToWorkFlow;
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
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
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
			boolean flag = serviceRestart.restartCheckout(vo,userJob);
			if(flag == false){
				 resp.setSuccess(false);
				 resp.setMessage("此案件已过户，不能重启流程！");
				 return resp;
			}else{
				vo.setUserId(userId);
				vo.setUserName(u.getUsername());
				vo.setOrgId(u.getServiceDepId());		
				
				// 删除相关
				StartProcessInstanceVo piv = serviceRestart.restartAndDeleteSubProcess(vo);		
				resp.setContent(piv);
				return resp;
			}
		}catch(Exception e){
			throw new BusinessException("重启流程异常！");	 
		}
	}

	
	
	@RequestMapping("apply/process")
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
	public String toApproveProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		// 税费卡
		int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
		if (cou > 0) {
			caseBaseVO.setLoanType("30004005");
		}
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);

		request.setAttribute("approveType", "7");
		request.setAttribute("operator", user != null ? user.getId() : "");
		return "task" + UiImproveUtil.getPageType(request)
				+ "/taskserviceRestartApprove";
	}

	/**
	 * 
	 * @return
	 */
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
		ToWorkFlow workFlow = new ToWorkFlow();

		workFlow.setCaseCode(vo.getCaseCode());
		ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(workFlow);
		if (record != null) {
			record.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(record);
		}

		vo.setUserId(u.getId());
		vo.setUserName(u.getUsername());
		boolean piv = serviceRestart.approve(vo);
		AjaxResponse resp = new AjaxResponse(piv);
		return resp;
	}
}
