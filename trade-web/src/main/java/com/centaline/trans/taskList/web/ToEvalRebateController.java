package com.centaline.trans.taskList.web;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
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
import java.util.Date;

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
	private UamSessionService uamSessionService;
	
	@RequestMapping(value = "evalRebate/assistant",method = RequestMethod.GET)
	public String toProcess(HttpServletRequest request,String caseCode){
		ToEvalReportProcess eval = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
		request.setAttribute("eval",eval);
		request.setAttribute("evalCompany",tsFinOrgService.findBankByFinOrg(eval.getFinOrgId()));
		request.setAttribute("rebate",toEvalRebateService.findToEvalRebateByCaseCode(caseCode));
		return "eval/taskEvalRebateAssistant";
	}

	@RequestMapping(value="evalRebate/assistant" , method = RequestMethod.POST)
	public AjaxResponse<String> submit(ToApproveRecord record,String rebateId,
			String evalRecept,boolean approve,String response){
		AjaxResponse<String> result = new AjaxResponse<>();
		try{
			SessionUser user = uamSessionService.getSessionUser();
			ToEvalRebate rebate = toEvalRebateService.selectByPrimaryKey(Long.parseLong(rebateId));
			if(approve){
				rebate.setStatus(EvalRebateStatusEnum.SUCCESS.getCode());
			}else{
				rebate.setStatus(EvalRebateStatusEnum.BACK.getCode());
			}
			rebate.setEvalRecept(evalRecept);
			if(approve){
				record.setContent("通过,审批意见为:"+response);
			}else{
				record.setContent("不通过,审批意见为:"+response);
				record.setNotApprove("审批不通过");
			}
			record.setOperatorTime(new Date());
			record.setOperator(user.getId());
			toEvalRebateService.assistantApprove(rebate,record,approve);
			result.setSuccess(true);
			result.setCode("Y");
		}catch(Exception e){
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			result.setCode("N");
		}
		return result;
	}

}
