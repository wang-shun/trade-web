package com.centaline.trans.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.template.remote.UamTemplateService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.EditCaseDetailService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.EditCaseDetailVO;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.AwardBaseService;
import com.centaline.trans.task.service.CaseCloseService;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Service
public class CaseCloseServiceImpl implements CaseCloseService {
	
	@Autowired
	private EditCaseDetailService editCaseDetailService;
	@Autowired
	LoanlostApproveService loanlostApproveService;
	@Autowired
	ToCaseService toCaseService;
	@Autowired
	WorkFlowManager workFlowManager;
	@Autowired
	ToPropertyInfoService toPropertyInfoService;
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	UamMessageService uamMessageService;
	@Autowired
	UamSessionService uamSessionService;
	@Autowired
	UamTemplateService uamTemplateService;
	@Autowired
	ToWorkFlowService toWorkFlowService;
	@Autowired
	AwardBaseService awardBaseService;

	@Override
	public boolean submitCaseClose(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, EditCaseDetailVO editCaseDetailVO) throws Exception {
		editCaseDetailService.saveCaseCloseDetai(editCaseDetailVO, processInstanceVO);
		ToApproveRecord toApproveRecord = saveToApproveRecord(processInstanceVO, loanlostApproveVO, "", "结档归案提交。");
		sendMessage(processInstanceVO, toApproveRecord.getContent(), toApproveRecord.getApproveType());
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());
		boolean b =workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		if(!b)
			throw new Exception("任务执行失败");
		return true;
	}

	@Override
	public Boolean caselostApproveFirst(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String CaseCloseFirstCheck, String CaseCloseFirstCheck_response) throws Exception {
		ToApproveRecord toApproveRecord = saveToApproveRecord(processInstanceVO, loanlostApproveVO, CaseCloseFirstCheck, CaseCloseFirstCheck_response);
		/*发送提醒*/
		sendMessage(processInstanceVO, toApproveRecord.getContent(), toApproveRecord.getApproveType());
		/*流程引擎相关*/
		RestVariable restVariable = new RestVariable();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		restVariable.setName("CaseCloseFirstCheck");
		restVariable.setValue(CaseCloseFirstCheck.equals("true"));
		variables.add(restVariable);
		if(!StringUtils.isBlank(CaseCloseFirstCheck_response)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("CaseCloseFirstCheck_response");
			restVariable1.setValue(CaseCloseFirstCheck_response);
			variables.add(restVariable1);
		}
		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
		boolean b = workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		if(!b)
			throw new Exception("任务执行失败");
		return true;
	}

	@Override
	public Boolean caselostApproveSecond(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String CaseCloseSecondCheck, String CaseCloseSecondCheck_response) throws Exception {
		ToApproveRecord toApproveRecord = saveToApproveRecord(processInstanceVO, loanlostApproveVO, CaseCloseSecondCheck, CaseCloseSecondCheck_response);
		/*发送提醒*/
		sendMessage(processInstanceVO, toApproveRecord.getContent(), toApproveRecord.getApproveType());
		/*流程引擎相关*/
		RestVariable restVariable = new RestVariable();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		restVariable.setName("CaseCloseSecondCheck");
		restVariable.setValue(CaseCloseSecondCheck.equals("true"));
		variables.add(restVariable);
		if(!StringUtil.isBlank(CaseCloseSecondCheck_response)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("CaseCloseSecondCheck_response");
			restVariable1.setValue(CaseCloseSecondCheck_response);
			variables.add(restVariable1);
		}
		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
		boolean b = workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		/*审批通过，完结交易单*/
		if(CaseCloseSecondCheck.equals("true")) {
			toCase.setCaseProperty("30003002");
			toCase.setCloseTime(new Date());
			toCaseService.updateByCaseCodeSelective(toCase);
			/*审批通过才能流程才会结束*/
			ToWorkFlow t=new ToWorkFlow();
			t.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
			t.setCaseCode(processInstanceVO.getCaseCode());
			ToWorkFlow mainflow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(t);
			mainflow.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(mainflow);
			awardBaseService.setAwradCaseCloseDate(processInstanceVO.getCaseCode(),new Date());//设置基本奖金的结案时间
		}
		if(!b)
			throw new Exception("任务执行失败");
		return true;
	}
	
	@Override
	public Boolean caselostApproveThird(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
			String CaseCloseThirdCheck, String CaseCloseThirdCheck_response) throws Exception {
		ToApproveRecord toApproveRecord = saveToApproveRecord(processInstanceVO, loanlostApproveVO, CaseCloseThirdCheck, CaseCloseThirdCheck_response);
		/*发送提醒*/
		sendMessage(processInstanceVO, toApproveRecord.getContent(), toApproveRecord.getApproveType());
		/*流程引擎相关*/
		RestVariable restVariable = new RestVariable();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		restVariable.setName("CaseCloseThirdCheck");
		restVariable.setValue(CaseCloseThirdCheck.equals("true"));
		variables.add(restVariable);
		if(!StringUtil.isBlank(CaseCloseThirdCheck_response)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("CaseCloseThirdCheck_response");
			restVariable1.setValue(CaseCloseThirdCheck_response);
			variables.add(restVariable1);
		}
//		processInstanceVO.setCaseCode("ADYG-00410");
		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
		boolean b = workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		if(!b)
			throw new Exception("任务执行失败");
		return true;
	}
	
	/**
	 * 保存审核记录
	 * @param processInstanceVO
	 * @param loanlostApproveVO
	 * @param loanLost
	 * @param loanLost_response
	 */
	private ToApproveRecord saveToApproveRecord(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
			String loanLost, String loanLost_response) {
		ToApproveRecord toApproveRecord = new ToApproveRecord();
//		toApproveRecord.setPkid(loanlostApproveVO.getLapPkid());
		toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
		toApproveRecord.setPartCode(processInstanceVO.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
		toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
		toApproveRecord.setContent(loanLost_response);
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
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
