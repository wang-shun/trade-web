package com.centaline.parportal.mobile.taskflow.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;
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
	ToCaseInfoService toCaseInfoService;
	
	@Autowired
	private TsFinOrgService tsFinOrgService;
	
	@RequestMapping("process")
	@ResponseBody
	public Object TmpBankAduitProcess(Model model, HttpServletRequest request, String taskitem,
    		String caseCode, String instCode,String source,String post){
		String taskId = request.getParameter("taskId");
		ToMortgage mortage = toMortgageService.findToMortgageByCaseCode2(caseCode);
		ToCase c = toCaseService.findToCaseByCaseCode(caseCode);
		request.setAttribute("afterTimeFlag", false);
		CaseBaseVO caseBaseVO = null;
		if(c != null) {
			caseBaseVO = toCaseService.getCaseBaseVO(c.getPkid());
		}
		
        JSONObject json = new JSONObject();
        //根据post 是否等于  manager 判断银行 是否可以选择
		json.put("post", post);
		json.put("prAddress", caseBaseVO.getToPropertyInfo().getPropertyAddr());
//		处理分行名字
//		json.put("tmpBankName", value);
		
		json.put("partCode", taskitem);
		json.put("pkid", mortage.getPkid());
		json.put("taskId", taskId);
		json.put("caseCode", caseCode);
		json.put("processInstanceId", instCode);

		json.put("approveType", 8);
		
		if(StringUtils.isNotBlank(mortage.getFinOrgCode())) {
			TsFinOrg childBank = tsFinOrgService.findBankByFinOrg(mortage.getFinOrgCode());
			json.put("childBank", childBank);
			
			TsFinOrg parentsBank = tsFinOrgService.findBankByFinOrg(childBank.getFaFinOrgCode());
			json.put("parentsBank", parentsBank);
		}
		return json;
	}
	
	@RequestMapping("audit")
	@ResponseBody
	public Object toTmpBankAduitProcess(ToMortgage mortage,String prAddress,
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
