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
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.SpvCashFlowApplyStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.common.vo.FileUploadVO;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvAduit;
import com.centaline.trans.spv.entity.ToSpvCashFlow;
import com.centaline.trans.spv.entity.ToSpvCashFlowApply;
import com.centaline.trans.spv.entity.ToSpvCashFlowApplyAttach;
import com.centaline.trans.spv.entity.ToSpvReceipt;
import com.centaline.trans.spv.entity.ToSpvVoucher;
import com.centaline.trans.spv.repository.ToSpvAduitMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowApplyAttachMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowApplyMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowMapper;
import com.centaline.trans.spv.repository.ToSpvReceiptMapper;
import com.centaline.trans.spv.repository.ToSpvVoucherMapper;
import com.centaline.trans.spv.service.CashFlowInService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvCaseFlowOutInfoVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;
import com.centaline.trans.spv.vo.SpvRecordedsVO;
import com.centaline.trans.spv.vo.SpvReturnCashflowVO;

@Service
public class CashFlowInServiceImpl implements CashFlowInService {
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
	private UamSessionService uamSessionService;	
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private ToSpvCashFlowApplyMapper toSpvCashFlowApplyMapper;	
	@Autowired
	private ToSpvCashFlowMapper toSpvCashFlowMapper;
	@Autowired
	private ToSpvAduitMapper toSpvAduitMapper;
	@Autowired
	private ToSpvReceiptMapper toSpvReceiptMapper;
	@Autowired
	private ToSpvCashFlowApplyAttachMapper toSpvCashFlowApplyAttachMapper;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;

	@Override
	public void cashFlowInPage(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
        String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCode(businessKey);

        if(spvChargeInfoVO != null && spvChargeInfoVO.getToSpvCashFlowApply() != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	String applyAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getApplyAuditor()).getRealName();
        	String ftPreAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getFtPreAuditor()).getRealName();
        	String ftPostAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getFtPostAuditor()).getRealName();
        	String createByName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getCreateBy()).getRealName();
        	request.setAttribute("applyAuditorName", applyAuditorName);
        	request.setAttribute("ftPreAuditorName", ftPreAuditorName);
        	request.setAttribute("ftPostAuditorName", ftPostAuditorName);
        	request.setAttribute("createByName", createByName);
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	@Override
	public void cashFlowInPageDealApply(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey) throws Exception {
		SessionUser user = uamSessionService.getSessionUser();
		//判断流程是否已存在
		ToWorkFlow  toWorkFlow = toWorkFlowService.queryWorkFlowByInstCode(instCode);
		if( null != toWorkFlow && !WorkFlowStatus.ACTIVE.getCode().equals(toWorkFlow.getStatus())){
			throw new BusinessException("流程已经开启!");
		}
		
		if(null == spvRecordedsVO || null == spvRecordedsVO.getItems()) throw new BusinessException("申请入账流水信息不存在！");
		
		//保存数据
		//toSpvService.saveSpvChargeInfoVObyIn(spvRecordedsVO); 
		
		Map<String, Object> vars = new HashMap<String, Object>();
		//开启流程
		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSpvCashflowInProcess(), createSpvApplyCode(), vars);

		//插入工作流表
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setBusinessKey("SpvCashflowInProcess");
		if(null != spvRecordedsVO.getSpvConCode()){
			workFlow.setCaseCode(spvRecordedsVO.getCaseCode());
		}else{
			throw new BusinessException("监管合约信息不存在！");
		}
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(propertyUtilsService.getSpvCashflowInProcess());
		workFlow.setProcessOwner(user.getId());
		workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(workFlow);
		
		// 提交申请任务
		PageableVo pageableVo = taskService.listTasks(processInstance.getId(), false);
		List<TaskVo> taskList = pageableVo.getData();
		for (TaskVo task : taskList) {
			if ("SpvCashflowInApply".equals(task.getTaskDefinitionKey())) {
				taskService.complete(task.getId() + "");
			}
		}
	}

	@Override
	public void cashFlowInApplyProcess(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCodeByIn(businessKey);

        if(null != spvChargeInfoVO  && null != spvChargeInfoVO.getToSpvCashFlowApply() && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	String applyAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getApplyAuditor()).getRealName();
        	String createByName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getCreateBy()).getRealName();
        	request.setAttribute("applyAuditorName", applyAuditorName);
        	request.setAttribute("createByName", createByName);
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	@Override
	public void cashFlowInApplyDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey, Boolean chargeInAppr) throws Exception {

		SessionUser user = uamSessionService.getSessionUser();
		String spvApplyCode = null;
		
		toSpvService.saveSpvChargeInfoVObyIn(spvRecordedsVO,handle,spvApplyCode);//保存数据  
		
		/**1.查询申请*/
		ToSpvCashFlowApply toSpvCashFlowApply = toSpvCashFlowApplyMapper.selectByCashFlowApplyCode(spvRecordedsVO.getBusinessKey());
		List<ToSpvCashFlow> toSpvCashFlowList = toSpvCashFlowMapper.selectByCashFlowApplyId(toSpvCashFlowApply.getPkid());
		if(null != toSpvCashFlowList)
			for(ToSpvCashFlow toSpvCashFlow:toSpvCashFlowList){
				toSpvCashFlow.setStatus(SpvCashFlowApplyStatusEnum.DIRECTORADUIT.getCode());
				toSpvCashFlow.setUpdateBy(user.getId());
				toSpvCashFlow.setUpdateTime(new Date());
				toSpvCashFlowMapper.updateByPrimaryKeySelective(toSpvCashFlow);
			}
		
		toSpvCashFlowApply.setStatus(SpvCashFlowApplyStatusEnum.DIRECTORADUIT.getCode());
		toSpvCashFlowApply.setUpdateBy(user.getId());
		toSpvCashFlowApply.setUpdateTime(new Date());
		toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(toSpvCashFlowApply);//更新状态
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("RiskControlOfficer", user.getUsername());
		variables.put("RiskControlDirector", "wufeng01"); 
		taskService.submitTask(taskId, variables);
		
	}

	@Override
	public void cashFlowInDirectorAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
        
        String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCodeByIn(businessKey);
    	spvCode = (String) request.getAttribute("spvCode");
    	if(null == spvCode && spvChargeInfoVO != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
    		spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
    	}
    	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
    	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(null == toSpv?null:toSpv.getPkid());
    	if(spvChargeInfoVO != null){       	
    		String applyAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getApplyAuditor()).getRealName();
        	String createByName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getCreateBy()).getRealName();
        //	divideTenThousand(spvChargeInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);  
        	request.setAttribute("applyAuditorName", applyAuditorName);
        	request.setAttribute("createByName", createByName);
    	}

    	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);    
        
	}

	@Override
	public void cashFlowInDirectorAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey, Boolean chargeInAppr)
			throws Exception {
			SessionUser user = uamSessionService.getSessionUser();
			Map<String, Object> variables = new HashMap<String, Object>();
			String statusType="";
			/**1.查询申请*/
			ToSpvCashFlowApply toSpvCashFlowApply = toSpvCashFlowApplyMapper.selectByCashFlowApplyCode(spvRecordedsVO.getBusinessKey());
			
			ToSpvAduit toSpvAduit = new ToSpvAduit();
			//toSpvAduit.setApplyId(spvRecordedsVO.getToSpvCashFlowApplyPkid());//流水ID
			toSpvAduit.setApplyId(toSpvCashFlowApply.getPkid().toString());
			toSpvAduit.setActProcId(taskId);//流程实例
			toSpvAduit.setTaskDefKey(instCode);//流程节点
			toSpvAduit.setTaskId(taskId);//任务ID
			toSpvAduit.setOperator(user.getId());//操作人
			
			String resultType="";//审核结果
			switch (handle) {
           		case "apply":
           			resultType = "风控专员";
           			break;
               case "directorAduit":
            	   resultType = "总监审批";
           			break;
               case "financeAduit":
            	   resultType = "财务审核";
            	   break;
            }
			
			if(chargeInAppr){
				toSpvAduit.setResult(resultType+"通过");
				statusType = SpvCashFlowApplyStatusEnum.FINANCEADUIT.getCode();
				//variables.put("assignee", "wufeng01");	
			}else{
				toSpvAduit.setResult(resultType+"驳回");
				statusType = SpvCashFlowApplyStatusEnum.APPLY.getCode();
				//variables.put("assignee", "wangqiao7");	
			}
			
			toSpvAduit.setContent(spvRecordedsVO.getTurndownContent());//内容
			toSpvAduit.setCreateBy(user.getId());
			toSpvAduit.setCreateTime(new Date());
			toSpvAduitMapper.insertSelective(toSpvAduit);//保存审核记录

			List<ToSpvCashFlow> toSpvCashFlowList = toSpvCashFlowMapper.selectByCashFlowApplyId(toSpvCashFlowApply.getPkid());
			if(null != toSpvCashFlowList)
				for(ToSpvCashFlow toSpvCashFlow:toSpvCashFlowList){
					toSpvCashFlow.setStatus(statusType);
					toSpvCashFlow.setUpdateBy(user.getId());
					toSpvCashFlow.setUpdateTime(new Date());
					toSpvCashFlowMapper.updateByPrimaryKeySelective(toSpvCashFlow);//更新状态
				}
			
			toSpvCashFlowApply.setStatus(statusType);
			toSpvCashFlowApply.setUpdateBy(user.getId());
			toSpvCashFlowApply.setUpdateTime(new Date());
			toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(toSpvCashFlowApply);//更新状态
			
			variables.put("directorAduit",chargeInAppr);
			taskService.submitTask(taskId, variables);
			
	}

	@Override
	public void cashFlowInFinanceAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCodeByIn(businessKey);

        if(spvChargeInfoVO != null && spvChargeInfoVO.getToSpvCashFlowApply() != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	String applyAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getApplyAuditor()).getRealName();
        	String createByName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getCreateBy()).getRealName();
        	request.setAttribute("applyAuditorName", applyAuditorName);
        	request.setAttribute("createByName", createByName);
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	@Override
	public void cashFlowInFinanceAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey, Boolean chargeInAppr)
			throws Exception {

			SessionUser user = uamSessionService.getSessionUser();
			TaskVo task = workFlowManager.getHistoryTask(taskId);
			Map<String, Object> variables = new HashMap<String, Object>();
			String statusType = "";
			/**1.查询申请*/
			ToSpvCashFlowApply toSpvCashFlowApply = toSpvCashFlowApplyMapper.selectByCashFlowApplyCode(spvRecordedsVO.getBusinessKey());
			
			ToSpvAduit toSpvAduit = new ToSpvAduit();
			
			//toSpvAduit.setApplyId(spvRecordedsVO.getToSpvCashFlowApplyPkid());//流水ID
			toSpvAduit.setApplyId(toSpvCashFlowApply.getPkid().toString());
			toSpvAduit.setActProcId(taskId);//流程实例
			toSpvAduit.setTaskDefKey(instCode);//流程节点
			toSpvAduit.setTaskId(taskId);//任务ID
			toSpvAduit.setOperator(user.getId());//操作人
			String resultType="";//审核结果
			switch (handle) {
	       		case "apply":
	       			resultType = "风控专员";
	       			break;
	            case "directorAduit":
	        	   resultType = "总监审批";
	       			break;
	            case "financeAduit":
	        	   resultType = "财务审核";
	        	   break;
			}
			
			if(chargeInAppr){
				toSpvAduit.setResult(resultType+"通过");
				statusType = SpvCashFlowApplyStatusEnum.AUDITCOMPLETED.getCode();
				ToWorkFlow workFlow = toWorkFlowService.queryWorkFlowByInstCode(instCode);//更新状态
				workFlow.setStatus(WorkFlowStatus.COMPLETE.getCode());
				toWorkFlowService.updateByPrimaryKeySelective(workFlow);
				//variables.put("assignee", "wufeng01");	
			}else{
				toSpvAduit.setResult(resultType+"驳回");
				statusType = SpvCashFlowApplyStatusEnum.APPLY.getCode();
				//variables.put("assignee", "wangqiao7");	
			}
			
			toSpvAduit.setContent(spvRecordedsVO.getTurndownContent());//内容
			toSpvAduit.setCreateBy(user.getId());
			toSpvAduit.setCreateTime(new Date());
			toSpvAduitMapper.insertSelective(toSpvAduit);
			
			List<ToSpvCashFlow> toSpvCashFlowList = toSpvCashFlowMapper.selectByCashFlowApplyId(toSpvCashFlowApply.getPkid());
			if(null != toSpvCashFlowList)
				for(ToSpvCashFlow toSpvCashFlow:toSpvCashFlowList){
					toSpvCashFlow.setStatus(statusType);
					toSpvCashFlow.setUpdateBy(user.getId());
					toSpvCashFlow.setUpdateTime(new Date());
					if(chargeInAppr){
						toSpvCashFlow.setCloseTime(new Date());//完成时间
					}
					toSpvCashFlowMapper.updateByPrimaryKeySelective(toSpvCashFlow);
				}
			
			if(!StringUtils.isBlank(toSpvCashFlowMapper.getUserIdByUserName(task.getAssignee()))){
				toSpvCashFlowApply.setFtPostAuditor(toSpvCashFlowMapper.getUserIdByUserName(task.getAssignee()));//设置处理人
			}
			
			toSpvCashFlowApply.setStatus(statusType);
			toSpvCashFlowApply.setUpdateBy(user.getId());
			toSpvCashFlowApply.setUpdateTime(new Date());
			toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(toSpvCashFlowApply);
			
			variables.put("financeAduit",chargeInAppr);
			taskService.submitTask(taskId, variables);
	}


	@Override
	public void cashFlowInFinanceSecondAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeInAppr)
			throws Exception {
		
		    chargeInAppr = true;
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("financeSecondAduit",chargeInAppr);
			
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowInDealProcess(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCode(businessKey);

        if(spvChargeInfoVO != null && spvChargeInfoVO.getToSpvCashFlowApply() != null && !StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode())){
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
        	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv.getPkid());
        	String applyAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getApplyAuditor()).getRealName();
        	String ftPreAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getFtPreAuditor()).getRealName();
        	String ftPostAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getFtPostAuditor()).getRealName();
        	String createByName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getCreateBy()).getRealName();
        	request.setAttribute("applyAuditorName", applyAuditorName);
        	request.setAttribute("ftPreAuditorName", ftPreAuditorName);
        	request.setAttribute("ftPostAuditorName", ftPostAuditorName);
        	request.setAttribute("createByName", createByName);
        	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);
        }        
	}

	@Override
	public void cashFlowInDeal(HttpServletRequest request, String instCode, String taskId, String handle,
			SpvChargeInfoVO spvChargeInfoVO, Boolean chargeInAppr) throws Exception {
		
			Map<String, Object> variables = new HashMap<String, Object>();
			chargeInAppr = true;
			taskService.submitTask(taskId, variables);
	}	
	
	private String createSpvApplyCode() {
		return uamBasedataService.nextSeqVal("SPV_CODE", new SimpleDateFormat("yyyyMMdd").format(new Date()));
	}
	
	@Override
	public void cashFlowInPageDeal(HttpServletRequest request, String handle, SpvRecordedsVO spvRecordedsVO, String businessKey) throws Exception {
		SessionUser user = uamSessionService.getSessionUser();
		
		if(null == spvRecordedsVO || null == spvRecordedsVO.getItems()) throw new BusinessException("申请入账流水信息不存在！");
		
		//创建spvApplyCode
		String spvApplyCode = createSpvApplyCode();
		if(null == spvApplyCode ) throw new BusinessException("创建spvApplyCode失败！");
		//保存数据
		toSpvService.saveSpvChargeInfoVObyIn(spvRecordedsVO,handle,spvApplyCode); 
		
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("RiskControlOfficer", user.getUsername());
		User riskControlDirector = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getServiceDepId(), "JYFKZJ");
		vars.put("RiskControlDirector", riskControlDirector.getUsername());
		//开启流程
		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSpvCashflowInProcess(), spvApplyCode, vars);

		//插入工作流表
		ToWorkFlow workFlow = new ToWorkFlow();
		//workFlow.setBusinessKey(spvApplyCode);
		workFlow.setBusinessKey("SpvCashflowInProcess");
		if(null != spvRecordedsVO.getSpvConCode()){
			workFlow.setCaseCode(spvRecordedsVO.getCaseCode());
		}else{
			throw new BusinessException("监管合约信息不存在！");
		}
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(propertyUtilsService.getSpvCashflowInProcess());
		workFlow.setProcessOwner(user.getId());
		workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSpvCashflowInProcessSelective(workFlow);
		
		// 提交申请任务
		PageableVo pageableVo = taskService.listTasks(processInstance.getId(), false);
		List<TaskVo> taskList = pageableVo.getData();
		for (TaskVo task : taskList) {
			if ("SpvCashflowInApply".equals(task.getTaskDefinitionKey())) {
				taskService.complete(task.getId() + "");
			}
		}
	}
	@Override
	public SpvReturnCashflowVO saveCashFlowApply(HttpServletRequest request, String handle, SpvRecordedsVO spvRecordedsVO, String businessKey) throws Exception {
		if(null == spvRecordedsVO || null == spvRecordedsVO.getItems() || StringUtils.isEmpty(spvRecordedsVO.getItems().get(0).getPayerName()) ) throw new BusinessException("申请入账流水信息不存在！");
		//保存数据
		toSpvService.saveSpvChargeInfoVObyIn(spvRecordedsVO,handle,businessKey); 
		String type = null;
		//保存数据
		return toSpvService.saveSpvChargeInfoVOFormHtml(spvRecordedsVO,type); 
		
	}
	
	
	@Override
	public void cashFlowOutApprDealAppDelete(HttpServletRequest request, String instCode, String pkid,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey, Boolean chargeInAppr) throws Exception {

		SessionUser user = uamSessionService.getSessionUser();
		
		ToSpvCashFlow toSpvCashFlow = toSpvCashFlowMapper.selectByPrimaryKey(Long.valueOf(pkid));
		toSpvCashFlow.setUpdateBy(user.getId());
		toSpvCashFlow.setUpdateTime(new Date());
		toSpvCashFlow.setIsDeleted("1");
		toSpvCashFlowMapper.updateByPrimaryKey(toSpvCashFlow);
		
	}
	@Override
	public void saveAttachments(FileUploadVO fileUploadVO,String cashFlowCode) {
		SessionUser user = uamSessionService.getSessionUser();
		
		ToSpvCashFlow toSpvCashFlow = toSpvCashFlowMapper.selectByPrimaryKey(Long.valueOf(cashFlowCode));
		
		if(fileUploadVO.getPkIdArr() != null && !fileUploadVO.getPkIdArr().isEmpty()) {
			delAttachment(fileUploadVO.getPkIdArr());
		}
		
		if(fileUploadVO.getPictureNo() != null && !fileUploadVO.getPictureNo().isEmpty()){
			if(null != toSpvCashFlow)
			for(int i=0; i<fileUploadVO.getPictureNo().size(); i++) {
				ToSpvReceipt attach = new ToSpvReceipt();
				attach.setAttachId(fileUploadVO.getPictureNo().get(i));
				attach.setCashflowId(toSpvCashFlow.getPkid().toString());
				attach.setType("in");
				int length = fileUploadVO.getPicName().get(i).length();
				int index = fileUploadVO.getPicName().get(i).lastIndexOf(".");
				attach.setType(fileUploadVO.getPicName().get(i).substring(index+1, length));
				attach.setComment(fileUploadVO.getPicName().get(i));
				attach.setIsDeleted("0");
				attach.setCreateBy(user.getId());
				attach.setCreateTime(new Date());
				
				toSpvReceiptMapper.insertSelective(attach);
				
			/*	//回单编号	
				toSpvCashFlow.setReceiptNo(attach.getPkid().toString());
				//回单生成时间	
				toSpvCashFlow.setReceiptTime(new Date());
				toSpvCashFlowMapper.updateByPrimaryKeySelective(toSpvCashFlow);*/
				
			}	
		}
		
	}
	@Override
	public void delAttachment(List<Long> pkIdArr) {
		for(Long pkid:pkIdArr) {
			toSpvCashFlowApplyAttachMapper.setIsDeletedByPrimaryKey(pkid);
		}
	}
	/**
	 * 删除入账申请数据
	 */
	@Override
	public void cashFlowOutApprDeleteCashFlowAll(HttpServletRequest request, String instCode, String pkid,
			String handle) throws Exception {
		SessionUser user = uamSessionService.getSessionUser();
		if(StringUtils.equals("apply", handle) && !StringUtils.isBlank(instCode)){
			ToSpvCashFlowApply toSpvCashFlowApply = toSpvCashFlowApplyMapper.selectByPrimaryKey(Long.valueOf(pkid));
			List<ToSpvCashFlow> toSpvCashFlowList = toSpvCashFlowMapper.selectByCashFlowApplyId(toSpvCashFlowApply.getPkid());
			for(ToSpvCashFlow toSpvCashFlow:toSpvCashFlowList){
				toSpvCashFlow.setUpdateBy(user.getId());
				toSpvCashFlow.setUpdateTime(new Date());
				toSpvCashFlow.setIsDeleted("1");
				toSpvCashFlowMapper.updateByPrimaryKey(toSpvCashFlow);//删除入账流水
				List<ToSpvReceipt> toSpvReceiptList = toSpvReceiptMapper.selectByCashFlowId(toSpvCashFlow.getPkid().toString());
				for(ToSpvReceipt toSpvReceipt:toSpvReceiptList){
					toSpvReceipt.setUpdateBy(user.getId());
					toSpvReceipt.setUpdateTime(new Date());
					toSpvReceipt.setIsDeleted("1");
					toSpvReceiptMapper.updateByPrimaryKeySelective(toSpvReceipt);//删除入账流水附件
				}
			}
			toSpvCashFlowApply.setUpdateBy(user.getId());
			toSpvCashFlowApply.setUpdateTime(new Date());
			toSpvCashFlowApply.setIsDeleted("1");
			toSpvCashFlowApplyMapper.updateByPrimaryKey(toSpvCashFlowApply);//删除入账申请
			processInstanceService.deleteProcess(instCode);//删除流程
		}
	}

}
