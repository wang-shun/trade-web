package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.task.service.CaseCloseService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;
import com.centaline.trans.utils.UiImproveUtil;


@Controller
@RequestMapping(value="/task/caselostApprove")
public class CaseCloseApproveController {

	@Autowired(required = true)
	private ToCaseService toCaseService;
	/*发送消息*/
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;
	@Autowired(required = true)
	private UamSessionService uamSessionService;/*用户信息*/
	@Autowired
	private CaseCloseService caseCloseService;
	
	@RequestMapping("first/process")
	public String toFirstProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source){
		SessionUser user=uamSessionService.getSessionUser();
		request.setAttribute("source", source);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
		if ( cou >0) {
			caseBaseVO.setLoanType("30004005");
		}
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("approveType", "3");
		request.setAttribute("operator", user != null ? user.getId():"");
		
		return "task" + UiImproveUtil.getPageType(request) + "/taskCaseCloseFirstApprove";
	}
	@RequestMapping("second/process")
	public String toSecondProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source){
		SessionUser user=uamSessionService.getSessionUser();
		request.setAttribute("source", source);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("approveType", "3");
		request.setAttribute("operator", user != null ? user.getId():"");
		return "task" + UiImproveUtil.getPageType(request) + "/taskCaseCloseSecondApprove";
	}
	
	
	@RequestMapping(value="caselostApproveFirst")
	@ResponseBody
	public Boolean caselostApproveFirst(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String CaseCloseFirstCheck, String CaseCloseFirstCheck_response) {
		try {
			caseCloseService.caselostApproveFirst(request, processInstanceVO, loanlostApproveVO, CaseCloseFirstCheck, CaseCloseFirstCheck_response);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@RequestMapping(value="caselostApproveSecond")
	@ResponseBody
	public Boolean caselostApproveSecond(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String CaseCloseSecondCheck, String CaseCloseSecondCheck_response) {
		try {
			caseCloseService.caselostApproveSecond(request, processInstanceVO, loanlostApproveVO, CaseCloseSecondCheck, CaseCloseSecondCheck_response);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@RequestMapping(value="caselostApproveThird")
	@ResponseBody
	public Boolean caselostApproveThird(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String CaseCloseThirdCheck, String CaseCloseThirdCheck_response) {
		try {
			caseCloseService.caselostApproveThird(processInstanceVO, loanlostApproveVO, CaseCloseThirdCheck, CaseCloseThirdCheck_response);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
//		toCase.setCaseProperty("30003002");
//		toCase.setCloseTime(new Date());
//		toCaseService.updateByCaseCodeSelective(toCase);
		return true;
	}
	
}
