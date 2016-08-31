package com.centaline.trans.mortgage.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.TmpBankStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;

@Controller
@RequestMapping(value="/mortgage/tmpBankAudit")
public class TmpBankAduitController {

	@Autowired(required = true)
	private ToCaseService toCaseService;
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	@Qualifier("uamMessageServiceClient")
    @Autowired
    private UamMessageService uamMessageService;
	
	@Autowired
	private ToMortgageService toMortgageService;

	
	@Autowired(required = true)
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	
	@RequestMapping("start")
	@ResponseBody
	public AjaxResponse<String> startWorkFlow(String caseCode) {	
		
	ToWorkFlow twf = new ToWorkFlow();
	twf.setBusinessKey(WorkFlowEnum.TMP_BANK_BUSSKEY.getCode());
	twf.setCaseCode(caseCode);

	ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
	
	//更新贷款表临时银行状态为默认：‘’
	ToMortgage mortage = toMortgageService.findToMortgageByCaseCode2(caseCode);
	String status = mortage.getTmpBankStatus();
	
	if(record != null || TmpBankStatusEnum.AGREE.getCode().equals(status)){
		throw new BusinessException("启动失败：流程正在运行或已经结束！");
	}

	return toMortgageService.startTmpBankWorkFlow(caseCode);
	
	}
	
	
	@RequestMapping("process")
	public String TmpBankAduitProcess(Model model, HttpServletRequest request, String taskitem,
    		String caseCode, String taskId, String instCode,String source,String post){
		ToMortgage mortage = toMortgageService.findToMortgageByCaseCode2(caseCode);
		
		request.setAttribute("post", post);
		request.setAttribute("taskId", taskId);
    	request.setAttribute("processInstanceId", instCode);
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("taskitem", taskitem);
		request.setAttribute("source", source);
		ToCase c = toCaseService.findToCaseByCaseCode(caseCode);
		request.setAttribute("afterTimeFlag", false);
		if(c != null) {
			CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(c.getPkid());
			if(c.getCreateTime()!=null){
				request.setAttribute("afterTimeFlag", c.getCreateTime().after(new Date(1467302399999l)));
			}
			request.setAttribute("caseBaseVO", caseBaseVO);
		}

		request.setAttribute("post", post);
		request.setAttribute("mortage", mortage);
		return "task/taskManagerAduit";
	}
	
	@RequestMapping("aduit")
	public AjaxResponse<?> toTmpBankAduitProcess(ToMortgage mortage,String prAddress,
			String tmpBankName,String tmpBankCheck,String taskId,String bankCode,String temBankRejectReason,
			String processInstanceId,String caseCode,String post) {
		
		return toMortgageService.tmpBankThriceAduit(mortage, prAddress, tmpBankName, tmpBankCheck, taskId, bankCode, temBankRejectReason, processInstanceId, caseCode, post);
		
	}
	



}
