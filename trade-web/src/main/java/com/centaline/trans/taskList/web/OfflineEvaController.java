package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.mortgage.service.ToEvaReportService;

@Controller
@RequestMapping(value="task/offlineEva")
public class OfflineEvaController {
	
	@Autowired
	private ToEvaReportService toEvaReportService;
	

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@RequestMapping(value="saveToEvaReport")
	@ResponseBody
	public Boolean saveToEvaReport(ToEvaReport toEvaReport) {
		toEvaReportService.saveToEvaReport(toEvaReport);
		return true;
	}
	
	@RequestMapping(value="submitToEvaReport")
	@ResponseBody
	public Boolean submitToEvaReport(ToEvaReport toEvaReport, String taskId, String processInstanceId) {
		toEvaReportService.saveToEvaReport(toEvaReport);
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(toEvaReport.getCaseCode());	
		return workFlowManager.submitTask(variables, taskId, processInstanceId, 
				toCase.getLeadingProcessId(), toEvaReport.getCaseCode());
	}
}
