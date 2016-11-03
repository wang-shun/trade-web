package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.task.service.InvalidCaseApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Controller
@RequestMapping(value = "/task/invalidCaseApprove")
public class InvalidCaseApproveController {

	@Autowired
	private InvalidCaseApproveService invalidCaseApproveService;
	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private UamSessionService uamSessionService;
	@RequestMapping(value="process")
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source){
		SessionUser user=uamSessionService.getSessionUser();
		request.setAttribute("approveType", "0");
		request.setAttribute("operator", user != null ? user.getId():"");
		
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		
		//设置显示税费卡提示
		int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
		if ( cou >0) {
			caseBaseVO.setLoanType("30004005");
		}
		
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		return "task/taskInvalidCaseApprove";
	}
	@RequestMapping(value = "invalidCaseApprove")
	@ResponseBody
	public Boolean invalidCaseApprove(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String InvalidCaseApprove, String InvalidCaseApprove_response) {
		return invalidCaseApproveService.invalidCaseApprove(processInstanceVO, loanlostApproveVO, InvalidCaseApprove,
				InvalidCaseApprove_response);
	}

}
