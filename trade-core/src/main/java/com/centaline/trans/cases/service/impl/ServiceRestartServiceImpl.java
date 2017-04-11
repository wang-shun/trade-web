package com.centaline.trans.cases.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.bizwarn.repository.BizWarnInfoMapper;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ServiceRestartService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.UnlocatedTaskService;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.utils.ConstantsUtil;

@Service
@Transactional(readOnly = true)
public class ServiceRestartServiceImpl implements ServiceRestartService {

	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private ToApproveRecordService toApproveService;
	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private BizWarnInfoMapper bizWarnInfoMapper;
	@Autowired
	private TransplanServiceFacade toTransplanOperateService;// add by zhoujp

	@Override
	@Transactional(readOnly = false)
	public StartProcessInstanceVo restart(ServiceRestartVo vo) {

		ToWorkFlow twf = new ToWorkFlow();
		twf.setBusinessKey(WorkFlowEnum.TMP_BANK_DEFKEY.getCode());
		twf.setCaseCode(vo.getCaseCode());
		toMortgageService.deleteTmpBankProcess(twf);
		toWorkFlowService.deleteWorkFlowByProperty(twf);

		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		ToWorkFlow sameOne = toWorkFlowService
				.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if (sameOne != null) {
			throw new BusinessException("当前重启流程尚未结束！");
		}

		ProcessInstance pi = new ProcessInstance(
				propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART
						.getCode()), vo.getCaseCode());
		StartProcessInstanceVo spv = workFlowManager.startCaseWorkFlow(pi,
				vo.getUserName(), vo.getCaseCode());
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setBizCode(vo.getCaseCode());
		wf.setProcessOwner(vo.getUserId());
		wf.setProcessDefinitionId(propertyUtilsService
				.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()));
		wf.setInstCode(spv.getId());
		toWorkFlowService.insertSelective(wf);
		return spv;
	}

	@Override
	public StartProcessInstanceVo restartAndDeleteSubProcess(ServiceRestartVo vo) {

		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		ToWorkFlow sameOne = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if (sameOne != null) {
			throw new BusinessException("当前重启流程尚未结束！");
		}
		/*
		 * ProcessInstance pi=new
		 * ProcessInstance(propertyUtilsService.getProcessDfId
		 * (WorkFlowEnum.SERVICE_RESTART.getCode()), vo.getCaseCode());
		 * StartProcessInstanceVo spv=workFlowManager.startCaseWorkFlow(pi,
		 * vo.getUserName(),vo.getCaseCode());
		 */
		//相关流程都挂起
		ToWorkFlow zhuwf = new ToWorkFlow();
		zhuwf.setCaseCode(vo.getCaseCode());
		List<ToWorkFlow> zhulcList = toWorkFlowService.queryActiveToWorkFlowByCaseCode(zhuwf);
		for(ToWorkFlow t : zhulcList){
			workFlowManager.activateOrSuspendProcessInstance(t.getInstCode(),false);//案件的相关流程挂起
		}
		ToCase toCase = new ToCase();
		toCase.setCaseProperty(CasePropertyEnum.TPGQ.getCode());
		toCase.setCaseCode(vo.getCaseCode());
		toCaseService.updateByCaseCodeSelective(toCase);//将案件更新成挂起案件

        //打开重启流程
		Map<String, Object> vars = new HashMap<>();
		// 根据案件所在组找主管
		String managerName = toCaseService.getManagerByCaseOwner(vo.getCaseCode());

		vars.put("consultant", vo.getUserName());
		vars.put("Manager", managerName);
		// vars.put("Manager", manager.getUsername());
		StartProcessInstanceVo spv = processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()), vo.getCaseCode(), vars);
		List<TaskVo> tasks = taskService.listTasks(spv.getId(), false,vo.getUserName()).getData();
		if (tasks != null && !tasks.isEmpty()) {
			spv.setActiveTaskId(tasks.get(0).getId() + "");
		}
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setBizCode(vo.getCaseCode());
		wf.setProcessOwner(vo.getUserId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()));
		wf.setInstCode(spv.getId());
		toWorkFlowService.insertSelective(wf);
		return spv;
	}

	/****
	 * 删除该案件下的所有贷款类型
	 */

	private void deleteMortFlowByCaseCode(String caseCode) {

		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setCaseCode(caseCode);
		List<ToWorkFlow> wordkFlowDBList = toWorkFlowService.getMortToWorkFlowByCaseCode(workFlow);

		for (ToWorkFlow workFlowDB : wordkFlowDBList) {
			workFlowManager.deleteProcess(workFlowDB.getInstCode());               //清除流程 activiti接口
			toWorkFlowService.deleteWorkFlowByInstCode(workFlowDB.getInstCode());  //修改数据状态
		}
	}

	@Transactional(readOnly = false)
	@Override
	public boolean apply(ServiceRestartVo vo) {
		workFlowManager.submitTask(null, vo.getTaskId(), vo.getInstCode(),
				null, vo.getCaseCode());
		ToApproveRecord record = new ToApproveRecord();
		record.setApproveType("7");
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(vo.getPartCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveService.insertToApproveRecord(record);
		return true;
	}

	@Transactional(readOnly = false)
	@Override
	public boolean approve(ServiceRestartVo vo) {
		//打开挂起的流程
		ToWorkFlow zhuwf = new ToWorkFlow();
		zhuwf.setCaseCode(vo.getCaseCode());
		List<ToWorkFlow> zhulcList = toWorkFlowService.queryActiveToWorkFlowByCaseCode(zhuwf);
		for(ToWorkFlow t : zhulcList){
			if(!WorkFlowEnum.SERVICE_RESTART.getCode().equals(t.getBusinessKey())){
				workFlowManager.activateOrSuspendProcessInstance(t.getInstCode(),true);//案件的相关流程挂起
			}
		}
		ToCase toCase = new ToCase();
		toCase.setCaseProperty(CasePropertyEnum.TPZT.getCode());
		toCase.setCaseCode(vo.getCaseCode());
		toCaseService.updateByCaseCodeSelective(toCase);//将案件更新在途起案件
		List<RestVariable> vs = new ArrayList<>();
		RestVariable v = new RestVariable("is_approved", vo.getIsApproved());
		vs.add(v);
		
		workFlowManager.submitTask(vs, vo.getTaskId(), vo.getInstCode(), null,vo.getCaseCode());
		
		ToApproveRecord record = new ToApproveRecord();
		record.setApproveType("7");
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(vo.getPartCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveService.insertToApproveRecord(record);
		// add by zhoujp
		if (vo.getIsApproved()) {
			
			/*
			 * @author: zhuody
			 * @date:2017-04-10
			 * @desc:删除交易顾问派单流程
			 * */		
			ToWorkFlow loanerProcessWf = new ToWorkFlow();
			loanerProcessWf.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
			loanerProcessWf.setCaseCode(vo.getCaseCode());		
			toMortgageService.deleteTmpBankProcess(loanerProcessWf);
			toWorkFlowService.deleteWorkFlowByProperty(loanerProcessWf);
			
			
			
			/* 删除临时银行流程相关 */
			ToWorkFlow twf = new ToWorkFlow();

			twf.setBusinessKey(WorkFlowEnum.TMP_BANK_DEFKEY.getCode());
			twf.setCaseCode(vo.getCaseCode());
			toMortgageService.deleteTmpBankProcess(twf);
			//将T_TO_WORKFLOW 和 T_HI_WORKFLOW 中的status设置为2 非正常结束
			toWorkFlowService.deleteWorkFlowByProperty(twf);
			

			ToWorkFlow wf = new ToWorkFlow();
			wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
			wf.setCaseCode(vo.getCaseCode());
			deleteMortFlowByCaseCode(vo.getCaseCode());
		}
		// end

		if (vo.getIsApproved()) {
			doApproved(vo);
		}
		// 如果流程重启申请审批通过的话将交易计划表的数据转移到交易计划历史表并删除交易计划表add by zhoujp
		if (vo.getIsApproved()) {
			toTransplanOperateService.processRestartOrResetOperate(vo.getCaseCode(), ConstantsUtil.PROCESS_RESTART);
		}

		// 如果流程重启申请审批通过的话，就删除对应的预警信息
		if (vo.getIsApproved()) {
			bizWarnInfoMapper.deleteByCaseCode(vo.getCaseCode());
		}

		// 流程重启更改掉案件临时银行的状态
		ToMortgage toMortgage = toMortgageService.getMortgageByCaseCode(vo.getCaseCode());
		if (toMortgage != null) {
			toMortgageService.updateTmpBankStatus(vo.getCaseCode());
		}
		return true;
	}

	/**
	 * 审批同意操作
	 * 
	 * @param vo
	 */
	private void doApproved(ServiceRestartVo vo) {
		// toTransPlanService.deleteTransPlansByCaseCode(vo.getCaseCode());
		// 删除原主流程 更新原主流程记录
		ToWorkFlow t = new ToWorkFlow();
		t.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		t.setCaseCode(vo.getCaseCode());
		ToWorkFlow mainflow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(t);
		if (mainflow != null) {
			try {
				unlocatedTaskService.deleteByInstCode(mainflow.getInstCode());
				workFlowManager.deleteProcess(mainflow.getInstCode());
			} catch (WorkFlowException e) {
				if (!e.getMessage().contains("statusCode[404]"))
					throw e;
			}
			mainflow.setStatus(WorkFlowStatus.TERMINATE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(mainflow);
		}
		// 启动新的主流程并记录流程表
		ToCase cas = toCaseService.findToCaseByCaseCode(vo.getCaseCode());
		cas.setCaseProperty(CasePropertyEnum.TPZT.getCode());
		cas.setStatus(CaseStatusEnum.YFD.getCode());
		// 更新Case表
		toCaseService.updateByCaseCodeSelective(cas);
		User u = uamUserOrgService.getUserById(cas.getLeadingProcessId());
		// 无效业务表单
		toWorkFlowService.inActiveForm(vo.getCaseCode());
		// 更新当前流程为结束
		ToWorkFlow tf = toWorkFlowService.queryWorkFlowByInstCode(vo.getInstCode());
		tf.setStatus(WorkFlowStatus.COMPLETE.getCode());
		toWorkFlowService.updateByPrimaryKeySelective(tf);

		/*
		 * ProcessInstance process = new ProcessInstance();
		 * process.setBusinessKey(vo.getCaseCode());
		 * process.setProcessDefinitionId
		 * (propertyUtilsService.getProcessDfId(WorkFlowEnum
		 * .WBUSSKEY.getCode()));
		 */
		/* 流程引擎相关 */
		Map<String, Object> defValsMap = propertyUtilsService
				.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());
		/*
		 * List<RestVariable> variables = new ArrayList<RestVariable>();
		 * Iterator it = defValsMap.keySet().iterator(); while (it.hasNext()) {
		 * String key = it.next().toString(); RestVariable restVariable = new
		 * RestVariable(); restVariable.setName(key);
		 * restVariable.setValue(defValsMap.get(key));
		 * variables.add(restVariable); } process.setVariables(variables);
		 * StartProcessInstanceVo spi=workFlowManager.startCaseWorkFlow(process,
		 * u.getUsername(),vo.getCaseCode());
		 * 
		 * Map<String, Object> defValsMap =
		 * propertyUtilsService.getProcessDefVals
		 * (WorkFlowEnum.WBUSSKEY.getCode()); User user =
		 * uamUserOrgService.getUserById(userId);
		 */
		defValsMap.put("caseOwner", u.getUsername());
		StartProcessInstanceVo pIVo = processInstanceService
				.startWorkFlowByDfId(propertyUtilsService
						.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()), vo
						.getCaseCode(), defValsMap);

		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setBizCode(vo.getCaseCode());
		wf.setProcessOwner(cas.getLeadingProcessId());
		wf.setProcessDefinitionId(propertyUtilsService
				.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
		wf.setInstCode(pIVo.getId());
		toWorkFlowService.insertSelective(wf);
	}

	@Override
	public boolean restartCheckout(ServiceRestartVo vo, String userId) {
		
		if(null == userId || "".equals(userId)){
			throw new BusinessException("用户未登录！");	
		}		
		
		if(!"8a8493d553ad8759015404ac92750d43".equals(userId)){
			//案件详情页面未刷新时判断时候可以流程重启
			String caseCode = "";
			
			if(null != vo){
				caseCode = vo.getCaseCode() == null ? "":vo.getCaseCode();
			}
			ToCase  toCase = toCaseService.findToCaseByCaseCode(caseCode);
			if(null != toCase){
				String status = toCase.getStatus();
				if(null != status && !"".equals(status)){
					if("30001004".equals(status)){
						return false;
					}
				}			
			}
		}
		return true;
		
	}



}
