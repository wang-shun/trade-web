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
import com.centaline.trans.common.enums.TmpBankStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
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
	
	/**
	 * 贷款 派单页选择临时银行时 保存填写的相关信息
	 * @param toMortgage
	 * @return
	 */
	@RequestMapping("saveTmpBank")
	@ResponseBody
	public AjaxResponse<String> saveTmpBank(ToMortgage toMortgage) {	
		
		//更新贷款表临时银行状态为默认：‘’
		ToMortgage condition = new ToMortgage();
		condition.setCaseCode(toMortgage.getCaseCode());
		condition.setIsMainLoanBank("1");
		ToMortgage mortage = toMortgageService.findToMortgageByCaseCodeWithCommLoan(condition);
		
		mortage.setTmpBankReason(toMortgage.getTmpBankReason());
		mortage.setFinOrgCode(toMortgage.getFinOrgCode());
		mortage.setLoanerPhone(toMortgage.getLoanerPhone());
		mortage.setLoanerName(toMortgage.getLoanerName());
		mortage.setLoanerId(toMortgage.getLoanerId());
		mortage.setLoanerOrgCode(toMortgage.getLoanerOrgCode());
		mortage.setLoanerOrgId(toMortgage.getLoanerOrgId());
		mortage.setIsTmpBank("1");
		toMortgageService.saveToMortgage(mortage);
		return AjaxResponse.success("保存成功");
	}
	
	@RequestMapping("start")
	@ResponseBody
	public AjaxResponse<String> startWorkFlow(ToMortgage toMortgage) {	
		AjaxResponse<String> response = new AjaxResponse<>();
			try {
				ToWorkFlow twf = new ToWorkFlow();
				twf.setBusinessKey(WorkFlowEnum.TMP_BANK_DEFKEY.getCode());
				twf.setCaseCode(toMortgage.getCaseCode());
			
				ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
				
				//更新贷款表临时银行状态为默认：‘’
				ToMortgage condition = new ToMortgage();
				condition.setCaseCode(toMortgage.getCaseCode());
				condition.setIsMainLoanBank("1");
				ToMortgage mortage = toMortgageService.findToMortgageByCaseCodeWithCommLoan(condition);
				if(mortage!=null){
					String status = mortage.getTmpBankStatus();
					if(record != null || TmpBankStatusEnum.AGREE.getCode().equals(status) || TmpBankStatusEnum.INAPPROVAL.getCode().equals(status)){
						throw new BusinessException("启动失败：流程正在运行或已经结束！");
					}
				}else{
					mortage=new ToMortgage();
					mortage.setIsDelegateYucui("1");
					mortage.setCaseCode(toMortgage.getCaseCode());
					mortage.setIsActive("0");
					mortage.setIsMainLoanBank("1");
				}
				mortage.setTmpBankReason(toMortgage.getTmpBankReason());
				mortage.setFinOrgCode(toMortgage.getFinOrgCode());
				mortage.setLoanerPhone(toMortgage.getLoanerPhone());
				mortage.setLoanerName(toMortgage.getLoanerName());
				mortage.setLoanerId(toMortgage.getLoanerId());
				mortage.setLoanerOrgCode(toMortgage.getLoanerOrgCode());
				mortage.setLoanerOrgId(toMortgage.getLoanerOrgId());
				mortage.setIsTmpBank("1");
				toMortgageService.saveToMortgage(mortage);
	
				response = toMortgageService.startTmpBankWorkFlow(toMortgage.getCaseCode(),"");
				response.setSuccess(true);
				response.setMessage("临时银行审批流程开启成功！");
			} catch (Exception e) {
				response.setMessage(e.getMessage());
				response.setSuccess(false);
				e.printStackTrace();
			}
		
			return response;
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
			//税费卡
			int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
			if ( cou >0) {
				caseBaseVO.setLoanType("30004005");
			}
			request.setAttribute("caseBaseVO", caseBaseVO);
		}

		request.setAttribute("post", post);
		request.setAttribute("mortage", mortage);
		return "task/taskManagerAduit";
	}
	
	@RequestMapping("audit")
	@ResponseBody
	public AjaxResponse<?> toTmpBankAduitProcess(ToMortgage mortage,String prAddress,
			String tmpBankName,String tmpBankCheck,String taskId,String bankCode,String temBankRejectReason,
			String processInstanceId,String caseCode,String post) {
		AjaxResponse<?> response = new AjaxResponse<>();
		try {
			response = toMortgageService.tmpBankThriceAduit(mortage, prAddress, tmpBankName, tmpBankCheck, taskId, bankCode, temBankRejectReason, processInstanceId, caseCode, post);
			response.setSuccess(true);
			response.setMessage("任务提交成功！");
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}
	



}
