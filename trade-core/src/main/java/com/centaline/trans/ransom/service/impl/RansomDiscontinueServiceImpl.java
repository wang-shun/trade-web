package com.centaline.trans.ransom.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.rapidQuery.paramter.ParamterHander;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.common.enums.RansomStatusEnum;
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
import com.centaline.trans.ransom.service.RansomService;
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
	@Autowired
	RansomService ransomService;

	/**
	 * 提交赎楼中止申请
	 */
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
	
	@Override
	public boolean isCanSuspend(ServletRequest request, String ransomCode)throws Exception {
		ToRansomCaseVo ranCase = ransomListFormService.getRansomCase(null, ransomCode);
		if(ranCase != null && !RansomStopStatusEnum.STOPING.getCode().equals(ranCase.getIsstop())) {
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean aprroSubmit(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String examContent, String remark, String caseCode, String ransomCode)
			throws Exception {
		//通过：删除 [赎楼流程]
		if("pass".equals(examContent)) {
			//或者直接使用processInstanceVO.getProcessInstanceId()
			Map<String, Object> task = getSingleRansomTaskInfo(request, false, true, true, caseCode);
			if((boolean)task.get("hasData")) {
				processInstanceService.deleteProcess((String) task.get("INST_CODE"));
				ransomService.deleteRansomApplyByRansomCode((String) task.get("RANSOM_CODE"));
			}
		}
		//不通过：重启 [赎楼流程]
		else if("noPass".equals(examContent)) {
			Map<String, Object> task = getSingleRansomTaskInfo(request, false, true, true, caseCode);
			if((boolean)task.get("hasData")) {
				processInstanceService.activateOrSuspendProcessInstance((String) task.get("INST_CODE"), true);
			}
		}
		//保存审批记录
		saveToApproveRecord(processInstanceVO, loanlostApproveVO, examContent, remark);
		//赎楼中止 流程下一步
		return submitDiscontinueAppro(processInstanceVO, examContent, caseCode, ransomCode);
	}
	
	@Override
	public boolean submitDiscontinue(ToRansomCaseVo ransomCase, HttpServletRequest request,
			ProcessInstanceVO processInstanceVO, String caseCode, String ransomCode) throws Exception {
		Map<String, Object> task = null;
		String taskId = null;
		ransomCase.setRansomCode(ransomCode);
		ransomCase.setCaseCode(caseCode);
		//①挂起对应的【赎楼流程】(如果有)②开启一个【赎楼中止流程】(如果没有)③给processInstanceVO赋值
		task = getSingleRansomTaskInfo(request, true, false, false, caseCode);//查询中止流程
		if((boolean)task.get("hasData")) {
			//如果存在对应中止流程，那么判断责任人
			String assignee = (String) task.get("ASSIGNEE");
			SessionUser user = uamSessionService.getSessionUser();
			if(assignee == null || !assignee.equals(user.getUsername())) {
				return false;
			}
		}else {
			//如果不存在赎楼中止流程，则继续判断赎楼流程
			task = null;
			task = getSingleRansomTaskInfo(request, false, false, true, caseCode);//查询赎楼流程
			if((boolean)task.get("hasData")) {
				//如果有赎楼，无相应中止，那么表示第一次申请中止，则需要做①、②、③
				taskId = (String) task.get("INST_CODE");
				processInstanceService.activateOrSuspendProcessInstance(taskId, false);//①
			}else {
				//如果中止和赎楼都不存在，返回错误
				return false;
			}
			startDiscontinueTask(caseCode, ransomCode);//②
			task = null;
			task = getSingleRansomTaskInfo(request, true, false, false, caseCode);
			processInstanceVO.setTaskId((String)task.get("ID"));//③
			processInstanceVO.setProcessInstanceId((String)task.get("INST_CODE"));
			processInstanceVO.setPartCode((String)task.get("PART_CODE"));
			processInstanceVO.setCaseCode(caseCode);
			processInstanceVO.setBusinessKey(ransomCode);
		}
		return submitDiscontinueApply(ransomCase, processInstanceVO);
	}

	/**
	 * 保存审批记录
	 * @param processInstanceVO
	 * @param loanlostApproveVO
	 * @param loanLost
	 * @param loanLost_response
	 */
	private ToApproveRecord saveToApproveRecord(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
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
	
	private boolean submitDiscontinueAppro(ProcessInstanceVO vo, String examContent, String caseCode, String ransomCode) {
		ToRansomCaseVo ransomCaseVo = new ToRansomCaseVo();
		ransomCaseVo.setCaseCode(caseCode);
		ransomCaseVo.setRansomCode(ransomCode);
		//work_flow
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(propertyUtilsService.getProcessDfId("ransom_suspend"));
		wf.setCaseCode(vo.getCaseCode());
		wf.setInstCode(vo.getProcessInstanceId());
		if(!examContent.equals("reject")) {
			wf.setStatus(WorkFlowStatus.COMPLETE.getCode());
			if(examContent.equals("pass")) {
				ransomCaseVo.setIsstop(RansomStopStatusEnum.STOPED.getCode());
				ransomCaseVo.setRansomStatus(RansomStatusEnum.STOPED.getCode());
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
	
	private boolean startDiscontinueTask(String caseCode, String ransomCode) {
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
	public Map<String, Object> getSingleRansomTaskInfo(HttpServletRequest request, Boolean isSuspend,
			Boolean isSuspended, Boolean isIgnoreAssignee, String caseCode) {
		Map<String, String[]> paramMap = ParamterHander.getParameters(request);
        Map<String, Object> paramObj = new HashMap<String, Object>();
        ParamterHander.mergeParamter(paramMap, paramObj);
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
        	paramObj.put("isSuspended", "true");
        }else if(isSuspended != null && !isSuspended){
        	paramObj.put("isSuspended", "false");
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
