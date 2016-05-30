package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.TsMsgSendHistory;
import com.centaline.trans.task.service.TsMsgSendHistoryService;

@Controller
@RequestMapping(value="/task/PSFApprove")
public class PSFApproveController {

	@Autowired
	private ToMortgageService toMortgageService;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	
	@Autowired
	private ToPropertyInfoService topropertyInfoService;
	
	@Autowired
    private UamBasedataService   uambasedataService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private TsMsgSendHistoryService tsmsgSendHistoryService;
	
	@RequestMapping(value="saveMortgage")
	@ResponseBody
	public AjaxResponse<String> saveMortgage(HttpServletRequest request, ToMortgage toMortgage) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		ToMortgage mortgage = toMortgageService.saveToMortgage(toMortgage);
		response.setContent(String.valueOf(mortgage.getPkid()));
		return response;
	}

	@RequestMapping(value="submitMortgage")
	@ResponseBody
	public Result submitMortgage(HttpServletRequest request, ToMortgage toMortgage,
			String taskId, String processInstanceId, String partCode) {
		toMortgageService.saveToMortgage(toMortgage);
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		//RestVariable restVariable = new RestVariable();
		// restVariable.setName("EvaReportNeedAtLoanRelease");
		// restVariable.setValue(EvaReportNeedAtLoanRelease.equals(true));
		//variables.add(restVariable);
		
		ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());
		workFlowManager.submitTask(variables, taskId, processInstanceId, 
				toCase.getLeadingProcessId(), toMortgage.getCaseCode());
		
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
