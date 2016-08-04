package com.centaline.trans.mortgage.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;

@Controller
@RequestMapping(value="/mortgage/tmpBankAudit")
public class TmpBankAduitController {

	@Autowired(required = true)
	private ToCaseService toCaseService;

	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;
	
	@Qualifier("uamMessageServiceClient")
    @Autowired
    private UamMessageService uamMessageService;
	
	@Autowired
	private ToMortgageService toMortgageService;
	
	@Autowired
	private UamTemplateService uamTemplateService;
	
	@Autowired(required = true)
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	
	@RequestMapping("start")
	@ResponseBody
	public StartProcessInstanceVo startWorkFlow(String caseCode) {	
	ToWorkFlow twf = new ToWorkFlow();
	twf.setBusinessKey("TempBankAudit_Process");
	twf.setCaseCode(caseCode);

	ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);

	if(record != null){
		return null;
	}


	/*流程引擎相关*/
	List<RestVariable> variables = new ArrayList<RestVariable>();
	ToCase te=toCaseService.findToCaseByCaseCode(caseCode);
	String orgId = te.getOrgId();
	SessionUser user = uamSessionService.getSessionUser();
	//查询主管
	User manager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(orgId, "Manager");
	String parsentId = uamUserOrgService.getOrgById(orgId).getParentId();
	//查询高级主管
	User seniorManager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(orgId, "Senior_Manager");
	//查询总监
	User director = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(parsentId, "director");
	
	variables.add(new RestVariable("Manager",manager.getUsername()));
	variables.add(new RestVariable("SeniorManager",seniorManager == null?null:seniorManager.getUsername()));
	variables.add(new RestVariable("director",director.getUsername()));

	//启动 
	ProcessInstance process = new ProcessInstance(
			propertyUtilsService.getProcessTmpBankAuditDfKey(), caseCode, variables);
	StartProcessInstanceVo vo = workFlowManager.startCaseWorkFlow(process, manager.getUsername(),caseCode);
	//插入工作流表
	ToWorkFlow toWorkFlow = new ToWorkFlow();
	toWorkFlow.setBusinessKey("TempBankAudit_Process");
	toWorkFlow.setCaseCode(caseCode);
	toWorkFlow.setInstCode(vo.getId());
	toWorkFlow.setProcessDefinitionId(propertyUtilsService.getProcessTmpBankAuditDfKey());
	toWorkFlow.setProcessOwner(user.getId());
	toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
	toWorkFlowService.insertSelective(toWorkFlow);
	
	//更新贷款表临时银行状态为审批中：‘2’
	ToMortgage mortageDb = toMortgageService.findToMortgageByCaseCode(caseCode);
	mortageDb.setTmpBankStatus("2");
	toMortgageService.updateToMortgage(mortageDb);
	
	return vo;
	}
	
	
	@RequestMapping("process")
	public String TmpBankAduitProcess(Model model, HttpServletRequest request, String taskitem,
    		String caseCode, String taskId, String instCode,String source,String post){
		SessionUser user = uamSessionService.getSessionUser();
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

		String ctmCode=null;
		ToCaseInfo caseinfo=toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
		if(null!=caseinfo){
			ctmCode=caseinfo.getCtmCode();
		}

		request.setAttribute("post", post);
		request.setAttribute("mortage", mortage);
		return "task/taskManagerAduit";
	}
	
	@RequestMapping("aduit")
	public AjaxResponse toTmpBankAduitProcess(ToMortgage mortage,String prAddress,
			String tmpBankName,String tmpBankCheck,String taskId,String bankCode,String temBankRejectReason,
			String processInstanceId,String caseCode,String post) {
		SessionUser user=uamSessionService.getSessionUser();

		if("manager".equals(post)){
			boolean isManagerApprove = false;
			if("true".equals(tmpBankCheck)){
				isManagerApprove = true;
			}
				
			//更新案件信息
			ToMortgage mortageDb= toMortgageService.findToMortgageById(mortage.getPkid());

			if(!isManagerApprove){
				mortageDb.setRecLetterNo("");
				mortageDb.setTmpBankUpdateBy("");
				mortageDb.setIsTmpBank("0");
				mortageDb.setLastLoanBank("");
				mortageDb.setFinOrgCode("");
				mortageDb.setTmpBankStatus("0");
				mortageDb.setTmpBankRejectReason(temBankRejectReason);
				//更新流程状态为‘4’：已完成
				ToWorkFlow twf = new ToWorkFlow();
				twf.setBusinessKey("TempBankAudit_Process");
				twf.setCaseCode(caseCode);
				ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
				if(record != null){
					record.setStatus(WorkFlowStatus.COMPLETE.getCode());
					toWorkFlowService.updateByPrimaryKeySelective(record);
				}

			}else{
				mortageDb.setFinOrgCode(bankCode);
				mortageDb.setTmpBankUpdateBy(user.getId());
				mortageDb.setTmpBankUpdateTime(new Date());
				mortageDb.setTmpBankStatus("");
				mortageDb.setTmpBankRejectReason("");
			}
			toMortgageService.updateToMortgage(mortageDb);	
	
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("isManagerApprove",isManagerApprove));
			
			workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);

		}else if("seniorManager".equals(post)){
			boolean isSeniorManagerApprove = false;
			if("true".equals(tmpBankCheck)){
				isSeniorManagerApprove = true;
			}

			//更新案件信息
			ToMortgage mortageDb= toMortgageService.findToMortgageById(mortage.getPkid());

			if(!isSeniorManagerApprove ){
				mortageDb.setRecLetterNo("");
				mortageDb.setTmpBankUpdateBy("");
				mortageDb.setIsTmpBank("0");
				mortageDb.setLastLoanBank("");
				mortageDb.setFinOrgCode("");
				mortageDb.setTmpBankStatus("0");
				mortageDb.setTmpBankRejectReason(temBankRejectReason);
				//更新流程状态为‘4’：已完成
				ToWorkFlow twf = new ToWorkFlow();
				twf.setBusinessKey("TempBankAudit_Process");
				twf.setCaseCode(caseCode);
				ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
				if(record != null){
					record.setStatus(WorkFlowStatus.COMPLETE.getCode());
					toWorkFlowService.updateByPrimaryKeySelective(record);
				}
			}else{

				//mortageDb.setTmpBankUpdateBy(user.getId());
				mortageDb.setTmpBankUpdateTime(new Date());
			}
			toMortgageService.updateToMortgage(mortageDb);			
			
			
			List<RestVariable> variables = new ArrayList<RestVariable>();
			variables.add(new RestVariable("isSeniorManagerApprove",isSeniorManagerApprove ));
			
			workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
		}else if("director".equals(post)){	
			
			ToMortgage mortageDb= toMortgageService.findToMortgageById(mortage.getPkid());
			ToCase c = toCaseService.findToCaseByCaseCode(mortageDb.getCaseCode());
			//更新案件信息
			if("false".equals(tmpBankCheck)){
				mortageDb.setRecLetterNo("");
				mortageDb.setTmpBankUpdateBy("");
				mortageDb.setIsTmpBank("0");
				mortageDb.setLastLoanBank("");
				mortageDb.setFinOrgCode("");
				mortageDb.setTmpBankStatus("0");
				mortageDb.setTmpBankRejectReason(temBankRejectReason);
			}else if("true".equals(tmpBankCheck)){
				mortageDb.setTmpBankStatus("1");

				//mortageDb.setTmpBankUpdateBy(user.getId());
				mortageDb.setTmpBankUpdateTime(new Date());
			}

			toMortgageService.updateToMortgage(mortageDb);		
			
			//更新流程状态为‘4’：已完成
			ToWorkFlow twf = new ToWorkFlow();
			twf.setBusinessKey("TempBankAudit_Process");
			twf.setCaseCode(caseCode);
			ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
			if(record != null){
				record.setStatus(WorkFlowStatus.COMPLETE.getCode());
				toWorkFlowService.updateByPrimaryKeySelective(record);
			}
			
			Map<String, Object>params=new HashMap<String, Object>();
			params.put("case_code", mortageDb.getCaseCode());
			params.put("property_address", prAddress);
			params.put("bank", tmpBankName);
			
			String content = uamTemplateService.mergeTemplate("TMP_BANK_REMINDER", params);
			Message message = new Message();
			// 消息标题
			message.setTitle("临时银行处理提醒");
			// 消息类型
			message.setType(MessageType.SITE);
			/* 设置提醒列别 */

			message.setMsgCatagory(MsgCatagoryEnum.NEWS.getCode());

			/* 内容 */
			message.setContent(content);
			/* 发送人 */
			String senderId = user.getId();
			/* 设置发送人 */
			message.setSenderId(senderId);

			uamMessageService.sendMessageByUser(message, c.getLeadingProcessId());
			
			List<RestVariable> variables = new ArrayList<RestVariable>();
			workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
			}
		
		return AjaxResponse.success();
		
	}
	



}
