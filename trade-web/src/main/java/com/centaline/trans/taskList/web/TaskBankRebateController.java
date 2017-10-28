package com.centaline.trans.taskList.web;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.api.service.CaseApiService;
import com.centaline.trans.api.vo.ApiRebateReportInfo;
import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.service.ToBankRebateService;
import com.centaline.trans.cases.service.ToCaseParticipantService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToApproveRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 银行返利报告任务办理
 * @author yinchao
 * @date 2017-10-27
 */
@Controller
@RequestMapping(value="/task/bankRebate")
public class TaskBankRebateController {

	@Autowired
	ToBankRebateService toBankRebateService;

	@Autowired
	CaseApiService caseApiService;

	@Autowired
	WorkFlowManager workFlowManager;

	@Autowired
	ToCaseParticipantService toCaseParticipantService;

	@RequestMapping(value = "process",method = RequestMethod.GET)
	public String toProcess(String caseCode, String instCode, Model model){

		RestVariable compId = workFlowManager.getVar(instCode,"compId");
		if(compId!=null){
			model.addAttribute("bankRebateInfo",toBankRebateService.selectToRebateInfoByCaseCodeAndCompId(caseCode, (String) compId.getValue()));
		}
		model.addAttribute("loan",toCaseParticipantService.findCaseLoan(caseCode));
		model.addAttribute("warrant",toCaseParticipantService.findCaseWarrant(caseCode));
		return "bankRebate/bankRebateManagerApprove";
	}

	@RequestMapping(value = "reportInfo",method = RequestMethod.GET)
	@ResponseBody
	public ApiRebateReportInfo getReportInfo(String reportCode){
		return caseApiService.getApiRebateReportInfo(reportCode);
	}

	@RequestMapping(value="submit" , method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse<String> submit(ToApproveRecord record,String compId,String response){
		AjaxResponse<String> result = null;
		try{
			record.setContent("通过,审批意见为:"+response);
			ToBankRebateInfo info = toBankRebateService.selectToRebateInfoByCaseCodeAndCompId(record.getCaseCode(),compId);
			info.setFinishTime(new Date());
			toBankRebateService.submitRebateReport(info,record);
			result = new AjaxResponse<>(true,"成功");
		}catch(Exception e){
			result = new AjaxResponse<>(false,e.getMessage());
		}
		return result;
	}
}
