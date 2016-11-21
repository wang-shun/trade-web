package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.EditCaseDetailService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.EditCaseDetailVO;
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
@RequestMapping(value="/task/CaseClose")
public class CaseCloseController {

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	/*发送消息*/
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;
	@Autowired(required=true)
	private UamTemplateService uamTemplateService;
	@Autowired
	private ToPropertyInfoService toPropertyInfoService;
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private LoanlostApproveService loanlostApproveService;
	@Autowired
	private EditCaseDetailService editCaseDetailService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@RequestMapping("process")
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source){
		SessionUser user=uamSessionService.getSessionUser();
		request.setAttribute("source", source);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("approveType", "3");
		request.setAttribute("operator", user != null ? user.getId():"");
		toAccesoryListService.getAccesoryListCaseClose(request, caseCode);
		EditCaseDetailVO editCaseDetailVO=editCaseDetailService.queryCaseDetai(caseCode);
		request.setAttribute("editCaseDetailVO", editCaseDetailVO);
		request.setAttribute("loanReq", editCaseDetailVO.getLoanReq());
		return "task/taskCaseClose";
	}

	
	
	
	@RequestMapping(value="saveCaseClose")
	@ResponseBody
	public Boolean saveCaseDetai(HttpServletRequest request, EditCaseDetailVO editCaseDetailVO) {
		editCaseDetailService.saveCaseDetai(editCaseDetailVO);
		return true;
	}
	
	@RequestMapping(value="submitCaseClose")
	@ResponseBody
	public boolean submitCaseClose(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO,EditCaseDetailVO editCaseDetailVO) {
		editCaseDetailService.saveCaseDetai(editCaseDetailVO);
		ToApproveRecord toApproveRecord = saveToApproveRecord(processInstanceVO, loanlostApproveVO, "", "结档归案提交。");
		/*发送消息*/
		sendMessage(processInstanceVO, toApproveRecord.getContent(), toApproveRecord.getApproveType());
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();

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
