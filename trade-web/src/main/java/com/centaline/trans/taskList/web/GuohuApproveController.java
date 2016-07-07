package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Controller
@RequestMapping(value="/task/guohuApprove")
public class GuohuApproveController {

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
	@Autowired(required=true)
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private ToPropertyInfoService toPropertyInfoService;
	@Autowired(required = true)
	private UamSessionService uamSessionService;/*用户信息*/
	

	@RequestMapping(value="guohuApprove")
	@ResponseBody
	public Boolean guohuApprove(HttpServletRequest request, ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
			String GuohuApprove, String GuohuApprove_response,String notApprove) {
		
		ToApproveRecord toApproveRecord = saveToApproveRecord(processInstanceVO, loanlostApproveVO, GuohuApprove, GuohuApprove_response,notApprove);
		if(!"true".equals(GuohuApprove)){
			//没未通过审核，发站内信通知案件负责人
			String sender = uamSessionService.getSessionUser().getId();
			String caseCode = processInstanceVO.getCaseCode();
			String result = toApproveRecord.getContent();
			ToApproveRecord paramsApproveRecord = new ToApproveRecord();
			paramsApproveRecord.setPartCode("Guohu");
			paramsApproveRecord.setCaseCode(caseCode);
			ToApproveRecord lastApproveRecord = loanlostApproveService.findLastApproveRecord(paramsApproveRecord);
			if(lastApproveRecord!=null){
				String recevier = lastApproveRecord.getOperator();
				sendMessage(sender,recevier,caseCode,result);
			}
		}
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable restVariable = new RestVariable();
		restVariable.setName("GuohuApprove");
		restVariable.setValue(GuohuApprove.equals("true"));
		variables.add(restVariable);
		if(!StringUtil.isBlank(GuohuApprove_response)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("GuohuApprove_response");
			restVariable1.setValue(GuohuApprove_response);
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
	 * @param result
	 * @param approveType
	 */
	private void sendMessage(String sender,String recevier,String caseCode, String result) {
		//创建map放入消息参数
		Map<String, Object> params = new HashMap<String, Object>();
		ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
		params.put("property_address",(toPropertyInfo != null)?toPropertyInfo.getPropertyAddr():"");
		params.put("approver", uamSessionService.getSessionUser().getRealName());
		params.put("part_name", "过户审批");
		params.put("approve_content", result);
		params.put("case_code",caseCode);
		
	    //拼接发送的字符串
		String resourceCode = MsgLampEnum.APPROVE_RESULT_REMINDER.getCode();
		String content = uamTemplateService.mergeTemplate(resourceCode, params);
		
		Message message= new Message();
		//消息标题
		String title = MsgLampEnum.APPROVE_RESULT_REMINDER.getName();
		message.setTitle(title);
		//消息类型  
		message.setType(MessageType.SITE);
		/*设置提醒列别*/
		message.setMsgCatagory(MsgCatagoryEnum.RESPON.getCode());
		/*内容*/
		message.setContent(content);
		/*发送人*/
		message.setSenderId(sender);
		/*接收人*/
		uamMessageService.sendMessageByDist(message, recevier);
	}
	
	
}
