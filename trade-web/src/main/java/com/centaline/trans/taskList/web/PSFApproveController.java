package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;

@Controller
@RequestMapping(value="/task/PSFApprove")
public class PSFApproveController {

	@Autowired
	private ToMortgageService toMortgageService;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;

	@RequestMapping("process")
	public String toLoanLostApproveManagerProcess(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
		if ( cou >0) {
			caseBaseVO.setLoanType("30004005");
		}
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		toAccesoryListService.getAccesoryList(request, taskitem);
		request.setAttribute("PSFApprove", toMortgageService.findToMortgageByMortTypeAndCaseCode(caseCode,"30016003"));//--30016003
		return "task/taskPSFApprove";
	}
	
	@RequestMapping(value="saveMortgage")
	@ResponseBody
	public AjaxResponse<String> saveMortgage(HttpServletRequest request, ToMortgage toMortgage) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		ToMortgage mortgage= toMortgageService.findToMortgageById(toMortgage.getPkid());
		mortgage.setApprDate(toMortgage.getApprDate());
		mortgage.setRemark(toMortgage.getRemark());
		toMortgageService.saveToMortgage(mortgage);
		return response;
	}
	

	@RequestMapping(value="submitMortgage")
	@ResponseBody
	public Result submitMortgage(HttpServletRequest request, ToMortgage toMortgage,
			String taskId, String processInstanceId, String partCode) {

		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		//RestVariable restVariable = new RestVariable();
		// restVariable.setName("EvaReportNeedAtLoanRelease");
		// restVariable.setValue(EvaReportNeedAtLoanRelease.equals(true));
		//variables.add(restVariable);
		
		toMortgageService.submitMortgage(toMortgage,variables,taskId,processInstanceId);
		
		/**
		 * 功能: 给客户发送短信
		 * 作者：zhangxb16
		 */
		Result rs=new Result();
		try{
			int result=tgGuestInfoService.sendMsgHistory(toMortgage.getCaseCode(), partCode);
			if(result>0){
			}else{
				rs.setMessage("短信发送失败, 请您线下手工再次发送！");
			}
		}catch(BusinessException ex){
			ex.getMessage();
		}
		
		return rs;
	}
}
