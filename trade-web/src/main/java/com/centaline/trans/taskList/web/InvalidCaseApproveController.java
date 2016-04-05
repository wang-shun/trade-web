package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Controller
@RequestMapping(value="/task/invalidCaseApprove")
public class InvalidCaseApproveController {

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
	
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@RequestMapping(value="invalidCaseApprove")
	@ResponseBody
	public Boolean invalidCaseApprove(HttpServletRequest request, ProcessInstanceVO processInstanceVO,LoanlostApproveVO loanlostApproveVO,
			String InvalidCaseApprove, String InvalidCaseApprove_response) {
		
		ToApproveRecord toApproveRecord = saveToApproveRecord(processInstanceVO, loanlostApproveVO, InvalidCaseApprove, InvalidCaseApprove_response);
		/*发送提醒*/
		sendMessage(processInstanceVO, toApproveRecord.getContent(), toApproveRecord.getApproveType());
		
		/*流程引擎相关*/
		RestVariable restVariable = new RestVariable();
		List<RestVariable> variables = new ArrayList<RestVariable>();
		restVariable.setName("InvalidCaseApprove");
		restVariable.setValue(InvalidCaseApprove.equals("true"));
		variables.add(restVariable);
		if(!StringUtils.isBlank(InvalidCaseApprove_response)) {
			RestVariable restVariable1 = new RestVariable();
			restVariable1.setName("InvalidCaseApprove_response");
			restVariable1.setValue(InvalidCaseApprove_response);
			variables.add(restVariable1);
		}
		
		
		/**
		 * 功能：如果审批结果为审批通过则需要将 T_TO_CASE 表中的 CASE_PROPERTY 进行修改, 如果为审批不通过则不进行修改
		 * 作者：zhangxb16
		 */
		if(InvalidCaseApprove.equals("true")){ // 审批通过 
			ToCase tc=new ToCase();
			tc.setCaseCode(processInstanceVO.getCaseCode());
			tc.setCaseProperty("30003001");  // 无效
			int updateApprove=toCaseService.updateByCaseCodeSelective(tc);
			
			ToWorkFlow t=new ToWorkFlow();
			t.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
			t.setCaseCode(processInstanceVO.getCaseCode());
			ToWorkFlow mainflow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(t);
			mainflow.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(mainflow);
		}else{  // 审批不通过
			ToCase tc=new ToCase();
			tc.setCaseCode(processInstanceVO.getCaseCode());
			tc.setCaseProperty("30003003");  // 在途
			int updateApprove=toCaseService.updateByCaseCodeSelective(tc);
		}

		ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());	
		boolean wfm=workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), processInstanceVO.getCaseCode());
		
		// add zhangxb16 2016-2-19
		
		
		return wfm;
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
		boolean b = loanLost.equals("true");
		toApproveRecord.setContent((b?"通过":"不通过") + (",审批意见为"+loanLost_response));
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
		params.put("part_name", "无效审批");
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
		for(String tar : list) {
			if(tar.equals(senderId)) {/*不给自己发送提醒*/
				continue;
			}
			/*发送*/
			uamMessageService.sendMessageByDist(message, tar);
		}
	}
	
}
