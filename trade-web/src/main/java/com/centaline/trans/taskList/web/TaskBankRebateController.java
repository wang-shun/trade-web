package com.centaline.trans.taskList.web;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.api.service.CaseApiService;
import com.centaline.trans.api.vo.ApiRebateReportInfo;
import com.centaline.trans.bankRebate.service.ToBankRebateService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.common.enums.EvalRebateStatusEnum;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.task.entity.ToApproveRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

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

	@RequestMapping(value = "process",method = RequestMethod.GET)
	public String toProcess(String caseCode, String instCode, Model model){
		String compId = "P20171026224044";
		model.addAttribute("bankRebateInfo",toBankRebateService.selectToRebateInfoByCaseCodeAndCompId(caseCode,compId));
		return "bankRebate/bankRebateManagerApprove";
	}

	@RequestMapping(value = "reportInfo",method = RequestMethod.GET)
	@ResponseBody
	public ApiRebateReportInfo getReportInfo(String reportCode){
		return caseApiService.getApiRebateReportInfo(reportCode);
	}
}
