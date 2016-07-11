package com.centaline.trans.taskList.web;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.PSFSignService;
import com.centaline.trans.task.vo.PSFSignVO;

@Controller
@RequestMapping(value="/task/PSFSign")
public class PSFSignController {

	@Autowired
	private PSFSignService psfSignService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@RequestMapping("process")
	public String toLoanLostApproveManagerProcess(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		toAccesoryListService.getAccesoryList(request, taskitem);
		request.setAttribute("PSFSign", psfSignService.queryPSFSignNoBlank(caseCode));
		return "task/taskPSFApprove";
	}
	@RequestMapping(value="savePSFSign")
	@ResponseBody
	public AjaxResponse<String> savePSFSign(HttpServletRequest request, PSFSignVO psfSignVO) {
		AjaxResponse<String> response = new AjaxResponse<String> ();
		psfSignService.savePSFSign(psfSignVO);
		return response;
	}
	
	@RequestMapping(value="qureyGuestList")
	@ResponseBody
	public List<TgGuestInfo> qureyGuestList(HttpServletRequest request, String caseCode) {
		TgGuestInfo tgGuestInfo = new TgGuestInfo();
		tgGuestInfo.setCaseCode(caseCode);
		tgGuestInfo.setTransPosition("30006002");
		return tgGuestInfoService.findTgGuestInfosByCaseCode(tgGuestInfo);
	}

	
	@RequestMapping(value="submitPSFSign")
	@ResponseBody
	public boolean submitPSFSign(HttpServletRequest request, PSFSignVO psfSignVO) {	
		psfSignService.savePSFSign(psfSignVO);
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(psfSignVO.getCaseCode());	
		return workFlowManager.submitTask(variables, psfSignVO.getTaskId(), psfSignVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), psfSignVO.getCaseCode());
	}
	
}
