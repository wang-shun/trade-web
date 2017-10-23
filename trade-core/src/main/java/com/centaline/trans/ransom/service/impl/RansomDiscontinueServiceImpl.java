package com.centaline.trans.ransom.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.quickQuery.web.vo.DatagridVO;
import com.aist.common.rapidQuery.paramter.ParamterHander;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.common.enums.RansomStopStatusEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
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
	
	@Autowired
	QuickGridService quickGridService;
	
	@Override
	public boolean submitDiscontinueApply(ToRansomCaseVo ransomCase, ProcessInstanceVO vo) {
		//点击中止时即保存中止原因信息
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
		ransomCase.setIsstop(RansomStopStatusEnum.STOPING.getCode());
		ransomListFormService.updateRansomDiscountinue(ransomCase);
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
	public boolean submitDiscontinueAppro(ProcessInstanceVO vo, String examContent, String caseCode, String ransomCode) {
		ToRansomCaseVo ransomCaseVo = new ToRansomCaseVo();
		ransomCaseVo.setCaseCode(caseCode);
		//work_flow
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(propertyUtilsService.getProcessDfId("ransom_suspend"));
		wf.setCaseCode(vo.getCaseCode());
		wf.setInstCode(vo.getProcessInstanceId());
		if(!examContent.equals("reject")) {
			wf.setStatus(WorkFlowStatus.COMPLETE.getCode());
			if(examContent.equals("pass")) {
				ransomCaseVo.setIsstop(RansomStopStatusEnum.STOPED.getCode());
			}else {
				ransomCaseVo.setIsstop(RansomStopStatusEnum.UNSTOP.getCode());
			}
		}else {
			ransomCaseVo.setIsstop(RansomStopStatusEnum.STOPING.getCode());
			wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
		}
		toWorkFlowService.updateWorkFlowByInstCode(wf);
		//run_task
		Map<String, Object> defValsMap = new HashMap<String,Object>();
    	defValsMap.put("ransomAprro", !examContent.equals("reject"));
    	ransomListFormService.updateRansomDiscountinue(ransomCaseVo);
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
	
	@Override
	public Map<String, Object> getSingleRansomTaskInfo(Map<String, Object> paramObj, Boolean isSuspend,
			Boolean isSuspended, Boolean isIgnoreAssignee, String caseCode) {
		String processDfId = propertyUtilsService.getProcessDfId("ransom_suspend");
		String processDfId1 = propertyUtilsService.getProcessDfId("ransom_process");
        if(isSuspend != null && isSuspend) {//查询赎楼中止流程数据
        	paramObj.put("PROCESS_DEFINITION_ID_RANSOM_SUSPEND", processDfId);
        }else if(isSuspend != null && !isSuspend){//查询赎楼流程数据
        	paramObj.put("PROCESS_DEFINITION_ID_RANSOM", processDfId1);
        }else {
        	paramObj.put("PROCESS_DEFINITION_ID_RANSOM_SUSPEND", processDfId);
        	paramObj.put("PROCESS_DEFINITION_ID_RANSOM", processDfId1);
        }
        if(!isIgnoreAssignee) {//如果不忽略【任务责任人】条件
        	paramObj.put("isIgnoreAssignee", "exist");
        }
        if(isSuspended != null && isSuspended) {
        	paramObj.put("isSuspended", true);
        }else if(isSuspended != null && !isSuspended){
        	paramObj.put("isSuspended", false);
        }
        if(caseCode != null) {
        	paramObj.put("caseCode",caseCode);
        }
        SessionUser user = uamSessionService.getSessionUser();
        paramObj.put("sessionUserId", user.getId());
        JQGridParam gp = new JQGridParam();
        gp.setPage(1);
        gp.setRows(1);
        gp.putAll(paramObj);
        gp.setQueryId("queryRansomTaskListItemList");
        Page<Map<String, Object>> page = quickGridService.findPageForSqlServer(gp);
        Map<String, Object> map = new HashMap<>();
		if(page != null && page.hasContent() && page.getContent().size() > 0) {
			map = page.getContent().get(0);
			map.put("hasData", true);
		}else {
			map.put("hasData", false);
		}
		return map;
	}

}
