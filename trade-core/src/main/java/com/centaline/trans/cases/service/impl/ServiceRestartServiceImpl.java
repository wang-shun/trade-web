package com.centaline.trans.cases.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.alibaba.druid.util.StringUtils;
import com.centaline.trans.award.service.TsAwardCaseCentalService;
import com.centaline.trans.bizwarn.repository.BizWarnInfoMapper;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.repository.TsCaseEfficientMapper;
import com.centaline.trans.cases.service.ServiceRestartService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.comment.repository.ToCaseCommentMapper;
import com.centaline.trans.common.enums.CaseParticipantEnum;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseRestartPartCodeEnnum;
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
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ActRuTaskService;
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
	@Autowired
	private ActRuTaskService actRuTaskService;
	@Autowired
	private QuickGridService quickGridService;
	@Autowired
	private TsCaseEfficientMapper tsCaseEfficientMapper;

	@Autowired
	private TsAwardCaseCentalService tsAwardCaseCentalService;
	@Autowired
	private ToCaseCommentMapper toCaseCommentMapper;
	@Autowired
	private ToCaseInfoService toCaseInfoService;
	@Autowired
	private ToCaseParticipantMapper participantMapper;


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
		ToWorkFlow sameOne = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if (sameOne != null) {
			throw new BusinessException("当前重启流程尚未结束！");
		}

		ProcessInstance pi = new ProcessInstance(
				propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()), vo.getCaseCode());
		StartProcessInstanceVo spv = workFlowManager.startCaseWorkFlow(pi, vo.getUserName(), vo.getCaseCode());
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setBizCode(vo.getCaseCode());
		wf.setProcessOwner(vo.getUserId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()));
		wf.setInstCode(spv.getId());
		toWorkFlowService.insertSelective(wf);
		return spv;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public StartProcessInstanceVo restartAndDeleteSubProcess(ServiceRestartVo vo) {

		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		ToWorkFlow sameOne = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if (sameOne != null) {
			throw new BusinessException("当前重启流程尚未结束！");
		}
		
		// 相关流程都挂起,只关注主流程
		/*List<TaskVo> taskVos = actRuTaskService.getRuTaskByBizCode(vo.getCaseCode());
		for(TaskVo task : taskVos){
			if(!WorkFlowEnum.SERVICE_RESTART.getCode().equals(task.getBusiness_key())){
				if(!task.getSuspended()){
					workFlowManager.activateOrSuspendProcessInstance(task.getInstCode(), false);// 案件的相关流程挂起
				}
			}
		}	*/
		List<TaskVo> taskVos = actRuTaskService.getRuTaskByBizCode(vo.getCaseCode());
		/**
		 * 同实例id不同任务去重复
		 * @author wbcaiyx
		 */
		Map<String,Boolean> taskInstCode = new HashMap<>();
		for(TaskVo task : taskVos){
			if(!WorkFlowEnum.SERVICE_RESTART.getCode().equals(task.getBusiness_key())){
				if(!taskInstCode.containsKey(task.getInstCode())){
					taskInstCode.put(task.getInstCode(), task.getSuspended());
				}
			}
		}
		for(String instCode : taskInstCode.keySet()){
			if (!StringUtils.isEmpty(instCode)) {
				if(!taskInstCode.get(instCode)){
					workFlowManager.activateOrSuspendProcessInstance(instCode, false);// 案件的相关流程挂起
				}
			}
		}		
	
		Map<String, Object> vars = new HashMap<>();
		// 根据案件所在组找主管
		String manager = toCaseInfoService.getCaseManager(vo.getCaseCode());// 根据案件所在组找主管
		vars.put("consultant", vo.getUserName());
		vars.put("manager", manager);

		// 打开重启流程
		StartProcessInstanceVo spv = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()), vo.getCaseCode(), vars);
		List<TaskVo> tasks = taskService.listTasks(spv.getId(), false, vo.getUserName()).getData();
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

	/**
	 * 删除该案件下的所有贷款流程，并更新流程状态
	 * @param caseCode
	 */
	private void deleteMortFlowByCaseCode(String caseCode) {

		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setCaseCode(caseCode);
		List<ToWorkFlow> wordkFlowDBList = toWorkFlowService.getMortToWorkFlowByCaseCode(workFlow);

		for (ToWorkFlow workFlowDB : wordkFlowDBList) {
			// 清除流程
			workFlowManager.deleteProcess(workFlowDB.getInstCode());
			//修改workflow状态：非正常结束2						
			toWorkFlowService.deleteWorkFlowByInstCode(workFlowDB.getInstCode()); // 修改数据状态
		}
	}

	@Transactional(readOnly = false)
	@Override
	public boolean apply(ServiceRestartVo vo) {
		taskService.submitTask(vo.getTaskId());
		
		ToApproveRecord record = new ToApproveRecord();
		record.setApproveType("14");//案件重启申请
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(CaseRestartPartCodeEnnum.CASE_RESTART_APPLY.getCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveService.insertToApproveRecord(record);
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public boolean approve(ServiceRestartVo vo) {

		/**
		 * 案件重启流程结束
		 */
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setInstCode(vo.getInstCode());
		wf.setStatus(WorkFlowStatus.COMPLETE.getCode());
		toWorkFlowService.updateWorkFlowByInstCode(wf);

		List<RestVariable> vs = new ArrayList<>();
		workFlowManager.submitTask(vs, vo.getTaskId(), vo.getInstCode(), null, vo.getCaseCode());

		
		/**
		 * 添加重启流程审批记录
		 */
		ToApproveRecord record = new ToApproveRecord();
		record.setApproveType("15");//案件重启审批
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(vo.getPartCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveService.insertToApproveRecord(record);
		
		if (vo.getIsApproved()) {
			//删除贷款流程及更新workflow状态
			deleteMortFlowByCaseCode(vo.getCaseCode());
			//同意后的处理
			doApproved(vo);
			/**
			 * 如果流程重启申请审批通过的话将交易计划表的数据转移到交易计划历史表并删除交易计划表
			 * 不保留，移结束全部删除 计划时间
			 * @author wbcaiyx
			 * @date 2017/10/18
			 */
			toTransplanOperateService.processRestartOrResetOperate(vo.getCaseCode(), ConstantsUtil.PROCESS_RESTART);
			/**
			 * 如果流程重启申请审批通过的话，就删除对应的预警信息
			 * 保留
			 */
			bizWarnInfoMapper.deleteByCaseCode(vo.getCaseCode());
			
			//删除 案件备注
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("caseCode", vo.getCaseCode());
			toCaseCommentMapper.deleteByCasecode(params);
		}else{
			/**
			 * 审批未通过,恢复挂起的流程，只关注主流程相关的(主流程/贷款)
			 */
			/*List<TaskVo> taskVos = actRuTaskService.getRuTaskByBizCode(vo.getCaseCode());
			for(TaskVo task : taskVos){
				if(!WorkFlowEnum.SERVICE_RESTART.getCode().equals(task.getBusiness_key())){
					if(task.getSuspended()){
						workFlowManager.activateOrSuspendProcessInstance(task.getInstCode(), true);// 案件的相关流程挂起
					}
				}
			}	*/
			
			List<TaskVo> taskVos = actRuTaskService.getRuTaskByBizCode(vo.getCaseCode());
			/**
			 * 同实例id不同任务去重复
			 * @author wbcaiyx
			 */
			Map<String,Boolean> taskInstCode = new HashMap<>();
			for(TaskVo task : taskVos){
				if(!WorkFlowEnum.SERVICE_RESTART.getCode().equals(task.getBusiness_key())){
					if(!taskInstCode.containsKey(task.getInstCode())){
						taskInstCode.put(task.getInstCode(), task.getSuspended());
					}
				}
			}
			for(String instCode : taskInstCode.keySet()){
				if (!StringUtils.isEmpty(instCode)) {
					if(taskInstCode.get(instCode)){
						workFlowManager.activateOrSuspendProcessInstance(instCode, true);
					}
				}
			}	
		}

		return true;
	}

	/**
	 * 审批同意操作
	 * 
	 * @param vo
	 */
	private void doApproved(ServiceRestartVo vo) {
		
		/**
		 * 删除原主流程 更新workflow状态
		 */
		ToWorkFlow t = new ToWorkFlow();
		t.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		t.setCaseCode(vo.getCaseCode());
		ToWorkFlow mainflow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(t);
		if (mainflow != null) {
			try {
				workFlowManager.deleteProcess(mainflow.getInstCode());
			} catch (WorkFlowException e) {
				if (!e.getMessage().contains("statusCode[404]"))
					throw e;
			}
			//更新状态为非正常结束
			mainflow.setStatus(WorkFlowStatus.TERMINATE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(mainflow);
		}
		
		/**
		 * Case表初始化
		 */
		ToCase cas = toCaseService.findToCaseByCaseCode(vo.getCaseCode());
		cas.setCaseProperty(CasePropertyEnum.TPZT.getCode());
		cas.setStatus(CaseStatusEnum.WJD.getCode());
		toCaseService.updateByCaseCodeSelective(cas);

		/**
		 * 原案件相关业务表单无效处理
		 */
		toWorkFlowService.inActiveForm(vo.getCaseCode());
		
		/**
		 * 主流程重启
		 */
		Map<String, Object> vals = new HashMap<String,Object>();
		List<ToCaseParticipant> caseParticipants = participantMapper.selectByCaseCode(vo.getCaseCode());
		ToCaseParticipant owner = null;
		for(ToCaseParticipant part : caseParticipants){
			vals.put(part.getPosition(), part.getUserName());
			//有贷款权证 则贷款权证是案件拥有者 否则为过户权证
			if(owner == null && CaseParticipantEnum.WARRANT.getCode().equals(part.getPosition())){
				owner = part;
			}else if(CaseParticipantEnum.LOAN.getCode().equals(part.getPosition())){
				owner = part;
			}
		}
		if(owner!=null){
			//接单跟进人员
			vals.put("receiver",owner.getUserName());
			//权证经理环节
			vals.put("manager",owner.getGrpMgrUsername());
		}
		StartProcessInstanceVo pIVo = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()), vo.getCaseCode(), vals);

		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setBizCode(vo.getCaseCode());
		wf.setProcessOwner(cas.getLeadingProcessId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
		wf.setInstCode(pIVo.getId());
		toWorkFlowService.insertSelective(wf);
	}

	@Override
	public AjaxResponse<StartProcessInstanceVo> restartCheckout(ServiceRestartVo vo, String userJob) {
		AjaxResponse<StartProcessInstanceVo> result = new AjaxResponse<StartProcessInstanceVo>();
		//已过户案件不可重启
		String caseCode = "";
		if(vo != null){
			caseCode = vo.getCaseCode() == null ? "" : vo.getCaseCode();
		}
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		if(toCase != null){
			if(CaseStatusEnum.YGH.getCode().equals(toCase.getStatus())
					||CaseStatusEnum.YJY.getCode().equals(toCase.getStatus())
					||CaseStatusEnum.YLTZ.getCode().equals(toCase.getStatus())){
				result.setSuccess(false);
				result.setMessage("此案件已过户，不能重启流程!");
			}
		}
		ToWorkFlow wfl = new ToWorkFlow();
		wfl.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wfl.setCaseCode(toCase.getCaseCode());
		ToWorkFlow awfl = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wfl);
		if (awfl != null) {
			 result.setSuccess(false);
			 result.setMessage("当前已存在未结束的重启流程");
			 return result;
		}
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.CASE_STOP_PROCESS.getCode());
		wf.setCaseCode(toCase.getCaseCode());
		ToWorkFlow awf = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if (awf != null) {
			 result.setSuccess(false);
			 result.setMessage("当前已存在未结束的爆单流程");
			 return result;
		}
		return result;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String getVars(String instCode) {
		String var = "";
		try {
			var = (String) workFlowManager.getVar(instCode, "caseProperty1").getValue();
		} catch (WorkFlowException e) {
		}

		return var;
	}

}
