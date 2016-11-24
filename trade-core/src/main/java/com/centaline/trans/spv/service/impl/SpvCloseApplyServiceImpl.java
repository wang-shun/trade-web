package com.centaline.trans.spv.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.enums.SpvCloseApplyStatusEnum;
import com.centaline.trans.common.enums.SpvStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.repository.ToWorkFlowMapper;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvCloseApply;
import com.centaline.trans.spv.entity.ToSpvCloseApplyAudit;
import com.centaline.trans.spv.repository.ToSpvCloseApplyAuditMapper;
import com.centaline.trans.spv.repository.ToSpvCloseApplyMapper;
import com.centaline.trans.spv.repository.ToSpvMapper;
import com.centaline.trans.spv.service.CashFlowOutService;
import com.centaline.trans.spv.service.SpvCloseApplyService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvCloseInfoVO;
import com.centaline.trans.task.repository.ActRuEventSubScrMapper;

@Service
public class SpvCloseApplyServiceImpl implements SpvCloseApplyService {
	
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ToWorkFlowMapper toWorkFlowMapper;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private ActRuEventSubScrMapper actRuEventSubScrMapper;
	@Autowired
	private ToCaseService toCaseService;
	
	@Autowired
	private ToSpvService toSpvService;
	@Autowired
	private CashFlowOutService cashFlowOutService;
	
	@Autowired
	private ToSpvCloseApplyMapper toSpvCloseApplyMapper;
	@Autowired
	private ToSpvCloseApplyAuditMapper toSpvCloseApplyAuditMapper;
	@Autowired
	private ToSpvMapper toSpvMapper;
	
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	/**
	 * 中止/结束起始提交页面
	 */
	@Override
	public void spvCloseApplyPage(HttpServletRequest request, String spvCode, String businessKey) {
		setRequestAttribute(request,spvCode,businessKey);
	}

	/**
	 * 中止/结束驳回提交页面
	 */
	@Override
	public void spvCloseApplyProcess(HttpServletRequest request, String instCode, String taskId, String businessKey) {
		setRequestAttribute(request,null,businessKey);
	}

	/**
	 * 中止/结束主办审批页面
	 */
	@Override
	public void spvCloseHostAuditProcess(HttpServletRequest request, String instCode, String taskId, String businessKey) {
		setRequestAttribute(request,null,businessKey);
	}

	/**
	 * 中止/结束总监审批页面
	 */
	@Override
	public void spvCloseDirectorAuditProcess(HttpServletRequest request, String instCode, String taskId, String businessKey) {
		setRequestAttribute(request,null,businessKey);
	}	
	
	/**
	 * 中止/结束起始提交操作
	 */
	@Override
	public void spvClosePageDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String instCode) {
		//合约主对象
		String spvCode = spvCloseInfoVO.getToSpvCloseApply().getSpvCode();
		ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCode);
		String caseCode = toSpv.getCaseCode();
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		
		// 资金监管出入账申请无在途申请的时候才可以开启此流程
		ToWorkFlow record = new ToWorkFlow();
		record.setCaseCode(caseCode);
		List<ToWorkFlow> toWorkFlows = toWorkFlowService.queryActiveToWorkFlowByCaseCode(record);
		for(ToWorkFlow toWorkFlow : toWorkFlows){
			if(spvCode.equals(toWorkFlow.getBizCode())){
				if(WorkFlowEnum.SPV_CASHFLOW_IN_DEFKEY.getCode().equals(toWorkFlow.getBusinessKey())){
					throw new BusinessException("尚有‘入款’流程进行中，不能开启‘中止/结束’流程！");
				}else if(WorkFlowEnum.SPV_CASHFLOW_OUT_DEFKEY.getCode().equals(toWorkFlow.getBusinessKey())){
					throw new BusinessException("尚有‘出款’流程进行中，不能开启‘中止/结束’流程！");
				}else if(WorkFlowEnum.SPV_CLOSE_DEFKEY.getCode().equals(toWorkFlow.getBusinessKey())){
					throw new BusinessException("‘中止/结束’流程已经存在，不能重复开启！");
				}
			}
		}
		
		ToWorkFlow twf = new ToWorkFlow();
		twf.setBusinessKey(WorkFlowEnum.SPV_DEFKEY.getCode());
		twf.setBizCode(spvCode);
		ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(twf);
		
		if("0".equals(spvCloseInfoVO.getToSpvCloseApply().getCloseType())){
/*			ActRuEventSubScr condition=new ActRuEventSubScr();
			condition.setEventType("message");
			condition.setEventName("SpvFinishMsg");
			condition.setProcInstId(toWorkFlow.getInstCode());
			List<ActRuEventSubScr> subScrs = actRuEventSubScrMapper.listBySelective(condition);
			if(subScrs == null || subScrs.isEmpty()){
				throw new BusinessException("资金监管流程没有找到需要发送的消息，不能开启‘结束’流程！");
			}*/
			
			/**金额由用户自己判断*/
/*			Map<String,Object> completeCashFlowInfoMap = cashFlowOutService.getCompleteCashFlowInfoBySpvCode(spvCode);
	    	//所有合约下已完成的出账金额总和
			BigDecimal totalCashFlowOutAmount = (BigDecimal) completeCashFlowInfoMap.get("totalCashFlowOutAmount");
	    	//监管总额
			BigDecimal toSpvTotalAmount = toSpv.getAmount();
	    	if(totalCashFlowOutAmount.compareTo(toSpvTotalAmount) != 0){
	    		throw new BusinessException("出账金额总和与监管总额数值不等，不能开启‘结束’流程！");
	    	}*/
		}

		// 开启流程
		SessionUser user = uamSessionService.getSessionUser();
		
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("applier", user.getUsername());
		User host = uamUserOrgService.getUserById(toCase.getLeadingProcessId());
		vars.put("host", host.getUsername());
		User director = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getServiceDepId(), "JYFKZJ");
		vars.put("director", director.getUsername());
		String oldStatus = toSpv.getStatus();
		vars.put("oldStatus", oldStatus);
		
		String spvCloseCode = createSpvCloseCode();
		
		ToSpvCloseApply apply = spvCloseInfoVO.getToSpvCloseApply();
		apply.setSpvCode(spvCode);
		apply.setSpvCloseCode(spvCloseCode);
		apply.setApplier(user.getId());
		apply.setIsDeleted("0");
		apply.setAuditor(host.getId());
		apply.setReAuditor(director.getId());
		apply.setStatus(SpvCloseApplyStatusEnum.AUDIT.getCode());
		apply.setApplyTime(new Date());
		apply.setCreateBy(user.getId());
		apply.setCreateTime(new Date());
		
		toSpvCloseApplyMapper.insertSelective(apply);

		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSpvCloseApplyProcessDfKey(), spvCloseCode, vars);
		
		//更新合约状态
		toSpv.setStatus(SpvStatusEnum.INPROCESS.getCode());
		toSpvMapper.updateByPrimaryKeySelective(toSpv);
		//资金监管流程挂起
		if (toWorkFlow != null) {
			processInstanceService.activateOrSuspendProcessInstance(toWorkFlow.getInstCode(), false);
		}else{
			throw new BusinessException("找不到合约编号为"+spvCode+"的资金监管合约流程！");
		}
		
		//插入工作流表
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setBusinessKey(WorkFlowEnum.SPV_CLOSE_DEFKEY.getCode());
		workFlow.setCaseCode(caseCode);
		workFlow.setBizCode(spvCloseCode);
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(propertyUtilsService.getSpvCloseApplyProcessDfKey());
		workFlow.setProcessOwner(user.getId());
		workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(workFlow);
		
		// 提交申请任务
		PageableVo pageableVo = taskService.listTasks(processInstance.getId(), false);
		List<TaskVo> taskList = pageableVo.getData();
		for (TaskVo task : taskList) {
			if ("SpvCloseApply".equals(task.getTaskDefinitionKey())) {
				taskService.complete(task.getId() + "");
			}
		}
	}

	/**
	 * 中止/结束驳回提交操作
	 */
	@Override
	public void spvCloseApplyDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String taskId, String instCode, String businessKey, Boolean continueApply) {
		// 提交流程
		String spvCode = spvCloseInfoVO.getToSpvCloseApply().getSpvCode();

		if(!continueApply){
			//取消申请
			//1.更新回之前状态状态
			ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCode);
			String oldStatus = (String)workFlowManager.getVar(instCode, "oldStatus").getValue();
			toSpv.setStatus(oldStatus);
			toSpvMapper.updateByPrimaryKeySelective(toSpv);
			//2.删除当前流程
			processInstanceService.deleteProcess(instCode);
			//3.删除t_to_workflow表对应记录
			toWorkFlowService.deleteWorkFlowByInstCode(instCode);
			//4.删除‘中止/结束’申请表和审批表中数据
			toSpvCloseApplyMapper.deleteByPrimaryKey(spvCloseInfoVO.getToSpvCloseApply().getPkid());
			toSpvCloseApplyAuditMapper.deleteByApplyId(spvCloseInfoVO.getToSpvCloseApply().getPkid().toString());
			//5.资金监管流程激活
			ToWorkFlow twf = new ToWorkFlow();
			twf.setBusinessKey(WorkFlowEnum.SPV_DEFKEY.getCode());
			twf.setBizCode(spvCode);
			ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(twf);
			if (record != null) {
				processInstanceService.activateOrSuspendProcessInstance(record.getInstCode(), true);
			}
		}else{
			ToSpvCloseApply apply = spvCloseInfoVO.getToSpvCloseApply();
			toSpvCloseApplyMapper.updateByPrimaryKeySelective(apply);
			
			Map<String, Object> variables = new HashMap<String, Object>();
			taskService.submitTask(taskId, variables);
		}
	}

	/**
	 * 中止/结束主办审批操作
	 */
	@Override
	public void spvCloseHostAuditDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String instCode,
			String taskitem, String taskId, String businessKey,Boolean result) {
		// 提交流程
		SessionUser user = uamSessionService.getSessionUser();
		
		ToSpvCloseApply apply = spvCloseInfoVO.getToSpvCloseApply();
		apply.setAuditTime(new Date());
		if(result){
			apply.setStatus(SpvCloseApplyStatusEnum.REAUDIT.getCode());
		}else{
			apply.setStatus(SpvCloseApplyStatusEnum.DRAFT.getCode());
		}
		
		ToSpvCloseApplyAudit audit = spvCloseInfoVO.getToSpvCloseApplyAuditList().get(0);
		audit.setApplyId(spvCloseInfoVO.getToSpvCloseApply().getPkid().toString());
		audit.setActProcId(instCode);
		audit.setTaskDefKey(taskitem);
		audit.setTaskId(taskId);
		audit.setOperator(user.getId());
		audit.setResult(result?"通过":"驳回");
		audit.setCreateBy(user.getId());
		audit.setCreateTime(new Date());
		
		toSpvCloseApplyMapper.updateByPrimaryKeySelective(apply);
		toSpvCloseApplyAuditMapper.insertSelective(audit);
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("hostAppr", result);
		taskService.submitTask(taskId, variables);
	}

	/**
	 * 中止/结束总监审批操作
	 */
	@Override
	public void spvCloseDirectorAuditDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String instCode,
			String taskitem, String taskId, String businessKey, Boolean result) {
		// 提交流程
		SessionUser user = uamSessionService.getSessionUser();
		
		String spvCode = spvCloseInfoVO.getToSpvCloseApply().getSpvCode();
		
		ToSpvCloseApply apply = spvCloseInfoVO.getToSpvCloseApply();
		apply.setReAuditTime(new Date());
		if(result){
			apply.setStatus(SpvCloseApplyStatusEnum.COMPLETE.getCode());
		}else{
			apply.setStatus(SpvCloseApplyStatusEnum.DRAFT.getCode());
		}
		
		ToSpvCloseApplyAudit audit = spvCloseInfoVO.getToSpvCloseApplyAuditList().get(0);
		audit.setApplyId(spvCloseInfoVO.getToSpvCloseApply().getPkid().toString());
		audit.setActProcId(instCode);
		audit.setTaskDefKey(taskitem);
		audit.setTaskId(taskId);
		audit.setOperator(user.getId());
		audit.setResult(result?"通过":"驳回");
		audit.setCreateBy(user.getId());
		audit.setCreateTime(new Date());
		
		toSpvCloseApplyMapper.updateByPrimaryKeySelective(apply);
		toSpvCloseApplyAuditMapper.insertSelective(audit);
		
		ToSpvCloseApply tempApply = toSpvCloseApplyMapper.selectByPrimaryKey(apply.getPkid());
		
		String closeType = tempApply.getCloseType();
		if(result){
			if("1".equals(closeType)){
				//中止:删除资金监管流程，t_to_workflow更新，更新合约状态
				//更新spv表
				ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCode);
				toSpv.setStatus(SpvStatusEnum.TERMINATE.getCode());
				toSpvMapper.updateByPrimaryKeySelective(toSpv);
				//更新t_to_workflow表(资金监管流程)
				ToWorkFlow wf = new ToWorkFlow();
				wf.setBizCode(spvCode);
				wf.setBusinessKey(WorkFlowEnum.SPV_DEFKEY.getCode());
				ToWorkFlow wf2 = toWorkFlowMapper.queryActiveToWorkFlowByBizCodeBusKey(wf);
				if(wf2 != null){			
					wf2.setStatus(WorkFlowStatus.TERMINATE.getCode());
					toWorkFlowMapper.updateByPrimaryKeySelective(wf2);
				}
				//删除资金监管流程
				processInstanceService.deleteProcess(wf2.getInstCode());
				//更新t_to_workflow表(中止/结束流程)
				ToWorkFlow wf3 = new ToWorkFlow();
				wf3.setBizCode(apply.getSpvCloseCode());
				wf3.setBusinessKey(WorkFlowEnum.SPV_CLOSE_DEFKEY.getCode());
				ToWorkFlow wf4 = toWorkFlowMapper.queryActiveToWorkFlowByBizCodeBusKey(wf3);
				if(wf4 != null){
					wf4.setStatus(WorkFlowStatus.COMPLETE.getCode());
					toWorkFlowMapper.updateByPrimaryKeySelective(wf4);
				}
			}else if("0".equals(closeType)){
				//结束:发送消息到资金监管流程，更新t_to_workflow表(资金监管流程)，更新spv表状态
				ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCode);	
				//更新t_to_workflow表(资金监管流程)
				ToWorkFlow wf = new ToWorkFlow();
				wf.setBizCode(spvCode);
				wf.setBusinessKey(WorkFlowEnum.SPV_DEFKEY.getCode());
				ToWorkFlow wf2 = toWorkFlowMapper.queryActiveToWorkFlowByBizCodeBusKey(wf);
				if(wf2 != null){
					wf2.setStatus(WorkFlowStatus.COMPLETE.getCode());
					toWorkFlowMapper.updateByPrimaryKeySelective(wf2);
				}
				//更新t_to_workflow表(中止/结束流程)
				ToWorkFlow wf3 = new ToWorkFlow();
				wf3.setBizCode(apply.getSpvCloseCode());
				wf3.setBusinessKey(WorkFlowEnum.SPV_CLOSE_DEFKEY.getCode());
				ToWorkFlow wf4 = toWorkFlowMapper.queryActiveToWorkFlowByBizCodeBusKey(wf3);
				if(wf4 != null){
					//资金监管流程激活
					processInstanceService.activateOrSuspendProcessInstance(wf2.getInstCode(), true);
					wf4.setStatus(WorkFlowStatus.COMPLETE.getCode());
					toWorkFlowMapper.updateByPrimaryKeySelective(wf4);
				}
				//当出款总额等于监管金额时发起消息通知资金监管流程 ：SpvProcess
				messageService.sendSpvFinishMsgByIntermi(instCode);
				//更新spv表
				toSpv.setCloseTime(new Date());
				toSpv.setStatus(SpvStatusEnum.COMPLETE.getCode());
				toSpvMapper.updateByPrimaryKeySelective(toSpv);
			}
		}
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("directorAppr", result);
		taskService.submitTask(taskId, variables);

	}
	
	private void setRequestAttribute(HttpServletRequest request, String spvCode_, String businessKey){
		String spvCode = null;
		SpvCloseInfoVO spvCloseInfoVO = findSpvCloseInfoVOBySpvCode(businessKey);
		if(spvCloseInfoVO != null){
			List<ToSpvCloseApplyAudit> audits = spvCloseInfoVO.getToSpvCloseApplyAuditList();
			if(audits != null && !audits.isEmpty()){
				for(ToSpvCloseApplyAudit audit : audits){
					audit.setOperatorName(uamSessionService.getSessionUserById(audit.getOperator()).getRealName());
					audit.setOperatorJobName(uamSessionService.getSessionUserById(audit.getOperator()).getServiceJobName());
				}
			}
		}
        if(StringUtils.isBlank(businessKey)){
        	spvCode = spvCode_;
        }else{
        	spvCode = spvCloseInfoVO.getToSpvCloseApply().getSpvCode();
        }
		ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
		SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv == null?null:toSpv.getPkid());
		
		if(spvBaseInfoVO != null && spvBaseInfoVO.getToSpv() != null 
				&& !StringUtils.isBlank(spvBaseInfoVO.getToSpv().getApplyUser())){
			request.setAttribute("applyUserName",uamSessionService.getSessionUserById(spvBaseInfoVO.getToSpv().getApplyUser()).getRealName());
		}

		Map<String,Object> completeCashFlowInfoMap = cashFlowOutService.getCompleteCashFlowInfoBySpvCode(spvCode);
		
		request.setAttribute("cashFlowList", completeCashFlowInfoMap.get("cashFlowList"));
		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
		request.setAttribute("spvCloseInfoVO", spvCloseInfoVO);
	}
	
	private SpvCloseInfoVO findSpvCloseInfoVOBySpvCode(String spvCloseCode){
		SpvCloseInfoVO spvCloseInfoVO = new SpvCloseInfoVO();
		ToSpvCloseApply toSpvCloseApply = toSpvCloseApplyMapper.selectBySpvCloseCode(spvCloseCode);
		if(toSpvCloseApply == null) return null;
		
		List<ToSpvCloseApplyAudit> toSpvCloseApplyAuditList = toSpvCloseApplyAuditMapper.selectByApplyId(toSpvCloseApply.getPkid().toString());
		spvCloseInfoVO.setToSpvCloseApply(toSpvCloseApply);
		spvCloseInfoVO.setToSpvCloseApplyAuditList(toSpvCloseApplyAuditList);
		return spvCloseInfoVO;
	}
	
	private String createSpvCloseCode() {
		return uamBasedataService.nextSeqVal("SPV_CODE", new SimpleDateFormat("yydd").format(new Date()));
	}

}
