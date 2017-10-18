package com.centaline.trans.ransom.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.service.RansomDiscontinueService;
import com.centaline.trans.ransom.service.RansomListFormService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Service
public class RansomDiscontinueServiceImpl implements RansomDiscontinueService {
	
	@Autowired
	private LoanlostApproveService loanlostApproveService;

	@Autowired
	private RansomListFormService ransomListFormService;

	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private PropertyUtilsService propertyUtilsService;
	
	@Autowired
	private ProcessInstanceService processInstanceService;
	
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	ToCaseInfoService toCaseInfoService;
	
	@Override
	public boolean submitDiscontinueApply(ToRansomCaseVo ransomCase, ProcessInstanceVO vo) {
		//点击中止时即保存中止原因信息
		ToRansomCaseVo ransomCaseVo = ransomListFormService.getRansomCase(ransomCase.getCaseCode(), ransomCase.getRansomCode());
		if(StringUtils.isBlank(ransomCaseVo.getCaseCode())) {
			ransomListFormService.addRansomDetail(ransomCase);
		}else {
			ransomListFormService.updateRansomDiscountinue(ransomCase);
		}
		//work_flow
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(propertyUtilsService.getProcessDfId("ransom_suspend"));
		wf.setCaseCode(vo.getCaseCode());
		wf.setInstCode(vo.getProcessInstanceId());
		wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.updateWorkFlowByInstCode(wf);
		//run_task
		Map<String, Object> defValsMap = new HashMap<String,Object>();
    	taskService.submitTask(vo.getTaskId(),defValsMap);
		return true;
	}

	/**
	 * 保存审批记录
	 * @param processInstanceVO
	 * @param loanlostApproveVO
	 * @param loanLost
	 * @param loanLost_response
	 */
	@Override
	public ToApproveRecord saveToApproveRecord(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
			String loanLost, String loanLost_response) {
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
		toApproveRecord.setPartCode(processInstanceVO.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
		toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
		if(loanLost.equals("pass")) {
			toApproveRecord.setContent("通过,审批意见为"+loanLost_response);
		}else if(loanLost.equals("noPass")){
			toApproveRecord.setContent("不通过,审批意见为"+loanLost_response);
		}else {
			toApproveRecord.setContent("驳回,审批意见为"+loanLost_response);
		}
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
		loanlostApproveService.saveLoanlostApprove(toApproveRecord);
		return toApproveRecord;
	}
	
	@Override
	public boolean submitDiscontinueAppro(ProcessInstanceVO vo, String examContent) {
		//work_flow
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(propertyUtilsService.getProcessDfId("ransom_suspend"));
		wf.setCaseCode(vo.getCaseCode());
		wf.setInstCode(vo.getProcessInstanceId());
		if(!examContent.equals("reject")) {
			wf.setStatus(WorkFlowStatus.COMPLETE.getCode());
		}else {
			wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
		}
		toWorkFlowService.updateWorkFlowByInstCode(wf);
		//run_task
		Map<String, Object> defValsMap = new HashMap<String,Object>();
    	defValsMap.put("ransomAprro", !examContent.equals("reject"));
    	taskService.submitTask(vo.getTaskId(),defValsMap);
    	return true;
	}
	
	@Override
	public boolean startDiscontinueTask(String caseCode, String ransomCode) {
		SessionUser user = uamSessionService.getSessionUser();
		Map<String,Object> defValsMap = new HashMap<String,Object>();
		//通过case_info表获取权证经理
    	String manager = toCaseInfoService.getCaseManager(caseCode);
    	//为流程中所用到的用户参数赋值
		defValsMap.put("sessionUser", user.getUsername());
		defValsMap.put("manager", manager);
		String processDfId = propertyUtilsService.getProcessDfId("ransom_suspend");
		StartProcessInstanceVo pVo = processInstanceService.startWorkFlowByDfId(processDfId, ransomCode, defValsMap);
		//work_flow
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey("ransom_suspend");
		wf.setCaseCode(caseCode);
		wf.setBizCode(ransomCode);
		wf.setProcessOwner(user.getId());
		wf.setProcessDefinitionId(processDfId);
		wf.setInstCode(pVo.getId());
		wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(wf);
		return true;
	}

}
