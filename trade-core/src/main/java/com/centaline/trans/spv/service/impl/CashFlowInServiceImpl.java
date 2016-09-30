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
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
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
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired
	private UamPermissionService uamPermissionService;	
	@Autowired
	private UamSessionService uamSessionService;	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamBasedataService uamBasedataService;
	
	@Autowired
	private ToSpvCashFlowApplyMapper toSpvCashFlowApplyMapper;	
	
	@Autowired
	private ToSpvCashFlowMapper toSpvCashFlowMapper;
	@Autowired
	private ToSpvAduitMapper toSpvAduitMapper;
	@Autowired
	private ToSpvVoucherMapper toSpvVoucherMapper;
	@Autowired
	private ToSpvReceiptMapper toSpvReceiptMapper;
	@Autowired
	private ToSpvCashFlowApplyAttachMapper toSpvCashFlowApplyAttachMapper;

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
	public void cashFlowInApplyDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvRecordedsVO spvRecordedsVO, String businessKey) throws Exception {
		//multiplyTenThousand(spvChargeInfoVO);
	   // toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);    
		
		//保存数据
		//toSpvService.saveSpvChargeInfoVObyIn(spvRecordedsVO); 
		Map<String, Object> variables = new HashMap<String, Object>();
		
		taskService.submitTask(taskId, variables);
		
	}

	@Override
	public void cashFlowInDirectorAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
        
        String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCodeByIn(businessKey);
    	spvCode = (String) request.getAttribute("spvCode");
    	ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
    	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv == null?null:toSpv.getPkid());
    	if(spvChargeInfoVO != null){       	
    		String applyAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getApplyAuditor()).getRealName();
        	String ftPreAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getFtPreAuditor()).getRealName();
        	String ftPostAuditorName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getFtPostAuditor()).getRealName();
        	String createByName = uamSessionService.getSessionUserById(spvChargeInfoVO.getToSpvCashFlowApply().getCreateBy()).getRealName();
        	divideTenThousand(spvChargeInfoVO);
        	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);  
        	request.setAttribute("applyAuditorName", applyAuditorName);
        	request.setAttribute("ftPreAuditorName", ftPreAuditorName);
        	request.setAttribute("ftPostAuditorName", ftPostAuditorName);
        	request.setAttribute("createByName", createByName);
    	}

    	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);    
        
	}

	@Override
	public void cashFlowInDirectorAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeInAppr)
			throws Exception {

		    //toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);    
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("directorAduit",chargeInAppr);
			
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowInFinanceAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
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
	public void cashFlowInFinanceAduitDeal(HttpServletRequest request, String instCode, String taskId,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeInAppr)
			throws Exception {

			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("financeAduit",chargeInAppr);
			
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowInFinanceSecondAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
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
		//开启流程
		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSpvCashflowInProcess(), spvApplyCode, vars);

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
	public SpvReturnCashflowVO saveCashFlowApply(HttpServletRequest request, String handle, SpvRecordedsVO spvRecordedsVO, String businessKey) throws Exception {
		if(null == spvRecordedsVO || null == spvRecordedsVO.getItems()) throw new BusinessException("申请入账流水信息不存在！");
		String type = null;
		//保存数据
		return toSpvService.saveSpvChargeInfoVOFormHtml(spvRecordedsVO,type); 
		
	}
	
	//除万操作
	private void divideTenThousand(SpvChargeInfoVO spvChargeInfoVO){
		List<SpvCaseFlowOutInfoVO> spvCaseFlowOutInfoVOList = spvChargeInfoVO.getSpvCaseFlowOutInfoVOList();
		if(spvCaseFlowOutInfoVOList != null && !spvCaseFlowOutInfoVOList.isEmpty()){
			for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO : spvCaseFlowOutInfoVOList){
				ToSpvCashFlow toSpvCashFlow = spvCaseFlowOutInfoVO.getToSpvCashFlow();
				toSpvCashFlow.setAmount(toSpvCashFlow.getAmount() == null?null:toSpvCashFlow.getAmount().divide(new BigDecimal(10000)));
			}		
		}
	}

}
