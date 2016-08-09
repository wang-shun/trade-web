package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;


@Controller
@RequestMapping(value="/task")
public class LoanlostApproveController {

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private LoanlostApproveService loanlostApproveService;
	
	/*发送消息*/
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;
	@Autowired(required=true)
	private UamTemplateService uamTemplateService;
	@Autowired
	private ToPropertyInfoService toPropertyInfoService;
	@Autowired(required = true)
	private UamSessionService uamSessionService;/*用户信息*/
	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;

	@RequestMapping(value={"loanlostApproveManager/process","loanlostApproveDirector/process"})
	public String toLoanLostApproveManagerProcess(HttpServletRequest request, HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("approveType", "1");
		request.setAttribute("operator", user != null ? user.getId() : "");
		request.setAttribute("caseDetail", loanlostApproveService.queryCaseInfo(caseCode,"LoanlostApply",processInstanceId));
		ToMortgage mortgage= toMortgageService.findToSelfLoanMortgage(caseCode);
		
		if(mortgage!=null && mortgage.getCustCode()!=null){
			TgGuestInfo guest=tgGuestInfoService.selectByPrimaryKey(Long.parseLong(mortgage.getCustCode()));
			if(null !=guest){
				request.setAttribute("custCompany",guest.getWorkUnit());
				request.setAttribute("custName",guest.getGuestName());
			}
		};
		return "task/task"+taskitem;
	}
	
	
	@RequestMapping(value="loanlostApprove/loanlostApproveFirst")
	@ResponseBody
	public Boolean loanlostApproveFirst(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String LoanLost_manager, String LoanLost_manager_response,String loanLostManagerNotApprove) {
	   	/*保存审核记录*/
		ToApproveRecord toApproveRecord = saveToApproveRecord(processInstanceVO, loanlostApproveVO, LoanLost_manager, LoanLost_manager_response,loanLostManagerNotApprove);
		
		/*发送提醒*/
		sendMessage(processInstanceVO, toApproveRecord.getContent(), toApproveRecord.getApproveType());
		
		/*流程引擎相关*/
		RestVariable restVariable = new RestVariable();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		restVariable.setName("LoanLost_manager");
		restVariable.setValue(LoanLost_manager.equals("true"));		
		variables.add(restVariable);
		//非空判断
		if(!StringUtils.isBlank(LoanLost_manager_response)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("LoanLost_manager_response");
			restVariable1.setValue(LoanLost_manager_response);
			variables.add(restVariable1);
		}

		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
		
		return workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
	}
	
	@RequestMapping(value="loanlostApprove/loanlostApproveSecond")
	@ResponseBody
	public Boolean loanlostApproveSecond(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String LoanLost_director, String LoanLost_director_response,String loanLostDirectorNotApprove) {
		/*保存审核记录*/
		ToApproveRecord toApproveRecord = saveToApproveRecord(processInstanceVO, loanlostApproveVO, LoanLost_director, LoanLost_director_response,loanLostDirectorNotApprove);
		/*发送提醒*/
		sendMessage(processInstanceVO, toApproveRecord.getContent(), toApproveRecord.getApproveType());
		
		/*流程引擎相关*/
		RestVariable restVariable = new RestVariable();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		restVariable.setName("LoanLost_director");
		restVariable.setValue(LoanLost_director.equals("true"));
		variables.add(restVariable);
		if(!StringUtil.isBlank(LoanLost_director_response)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("LoanLost_director_response");
			restVariable1.setValue(LoanLost_director_response);
			variables.add(restVariable1);
		}

		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
		return workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
	}
	
	@RequestMapping(value="loanlostApprove/loanlostApproveThird")
	@ResponseBody
	public Boolean loanlostApproveThird(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String LoanLost_GeneralManager, String LoanLost_GeneralManager_response,String loanLostGeneralManagerNotApprove) {
		
		/*保存审核记录*/
		ToApproveRecord toApproveRecord = saveToApproveRecord(processInstanceVO, loanlostApproveVO, LoanLost_GeneralManager, LoanLost_GeneralManager_response,loanLostGeneralManagerNotApprove);
		/*发送提醒*/
		sendMessage(processInstanceVO, toApproveRecord.getContent(), toApproveRecord.getApproveType());
		
		/*流程引擎相关*/
		RestVariable restVariable = new RestVariable();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		restVariable.setName("LoanLost_GeneralManager");
		restVariable.setValue(LoanLost_GeneralManager.equals("true"));
		variables.add(restVariable);
		if(!StringUtil.isBlank(LoanLost_GeneralManager_response)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("LoanLost_GeneralManager_response");
			restVariable1.setValue(LoanLost_GeneralManager_response);
			variables.add(restVariable1);
		}

		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
		return workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
	}
	
	/**
	 * 保存审核记录
	 * @param processInstanceVO
	 * @param loanlostApproveVO
	 * @param loanLost
	 * @param loanLost_response
	 */
	private ToApproveRecord saveToApproveRecord(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
			String loanLost, String loanLost_response,String notApprove) {
		
		ToApproveRecord toApproveRecord = new ToApproveRecord();
//		toApproveRecord.setPkid(loanlostApproveVO.getLapPkid());
		toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
		toApproveRecord.setPartCode(processInstanceVO.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
		toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
		boolean b = "true".equals(loanLost);
		boolean c = loanLost_response == null || loanLost_response.intern().length() == 0;
		toApproveRecord.setContent((b?"通过":"不通过") + (c?",没有审批意见。":",审批意见为："+loanLost_response));
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
		//审核不通过原因
		toApproveRecord.setNotApprove(notApprove);		
		loanlostApproveService.saveLoanlostApprove(toApproveRecord);
		return toApproveRecord;	

	}
	
	/**
	 * 发送审批结果提醒
	 * @param processInstanceVO
	 * @param approveContent
	 * @param approveType
	 */
	private void sendMessage(ProcessInstanceVO processInstanceVO, String approveContent, String approveType) {
		/*消息模版code*/
		String resourceCode = MsgLampEnum.APPROVE_RESULT_REMINDER.getCode();
		/*消息标题*/
		String title = MsgLampEnum.APPROVE_RESULT_REMINDER.getName();
		//创建map放入消息参数
		Map<String, Object> params = new HashMap<String, Object>();
		//放入参数
		params.put("case_code", processInstanceVO.getCaseCode());
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(processInstanceVO.getCaseCode());
		if(toPropertyInfo != null) {
			params.put("property_address", toPropertyInfo.getPropertyAddr());
		} else {
			params.put("property_address","");
		}
		params.put("approver", uamSessionService.getSessionUser().getRealName());
		params.put("part_name", "流失审批");
		params.put("approve_content", approveContent);
		
	    //拼接发送的字符串
		String content = uamTemplateService.mergeTemplate(resourceCode, params);
		
		Message message= new Message();
		//消息标题
		message.setTitle(title);
		//消息类型  
		message.setType(MessageType.SITE);
		/*设置提醒列别*/
		message.setMsgCatagory(MsgCatagoryEnum.RESPON.getCode());
		/*内容*/
		message.setContent(content);
		/*发送人*/
		String senderId = uamSessionService.getSessionUser().getId();
		message.setSenderId(senderId);
		/*接收人*/
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
		toApproveRecord.setApproveType(approveType);
		List<String> list = loanlostApproveService.findApproveRecordByList(toApproveRecord);
		for(String operator : list) {
			if(operator.equals(senderId)) {/*不给自己发送提醒*/
				continue;
			}
			/*发送*/
			uamMessageService.sendMessageByDist(message, operator);
		}
	}
	
}
