package com.centaline.trans.spv.service.impl;

import java.math.BigDecimal;
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
import com.centaline.trans.task.entity.ActRuEventSubScr;
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
	private ActRuEventSubScrMapper actRuEventSubScrMapper;
	
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
	public void spvCloseManagerAuditProcess(HttpServletRequest request, String instCode, String taskId, String businessKey) {
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
	public void spvClosePageDeal(HttpServletRequest request, String spvCode, SpvCloseInfoVO spvCloseInfoVO, String instCode) {
		//合约主对象
		ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCloseInfoVO.getToSpvCloseApply().getSpvCode());
		
		// 资金监管出入账申请无在途申请的时候才可以开启此流程
		ToWorkFlow record = new ToWorkFlow();
		record.setBizCode(spvCode);
		record.setBusinessKey("SpvCashflowInProcess");
		ToWorkFlow toWorkFlow1 = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(record);
		if(toWorkFlow1 != null){
			throw new BusinessException("尚有‘入款’流程进行中，不能开启‘中止/结束’流程！");
		}
		record.setBusinessKey("SPVCashflowOutProcess");
		ToWorkFlow toWorkFlow2 = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(record);
		if(toWorkFlow2 != null){
			throw new BusinessException("尚有‘出款’流程进行中，不能开启‘中止/结束’流程！");
		}
		record.setBusinessKey("spvCloseApplyProcess");
		ToWorkFlow toWorkFlow3 = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(record);
		if(toWorkFlow3 != null){
			throw new BusinessException("‘中止/结束’流程已经存在，不能重复开启！");
		}
		
		if("0".equals(spvCloseInfoVO.getToSpvCloseApply().getCloseType())){
			ActRuEventSubScr condition=new ActRuEventSubScr();
			condition.setEventType("message");
			condition.setEventName("SpvFinishMsg");
			condition.setProcInstId(instCode);
			List<ActRuEventSubScr>subScrs= actRuEventSubScrMapper.listBySelective(condition);
			if(subScrs == null || subScrs.isEmpty()){
				throw new BusinessException("资金监管流程没有找到需要发送的消息，不能开启‘结束’流程！");
			}
			
			Map<String,Object> completeCashFlowInfoMap = cashFlowOutService.getCompleteCashFlowInfoBySpvCode(spvCode);
	    	//所有合约下已完成的出账金额总和
			BigDecimal totalCashFlowOutAmount = (BigDecimal) completeCashFlowInfoMap.get("totalCashFlowOutAmount");
	    	//监管总额
			BigDecimal toSpvTotalAmount = toSpv.getAmount();
	    	if(totalCashFlowOutAmount.compareTo(toSpvTotalAmount) != 0){
	    		throw new BusinessException("出账金额总和与监管总额数值不等，不能开启‘结束’流程！");
	    	}
		}
		

		// 开启流程
		SessionUser user = uamSessionService.getSessionUser();
		
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("applier", user.getUsername());
		//User riskControlDirector = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getServiceDepId(), "JYFKZJ");
		vars.put("manager", user.getUsername());
		vars.put("director", user.getUsername());
		
		String spvCloseCode = createSpvCloseCode();
		
		ToSpvCloseApply apply = spvCloseInfoVO.getToSpvCloseApply();
		apply.setSpvCode(spvCode);
		apply.setSpvCloseCode(spvCloseCode);
		apply.setApplier(user.getId());
		apply.setIsDeleted("0");
/*		apply.setAuditor(user.getId());
		apply.setReAuditor(user.getId());*/
		apply.setStatus(SpvCloseApplyStatusEnum.AUDIT.getCode());
		apply.setApplyTime(new Date());
		apply.setCreateBy(user.getId());
		apply.setCreateTime(new Date());
		
		toSpvCloseApplyMapper.insertSelective(apply);

		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSPVCashflowOutProcessDfKey(), spvCloseCode, vars);
		
		//资金监管流程挂起
		ToWorkFlow twf = new ToWorkFlow();
		twf.setBusinessKey(WorkFlowEnum.SPV_DEFKEY.getCode());
		twf.setBizCode(spvCode);
		ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(twf);

		if (toWorkFlow != null) {
			processInstanceService.activateOrSuspendProcessInstance(processInstance.getId(), false);
		}else{
			throw new BusinessException("找不到合约编号为"+spvCode+"的资金监管合约流程！");
		}
		
		//插入工作流表
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setBusinessKey("spvCloseApplyProcess");
		workFlow.setCaseCode(toSpv.getCaseCode());
		workFlow.setBizCode(spvCloseCode);
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(propertyUtilsService.getSPVCashflowOutProcessDfKey());
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
			//1.删除当前流程
			processInstanceService.deleteByBusinessKey(businessKey);
			//2.删除t_to_workflow表对应记录
			toWorkFlowService.deleteWorkFlowByInstCode(instCode);
			//3.删除‘中止/结束’申请表和审批表中数据
			toSpvCloseApplyMapper.deleteByPrimaryKey(spvCloseInfoVO.getToSpvCloseApply().getPkid());
			toSpvCloseApplyAuditMapper.deleteByApplyId(spvCloseInfoVO.getToSpvCloseApply().getPkid().toString());
			//4.资金监管流程激活
			ToWorkFlow twf = new ToWorkFlow();
			twf.setBusinessKey(WorkFlowEnum.SPV_DEFKEY.getCode());
			twf.setBizCode(spvCode);
			ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(twf);
			if (record != null) {
				processInstanceService.activateOrSuspendProcessInstance(instCode, true);
			}else{
				throw new BusinessException("找不到合约编号为"+spvCode+"的资金监管合约流程！");
			}
		}else{
			ToSpvCloseApply apply = spvCloseInfoVO.getToSpvCloseApply();
			toSpvCloseApplyMapper.insertSelective(apply);
			
			Map<String, Object> variables = new HashMap<String, Object>();
			taskService.submitTask(taskId, variables);
		}
	}

	/**
	 * 中止/结束主办审批操作
	 */
	@Override
	public void spvCloseManagerAuditDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String instCode,
			String taskitem, String taskId, String businessKey,Boolean result) {
		// 提交流程
		SessionUser user = uamSessionService.getSessionUser();
		
		ToSpvCloseApply apply = spvCloseInfoVO.getToSpvCloseApply();
		apply.setAuditor(user.getId());
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
		variables.put("managerAppr", result);
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
		apply.setReAuditor(user.getId());
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
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("directorAppr", result);
		taskService.submitTask(taskId, variables);
		
		String closeType = spvCloseInfoVO.getToSpvCloseApply().getCloseType();
		if("1".equals(closeType)){
			//中止:删除资金监管流程，t_to_workflow更新，删除表单数据
			//TODO ...
			processInstanceService.deleteByBusinessKey(spvCode);
		}else if("0".equals(closeType)){
			//结束:判断资金监管流程是否在等待消息，是的话发送消息结束资金监管流程，t_to_workflow更新，否则抛出异常
			//更新spv表
			ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCloseInfoVO.getToSpvCloseApply().getSpvCode());
			toSpv.setStatus(SpvStatusEnum.COMPLETE.getCode());
			toSpvMapper.updateByPrimaryKey(toSpv);
			//当出款总额等于监管金额时发起消息通知资金监管流程 ：SpvProcess
			messageService.sendSpvFinishMsgByIntermi(instCode);	
			//更新t_to_workflow表(资金监管流程)
			ToWorkFlow wf = new ToWorkFlow();
			wf.setCaseCode(toSpv.getCaseCode());
			wf.setBusinessKey(WorkFlowEnum.SPV_DEFKEY.getCode());
			ToWorkFlow wf2 = toWorkFlowMapper.queryActiveToWorkFlowByCaseCodeBusKey(wf);
			if(wf2 != null){
				wf2.setStatus(WorkFlowStatus.COMPLETE.getCode());
				toWorkFlowMapper.updateByPrimaryKey(wf2);
			}
		}
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
