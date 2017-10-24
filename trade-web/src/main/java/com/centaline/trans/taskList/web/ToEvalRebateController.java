package com.centaline.trans.taskList.web;

import com.aist.common.web.validate.AjaxResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 评估返利任务办理
 * @author yinchao
 * @date 2017-10-18
 */
@Controller
@RequestMapping(value="/task")
public class ToEvalRebateController {
	@Autowired
	private ToEvalRebateService toEvalRebateService;

	@Autowired
	private ToEvalReportProcessService toEvalReportProcessService;
	
	@Autowired
	private TsFinOrgService tsFinOrgService;

	@Autowired
	private ToCaseInfoService toCaseInfoService;

	@RequestMapping(value = "evalRebate/assistant",method = RequestMethod.GET)
	public String toProcess(HttpServletRequest request,String caseCode){
		ToEvalReportProcess eval = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
		request.setAttribute("eval",eval);
		request.setAttribute("evalCompany",eval !=null?tsFinOrgService.findFinByFinCode(eval.getFinOrgId()) : "");
		request.setAttribute("rebate",toEvalRebateService.findToEvalRebateByCaseCode(caseCode));
		request.setAttribute("ccaiCode",toCaseInfoService.findccaiCodeBycaseCode(caseCode));
		return "eval/taskEvalRebateAssistant";
	}

	@RequestMapping(value="evalRebate/assistant" , method = RequestMethod.POST)
	public AjaxResponse<String> submit(ToApproveRecord record,String rebateId,
			String evalRecept,BigDecimal evalCost,boolean approve,String response){
		AjaxResponse<String> result = null;
		try{
			if(approve){
				record.setContent("通过,审批意见为:"+response);
			}else{
				record.setContent("驳回,审批意见为:"+response);
				record.setNotApprove("审批不通过");
			}
			ToEvalRebate rebate = toEvalRebateService.selectByPrimaryKey(Long.parseLong(rebateId));
			if(approve){
				rebate.setStatus(EvalRebateStatusEnum.SUCCESS.getCode());
			}else{
				rebate.setStatus(EvalRebateStatusEnum.BACK.getCode());
			}
			rebate.setEvalRecept(evalRecept);
			//更新 分成信息
			rebate.setCentaComAmount(rebate.getEvalRealCharges().subtract(evalCost));
			rebate.setEvaComAmount(evalCost);
			rebate.setEvalCost(evalCost);
			toEvalRebateService.assistantApprove(rebate,record,approve);
			result = new AjaxResponse<>(true,"成功");
		}catch(Exception e){
			result = new AjaxResponse<>(false,e.getMessage());
		}
		return result;
	}

}
