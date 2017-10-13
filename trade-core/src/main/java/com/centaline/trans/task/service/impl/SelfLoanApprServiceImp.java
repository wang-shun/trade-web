package com.centaline.trans.task.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.EventTypeEnum;
import com.centaline.trans.common.enums.MessageEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.repository.ToWorkFlowMapper;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.ExecutionVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.task.entity.ActRuEventSubScr;
import com.centaline.trans.task.repository.ActRuEventSubScrMapper;
import com.centaline.trans.task.repository.ToMortgageTosaveMapper;
import com.centaline.trans.task.service.SelfLoanApprService;
import com.centaline.trans.task.vo.MortgageToSaveVO;
import com.centaline.trans.task.vo.ToAppRecordInfoVO;
import com.centaline.trans.utils.ConstantsUtil;
/**
 * 
 * @author wblujian
 *
 */
@Service
public class SelfLoanApprServiceImp implements SelfLoanApprService {

	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired
	private ActRuEventSubScrMapper actRuEventSubScrMapper;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired
	private WorkFlowEngine engine;//该处使用engine 否则无法进行访问流程引擎平台
	
	@Override
	public boolean saveAndSubmit(ToAppRecordInfoVO vo) {
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable  restVariable = new RestVariable();
		boolean b = false;
		if(vo.getResult().equals("0")){ //0通过1驳回
			restVariable.setName("approval");
			restVariable.setValue(false);
			variables.add(restVariable);
			 b = workFlowManager.submitTask(variables, vo.getTaskId(), vo.getProcessInstanceId(), null, vo.getCaseCode());
		}else{
			restVariable.setName("approval");
			restVariable.setValue(true);
			variables.add(restVariable);
			b = workFlowManager.submitTask(variables, vo.getTaskId(), vo.getProcessInstanceId(), null, vo.getCaseCode());
			return b;
		}
		ToWorkFlow workF = toWorkFlowService.queryWorkFlowByInstCode(vo.getProcessInstanceId());
		if(workF!=null){
			vo.setProcessDefinitionId(workF.getProcessDefinitionId());
		}
		updateProcess(vo);
		return false;
	}
	
	
	public void updateProcess(ToAppRecordInfoVO vo) {
		//获取流程信息
				ToWorkFlow wf = new ToWorkFlow();
				wf.setCaseCode(vo.getCaseCode());
				wf.setBusinessKey(WorkFlowEnum.LOANANDASSE_PROCESS.getCode());
				ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
				if (wordkFlowDB != null) {
					// 发送消息
					ActRuEventSubScr event = new ActRuEventSubScr();
					event.setEventType(MessageEnum.CCAI_UPDATED_MSG.getEventType());
					event.setEventName(MessageEnum.CCAI_UPDATED_MSG.getName());
					event.setProcInstId(wordkFlowDB.getInstCode());
					event.setActivityId(EventTypeEnum.CCAI_UPDATED_MSG_EVENT_CATCH.getName());
					ExecuteAction action = new ExecuteAction();
					action.setAction(EventTypeEnum.CCAI_UPDATED_MSG_EVENT_CATCH.getEventType());
					action.setMessageName(MessageEnum.CCAI_UPDATED_MSG.getName());
					List<ActRuEventSubScr> subScrs = actRuEventSubScrMapper.listBySelective(event);
					if (CollectionUtils.isNotEmpty(subScrs)) {
						//设置流程引擎登录用户 否则无法访问REST接口
						User user = uamUserOrgService.getUserById(wordkFlowDB.getProcessOwner());
						engine.setAuthUserName(user.getUsername());

						//更新案件参与人信息 及 案件所有人信息
						//action.setVariables(updateFlowParticipants(vo.getCaseCode(), wordkFlowDB.getPkid()));

						//调用REST接口发送消息
						action.setExecutionId(subScrs.get(0).getExecutionId());
						engine.RESTfulWorkFlow(WorkFlowConstant.PUT_EXECUTE_KEY, ExecutionVo.class, action);
					}
			
				}
	}	
}
