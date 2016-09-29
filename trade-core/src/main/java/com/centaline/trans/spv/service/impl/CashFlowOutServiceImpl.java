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
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvAduit;
import com.centaline.trans.spv.repository.ToSpvCashFlowApplyMapper;
import com.centaline.trans.spv.repository.ToSpvMapper;
import com.centaline.trans.spv.service.CashFlowOutService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;

@Service
public class CashFlowOutServiceImpl implements CashFlowOutService {
	@Autowired
	private ToSpvService toSpvService;	
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
		
	@Autowired
	private ToSpvMapper toSpvMapper;
	@Autowired
	private ToSpvCashFlowApplyMapper toSpvCashFlowApplyMapper;
		
	@Autowired
	private UamSessionService uamSessionService;	
	@Autowired
	private UamBasedataService uamBasedataService;

	@Override
	public void cashFlowOutPage(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
			setRequestAttribute(request,businessKey);	
	}

	@Override
	public void cashFlowOutPageDeal(HttpServletRequest request, String instCode, String taskId,String taskItem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey) throws Exception {
		SessionUser user = uamSessionService.getSessionUser();
        // TODO 判断流程是否已存在
		
		if(spvChargeInfoVO == null || spvChargeInfoVO.getToSpvCashFlowApply() == null) throw new BusinessException("申请信息不存在！");
		
		String cashflowApplyCode = spvChargeInfoVO.getToSpvCashFlowApply().getCashflowApplyCode();
		//创建spvApplyCode
		String spvApplyCode = createSpvApplyCode();
		if(StringUtils.isBlank(cashflowApplyCode)){
			cashflowApplyCode = spvApplyCode;
		}
		//设置申请人为当前用户
		spvChargeInfoVO.getToSpvCashFlowApply().setApplier(user.getId());
		//保存数据
		toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO); 
		//获取合约
		ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode());
		Map<String, Object> vars = new HashMap<String, Object>();
		//开启流程
		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSPVCashflowOutProcessDfKey(), cashflowApplyCode, vars);

		//插入工作流表
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setBusinessKey("SPVCashflowOutProcess");
		workFlow.setCaseCode(toSpv.getCaseCode());
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(propertyUtilsService.getSPVCashflowOutProcessDfKey());
		workFlow.setProcessOwner(user.getId());
		workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(workFlow);
		
		// 提交申请任务
		PageableVo pageableVo = taskService.listTasks(processInstance.getId(), false);
		List<TaskVo> taskList = pageableVo.getData();
		for (TaskVo task : taskList) {
			if ("CashflowOutApply".equals(task.getTaskDefinitionKey())) {
				taskService.complete(task.getId() + "");
			}
		}
	}

	@Override
	public void cashFlowOutApplyProcess(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
		setRequestAttribute(request,businessKey);       
	}

	@Override
	public void cashFlowOutApplyDeal(HttpServletRequest request, String instCode, String taskId,String taskItem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey,Boolean chargeOutAppr) throws Exception {
		
	    toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);    
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutDirectorAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		setRequestAttribute(request,businessKey);
	}

	@Override
	public void cashFlowOutDirectorAduitDeal(HttpServletRequest request, String instCode, String taskId,String taskItem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
		    SessionUser user = uamSessionService.getSessionUser();
		    //设置申请复审人
		    spvChargeInfoVO.getToSpvCashFlowApply().setApplyAuditor(user.getId());
		    //添加审批记录
		    addAduitRecord(instCode, taskId, taskItem, spvChargeInfoVO, chargeOutAppr);
		    toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(spvChargeInfoVO.getToSpvCashFlowApply());
		
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("directorAduit",chargeOutAppr);
			
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutFinanceAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		setRequestAttribute(request,businessKey);
	}

	@Override
	public void cashFlowOutFinanceAduitDeal(HttpServletRequest request, String instCode, String taskId,String taskItem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
			SessionUser user = uamSessionService.getSessionUser();
		    //设置申请复审人
		    spvChargeInfoVO.getToSpvCashFlowApply().setFtPreAuditor(user.getId());
		    //添加审批记录
		    addAduitRecord(instCode, taskId, taskItem, spvChargeInfoVO, chargeOutAppr);
		    toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(spvChargeInfoVO.getToSpvCashFlowApply());
	
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("financeAduit",chargeOutAppr);
			
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutFinanceSecondAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		setRequestAttribute(request,businessKey);
	}

	@Override
	public void cashFlowOutFinanceSecondAduitDeal(HttpServletRequest request, String instCode, String taskId,String taskItem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
			SessionUser user = uamSessionService.getSessionUser();
		    //设置申请复审人
		    spvChargeInfoVO.getToSpvCashFlowApply().setFtPostAuditor(user.getId());
		    //添加审批记录
		    addAduitRecord(instCode, taskId, taskItem, spvChargeInfoVO, chargeOutAppr);
		    toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(spvChargeInfoVO.getToSpvCashFlowApply());
	
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("financeSecondAduit",chargeOutAppr);
			
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutDealProcess(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
		setRequestAttribute(request,businessKey);
	}

	@Override
	public void cashFlowOutDeal(HttpServletRequest request, String instCode, String taskId,String taskItem, String handle,
			SpvChargeInfoVO spvChargeInfoVO, Boolean chargeOutAppr) throws Exception {
		
			Map<String, Object> variables = new HashMap<String, Object>();
			taskService.submitTask(taskId, variables);
	}	
	
	private String createSpvApplyCode() {
		return uamBasedataService.nextSeqVal("SPV_CODE", new SimpleDateFormat("yyyyMMdd").format(new Date()));
	}

	private void setRequestAttribute(HttpServletRequest request, String businessKey){
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCode(businessKey);
    	spvCode = (String) request.getAttribute("spvCode");
    	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
    	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv == null?null:toSpv.getPkid());
    	if(spvChargeInfoVO != null){       	
    		String applyAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getApplyAuditor()).getRealName();
        	String ftPreAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getFtPreAuditor()).getRealName();
        	String ftPostAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getFtPostAuditor()).getRealName();
        	String createByName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getCreateBy()).getRealName();
        	
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);  
        	request.setAttribute("applyAuditorName", applyAuditorName);
        	request.setAttribute("ftPreAuditorName", ftPreAuditorName);
        	request.setAttribute("ftPostAuditorName", ftPostAuditorName);
        	request.setAttribute("createByName", createByName);
    	}
    	
    	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);    
	}
	
	//添加审批记录
	private void addAduitRecord(String instCode, String taskId,String taskItem,SpvChargeInfoVO spvChargeInfoVO,Boolean chargeOutAppr){
		SessionUser user = uamSessionService.getSessionUser();
		//设置审批记录表的：流程实例、流程节点、任务id、操作人
		List<ToSpvAduit> toSpvAduitList = spvChargeInfoVO.getToSpvAduitList();
		
		ToSpvAduit toSpvAduit = new ToSpvAduit();
		toSpvAduit.setActProcId(instCode);
		toSpvAduit.setTaskDefKey(taskItem);
		toSpvAduit.setTaskId(taskId);
		toSpvAduit.setOperator(user.getId());
		toSpvAduit.setResult(chargeOutAppr?"审核通过":"审核驳回");
		toSpvAduit.setCreateBy(user.getId());
		toSpvAduit.setCreateTime(new Date());
	
		toSpvAduitList.add(toSpvAduit);
		spvChargeInfoVO.setToSpvAduitList(toSpvAduitList);
	}
	
}
